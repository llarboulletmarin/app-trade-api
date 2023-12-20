package fr.apptrade.backend.api.v1.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit_card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "card_holder")
    private String cardHolder;

    @Column(name = "card_number", length = 16)
    private String cardNumber;

    @Column(name = "card_cvc", length = 3)
    private String cardCvc;

    @Column(name = "card_expiration_date")
    @Temporal(TemporalType.DATE)
    private Date cardExpirationDate;

    @Column(name = "fkid_user")
    private Integer fkidUser;

}
