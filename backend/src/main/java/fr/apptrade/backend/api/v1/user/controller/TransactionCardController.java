package fr.apptrade.backend.api.v1.user.controller;

import fr.apptrade.backend.api.v1.config.model.ApiResponse;
import fr.apptrade.backend.api.v1.user.service.ITransactionCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("${api.base-url.v1}/user/card-transactions")
public class TransactionCardController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionCardController.class);

    private final ITransactionCardService transactionCardService;

    @Autowired
    public TransactionCardController(ITransactionCardService transactionCardService) {
        this.transactionCardService = transactionCardService;
    }

    /**
     * Endpoint de récupération des transactions de carte de crédit de l'utilisateur
     *
     * @param email : email de l'utilisateur connecté
     * @return : transactions de carte de crédit de l'utilisateur
     */
    @GetMapping("")
    public ResponseEntity<?> getTransactionsCard(@CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("getTransactionsCard({})", email);

        try {
            return ResponseEntity.ok(this.transactionCardService.getTransactionsCard(email));
        } catch (Exception e) {
            logger.error("getTransactionsCard({}, exception: {})", email, e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    /**
     * Endpoint de récupération des transactions de carte de crédit de l'utilisateur pour une carte de crédit
     *
     * @param email : email de l'utilisateur connecté
     * @return : transactions de carte de crédit de l'utilisateur
     */
    @GetMapping("/{cardNumber}")
    public ResponseEntity<?> getTransactionsCard(@PathVariable String cardNumber,
                                                 @CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("getTransactionsCard({}, cardNumber: {})", email, this.cardNumberToString(cardNumber));

        try {
            return ResponseEntity.ok(this.transactionCardService.getTransactionsCardByCardNumber(email, cardNumber));
        } catch (Exception e) {
            logger.error("getTransactionsCard({}, cardNumber: {}, exception: {})", email, this.cardNumberToString(cardNumber), e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    /**
     * Permet de masquer les 12 premiers chiffres de la carte de crédit
     *
     * @param cardNumber : numéro de la carte de crédit
     * @return : numéro de la carte de crédit masqué
     */
    private String cardNumberToString(String cardNumber) {
        return cardNumber.substring(0, cardNumber.length() - 4).replaceAll("[0-9]", "X") + cardNumber.substring(cardNumber.length() - 4);
    }

}
