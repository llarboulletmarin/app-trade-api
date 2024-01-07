package fr.apptrade.backend.api.v1.user.model;

import fr.apptrade.backend.api.v1.currency.model.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "amount", nullable = false, precision = 18, scale = 9)
    private BigDecimal amount;

    @Column(name = "value", nullable = false, precision = 18, scale = 9)
    private BigDecimal value;

    @Column(name = "transaction_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date transactionDate;
}
