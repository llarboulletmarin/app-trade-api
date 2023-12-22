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
    balance DECIMAL(18, 2) NOT NULL DEFAULT 0,
    register_date DATE NOT NULL,
    last_update_date DATE,
    fkid_role INT,
    CONSTRAINT fk_role_user FOREIGN KEY (fkid_role) REFERENCES role(id)
);
INSERT INTO user ( email, password, last_name, first_name, birthdate, sex, street, zip_code, city, country, balance, register_date, last_update_date, fkid_role )
VALUES (
        'example@example.com',
        '$2a$10$Ul9LL06mFRwwl8.6ZYhzHupc57gBPSuZqxOWzyjKzm.tWi2QV8bUe',
        'Doe',
        'John',
        '1990-01-01',
        'M',
        '123 Crypto Lane',
        '12345',
        'Bitville',
        'Cryptoland',
        1000000,
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
    fkid_user INT,
    card_holder          VARCHAR(255) NOT NULL,
    card_number          VARCHAR(16),
    card_cvc             VARCHAR(3),
    card_expiration_date DATE,
    CONSTRAINT fk_user_credit_card FOREIGN KEY (fkid_user) REFERENCES user(id)
);
INSERT INTO credit_card (fkid_user, card_holder, card_number, card_cvc, card_expiration_date)
VALUES ((SELECT id FROM user WHERE email = 'example@example.com'), 'Doe John Mom', '1234567890123456', '711',
        '2025-01-01'),
       ((SELECT id FROM user WHERE email = 'example@example.com'), 'Doe John Dad', '1234567891834189', '444',
        '2024-07-24')
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

CREATE TABLE transac_card (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(18, 2) NOT NULL,
    transaction_date DATE NOT NULL,
    fkid_credit_card INT,
    fkid_user INT,
    CONSTRAINT fk_user_card_transactions FOREIGN KEY (fkid_user) REFERENCES user(id),
    CONSTRAINT fk_credit_card_transactions FOREIGN KEY (fkid_credit_card) REFERENCES credit_card(id)
);
INSERT INTO transac_card (fkid_credit_card, fkid_user, amount, transaction_date)
VALUES
    ((SELECT id FROM credit_card WHERE card_number = '1234567890123456'), (SELECT id FROM user WHERE email = 'example@example.com'), 1000, CURDATE()),
    ((SELECT id FROM credit_card WHERE card_number = '1234567890123456'), (SELECT id FROM user WHERE email = 'example@example.com'), -600, CURDATE()),
    ((SELECT id FROM credit_card WHERE card_number = '1234567891834189'), (SELECT id FROM user WHERE email = 'example@example.com'), 10000, CURDATE()),
    ((SELECT id FROM credit_card WHERE card_number = '1234567891834189'), (SELECT id FROM user WHERE email = 'example@example.com'), -700, CURDATE())