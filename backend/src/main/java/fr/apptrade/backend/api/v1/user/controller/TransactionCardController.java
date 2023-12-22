package fr.apptrade.backend.api.v1.user.controller;

import fr.apptrade.backend.api.v1.config.model.ApiResponse;
import fr.apptrade.backend.api.v1.user.model.request.TransactionCardRequest;
import fr.apptrade.backend.api.v1.user.service.ITransactionCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;

@RestController
@RequestMapping("${api.base-url.v1}/user")
public class TransactionCardController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionCardController.class);

    private final ITransactionCardService transactionCardService;

    @Autowired
    public TransactionCardController(ITransactionCardService transactionCardService) {
        this.transactionCardService = transactionCardService;
    }

    /**
     * Endpoint de dépôt d'argent sur le compte de l'utilisateur
     *
     * @param depositRequest : montant à déposer et la carte de crédit
     * @param email          : email de l'utilisateur connecté
     * @return : montant déposé
     */
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody TransactionCardRequest depositRequest,
                                     @CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("deposit({}, depositRequest: {})", email, depositRequest);

        try {
            if (BigDecimal.ZERO.compareTo(depositRequest.getAmount()) >= 0) {
                throw new RuntimeException("Amount must be greater than 0");
            }

            return ResponseEntity.ok(this.transactionCardService.deposit(email, depositRequest));
        } catch (Exception e) {
            logger.error("deposit({}, depositRequest: {}, exception: {})", email, depositRequest, e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    /**
     * Endpoint de retrait d'argent du compte de l'utilisateur
     *
     * @param withdrawRequest : montant à retirer et la carte de crédit
     * @param email           : email de l'utilisateur connecté
     * @return : montant retiré
     */
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody TransactionCardRequest withdrawRequest,
                                      @CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("withdraw({}, withdrawRequest: {})", email, withdrawRequest);

        try {
            if (BigDecimal.ZERO.compareTo(withdrawRequest.getAmount()) >= 0) {
                throw new RuntimeException("Amount must be greater than 0");
            }

            return ResponseEntity.ok(this.transactionCardService.withdraw(email, withdrawRequest));
        } catch (Exception e) {
            logger.error("withdraw({}, withdrawRequest: {}, exception: {})", email, withdrawRequest, e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    /**
     * Endpoint de récupération des transactions de carte de crédit de l'utilisateur
     *
     * @param email : email de l'utilisateur connecté
     * @return : transactions de carte de crédit de l'utilisateur
     */
    @GetMapping("/card-transactions")
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
    @GetMapping("/card-transactions/{cardNumber}")
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
