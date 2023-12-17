CREATE TABLE role (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO role (role) VALUES ('admin');
INSERT INTO role (role) VALUES ('user');

CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    birthdate DATE NOT NULL,
    sex VARCHAR(1) NOT NULL,
    street VARCHAR(255) NOT NULL,
    zip_code VARCHAR(30) NOT NULL,
    city VARCHAR(30) NOT NULL,
    country VARCHAR(30) NOT NULL,
    register_date DATE NOT NULL,
    last_update_date DATE,
    fkid_role INT,
    CONSTRAINT fk_role_user FOREIGN KEY (fkid_role) REFERENCES role(id)
);
INSERT INTO user ( email, password, last_name, first_name, birthdate, sex, street, zip_code, city, country, register_date, last_update_date, fkid_role )
VALUES (
        'example@example.com',
        'MTIzNDU2Nzg=',
        'Doe',
        'John',
        '1990-01-01',
        'M',
        '123 Crypto Lane',
        '12345',
        'Bitville',
        'Cryptoland',
        CURDATE(),
        CURDATE(),
        (SELECT id FROM role WHERE role = 'user')
);

CREATE TABLE currency (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(30) NOT NULL
);
INSERT INTO currency (name, code) VALUES ('Bitcoin', 'BTC'),
                                         ('Ethereum', 'ETH'),
                                         ('Litecoin', 'LTC');

CREATE TABLE transac (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fkid_currency INT,
    fkid_user INT,
    amount DECIMAL(18,9) NOT NULL,
    value DECIMAL(18,9) NOT NULL,
    transaction_date DATE,
    CONSTRAINT fk_transaction_currency FOREIGN KEY (fkid_currency) REFERENCES currency(id),
    CONSTRAINT fk_transaction_user FOREIGN KEY (fkid_user) REFERENCES user(id)
);
INSERT INTO transac (fkid_currency, fkid_user, amount, value, transaction_date)
VALUES
    ((SELECT id FROM currency WHERE code = 'BTC'), (SELECT id FROM user WHERE email = 'example@example.com'), 0.001, 35000.123456789, CURDATE()),
    ((SELECT id FROM currency WHERE code = 'ETH'), (SELECT id FROM user WHERE email = 'example@example.com'), 2.000, 2500.987654321, CURDATE());


CREATE TABLE credit_card (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    num_card VARCHAR(30),
    fkid_user INT,
    CONSTRAINT fk_user_credit_card FOREIGN KEY (fkid_user) REFERENCES user(id)
);
INSERT INTO credit_card (num_card, fkid_user)
VALUES
    ('1234567890123456', (SELECT id FROM user WHERE email = 'example@example.com')),
    ('1234567890123457', (SELECT id FROM user WHERE email = 'example@example.com'))
;

CREATE TABLE favorite (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fkid_currency INT,
    fkid_user INT,
    CONSTRAINT fk_user_favorite FOREIGN KEY (fkid_user) REFERENCES user(id),
    CONSTRAINT fk_id_currency FOREIGN KEY (fkid_currency) REFERENCES currency(id)
);
INSERT INTO favorite (fkid_currency, fkid_user)
VALUES
    ((SELECT id FROM currency WHERE code = 'BTC'), (SELECT id FROM user WHERE email = 'example@example.com')),
    ((SELECT id FROM currency WHERE code = 'ETH'), (SELECT id FROM user WHERE email = 'example@example.com'));
