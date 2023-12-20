package fr.apptrade.backend.api.v1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit_card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "num_card", length = 30)
    private String numCard;

    @ManyToOne
    @JoinColumn(name = "fkid_user", referencedColumnName = "id")
    private User user;

}
