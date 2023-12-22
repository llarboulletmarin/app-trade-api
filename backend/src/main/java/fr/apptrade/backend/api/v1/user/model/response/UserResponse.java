package fr.apptrade.backend.api.v1.user.model.response;

import fr.apptrade.backend.api.v1.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String email;
    private String lastName;
    private String firstName;
    private Date birthdate;
    private String sex;
    private String street;
    private String zipCode;
    private String city;
    private String country;
    private BigDecimal balance;
    private List<CreditCardResponse> creditCards;
    private Date registerDate;
    private Date lastUpdateDate;

    public UserResponse(User user) {
        this.email = user.getEmail();
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
        this.birthdate = user.getBirthdate();
        this.sex = user.getSex();
        this.street = user.getStreet();
        this.zipCode = user.getZipCode();
        this.city = user.getCity();
        this.country = user.getCountry();
        this.balance = user.getBalance();
        this.creditCards = user.getCreditCards().stream().map(CreditCardResponse::new).toList();
        this.registerDate = user.getRegisterDate();
        this.lastUpdateDate = user.getLastUpdateDate();
    }
}
