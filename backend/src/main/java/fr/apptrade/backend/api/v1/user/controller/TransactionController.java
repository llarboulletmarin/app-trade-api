package fr.apptrade.backend.api.v1.user.controller;

import fr.apptrade.backend.api.v1.config.model.ApiResponse;
import fr.apptrade.backend.api.v1.user.service.ITransactionService;
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
@RequestMapping("${api.base-url.v1}/user/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final ITransactionService transactionService;

    @Autowired
    public TransactionController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Endpoint de récupération des transactions de l'utilisateur
     *
     * @param email : email de l'utilisateur connecté
     * @return : transactions de l'utilisateur
     */
    @GetMapping("")
    public ResponseEntity<?> getTransactions(@CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("getTransactions({})", email);

        try {
            return ResponseEntity.ok(this.transactionService.getTransactions(email));
        } catch (Exception e) {
            logger.error("getTransactions({}, exception: {})", email, e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    /**
     * Endpoint de récupération des transactions de l'utilisateur pour une devise
     *
     * @param email : email de l'utilisateur connecté
     * @return : transactions de l'utilisateur
     */
    @GetMapping("/{currencyCode}")
    public ResponseEntity<?> getTransactions(@PathVariable String currencyCode,
                                             @CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("getTransactions({}, currencyCode: {})", email, currencyCode);

        try {
            return ResponseEntity.ok(this.transactionService.getTransactionsByCode(email, currencyCode));
        } catch (Exception e) {
            logger.error("getTransactions({}, currencyCode: {}, exception: {})", email, currencyCode, e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

}
