package fr.apptrade.backend.api.v1.user.model;

import fr.apptrade.backend.api.v1.currency.model.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fkid_currency", referencedColumnName = "id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "fkid_user", referencedColumnName = "id")
    private User user;

}
