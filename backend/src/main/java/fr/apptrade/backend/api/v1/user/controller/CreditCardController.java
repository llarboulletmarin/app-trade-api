package fr.apptrade.backend.api.v1.user.controller;

import fr.apptrade.backend.api.v1.config.model.ApiResponse;
import fr.apptrade.backend.api.v1.user.model.CreditCard;
import fr.apptrade.backend.api.v1.user.service.ICreditCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("${api.base-url.v1}/user/cc")
public class CreditCardController {

    private static final Logger logger = LoggerFactory.getLogger(CreditCardController.class);

    private final ICreditCardService creditCardService;

    @Autowired
    public CreditCardController(ICreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    /**
     * Endpoint d'ajout d'une carte de crédit
     *
     * @param creditCard : carte de crédit
     * @param email      : email de l'utilisateur connecté
     * @return : carte de crédit mise à jour
     */
    @PostMapping("")
    public ResponseEntity<?> addCreditCard(@RequestBody CreditCard creditCard,
                                           @CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("addCreditCard({}, {})", email, this.creditCardToString(creditCard));

        try {
            if (creditCard == null) {
                return ResponseEntity.badRequest().body(new ApiResponse("Body are required", 400, "Bad Request", "Body are required", Instant.now()));
            }

            String error = this.checkCreditCardRequest(creditCard);
            if (error != null) {
                return ResponseEntity.badRequest().body(new ApiResponse(error, 400, "Bad Request", error, Instant.now()));
            }

            return ResponseEntity.ok(this.creditCardService.addCreditCard(creditCard, email));
        } catch (Exception e) {
            logger.error("addCreditCard({}, {}, exception : {})", email, this.creditCardToString(creditCard), e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCreditCard(@PathVariable Integer cardId,
                                              @CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("deleteCreditCard({}, cardId: {})", email, cardId);

        try {
            if (cardId == null) {
                return ResponseEntity.badRequest().body(new ApiResponse("Card id are required", 400, "Bad Request", "Card id are required", Instant.now()));
            }

            this.creditCardService.deleteCreditCard(cardId, email);

            return ResponseEntity.ok(new ApiResponse("Credit card deleted", 200, null, null, Instant.now()));
        } catch (Exception e) {
            logger.error("deleteCreditCard({}, cardId: {}, exception: {})", email, cardId, e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    /**
     * Permet de ne pas afficher les informations de la carte de crédit dans les logs
     *
     * @param creditCard : carte de crédit
     * @return : carte de crédit en string
     */
    private String creditCardToString(CreditCard creditCard) {
        if (creditCard == null) {
            return null;
        }

        return creditCard.toString()
                .replaceAll("cardNumber=[0-9]{12}", "cardNumber=XXXXXXXXXXXX")
                .replaceAll("cardCvc=.*?,", "cardCvc=XXX")
                .replaceAll("cardExpirationDate=.*?,", "cardExpirationDate=XXXX,");
    }

    /**
     * Vérifie que les informations de la carte de crédit sont correctes
     *
     * @param creditCard : carte de crédit
     * @return : erreur ou null
     */
    private String checkCreditCardRequest(CreditCard creditCard) {
        if (creditCard == null) {
            return "Credit card are required";
        }

        if (creditCard.getCardHolder() == null || creditCard.getCardHolder().isEmpty()) {
            return "Card holder are required";
        }

        if (creditCard.getCardNumber() == null || creditCard.getCardNumber().isEmpty()) {
            return "Card number are required";
        }

        if (creditCard.getCardCvc() == null || creditCard.getCardCvc().isEmpty()) {
            return "CVV are required";
        }

        if (creditCard.getCardExpirationDate() == null) {
            return "Expiration date are required";
        }

        return null;
    }

}
