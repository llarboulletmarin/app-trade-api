package fr.apptrade.backend.api.v1.user.model;

import fr.apptrade.backend.api.v1.currency.model.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transac")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fkid_currency", referencedColumnName = "id")
    private Currency currency;

    @Column(name = "fkid_user", nullable = false)
    private Integer fkidUser;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "transaction_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
}
