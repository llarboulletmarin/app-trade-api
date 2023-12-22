package fr.apptrade.backend.api.v1.user.model;

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

@Table(name = "transac_card")
public class TransactionCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date transactionDate;

    @ManyToOne
    @JoinColumn(name = "fkid_credit_card", referencedColumnName = "id")
    private CreditCard creditCard;

    @Column(name = "fkid_user", nullable = false)
    private Integer fkidUser;

}
