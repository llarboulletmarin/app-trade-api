package fr.apptrade.backend.api.v1.user.controller;

import fr.apptrade.backend.api.v1.config.model.ApiResponse;
import fr.apptrade.backend.api.v1.user.model.CreditCard;
import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.service.IUserService;
import fr.apptrade.backend.api.v1.user.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("${api.base-url.v1}/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Endpoint d'inscription
     *
     * @param user : utilisateur
     * @return : ok ou erreur
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        logger.info("register({})", user);

        try {
            String error = this.checkRegisterRequest(user);
            if (error != null) {
                return ResponseEntity.badRequest().body(new ApiResponse(error, 400, "Bad Request", error, Instant.now()));
            }

            this.userService.register(user);
            return ResponseEntity.ok(new ApiResponse("User created", 200, null, null, Instant.now()));
        } catch (Exception e) {
            logger.error("login()", e);
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    /**
     * Endpoint de connexion (est utilisé en réalité pour récupérer les informations de l'utilisateur)
     *
     * @param email : email
     * @return : informations de l'utilisateur
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("login({})", email);

        try {
            if (email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse("Email are required", 400, "Bad Request", "Email are required", Instant.now()));
            }

            return ResponseEntity.ok(this.userService.getUserByEmail(email));
        } catch (Exception e) {
            logger.error("login()", e);
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    /**
     * Endpoint d'ajout d'une carte de crédit
     *
     * @param creditCard : carte de crédit
     * @param email      : email de l'utilisateur connecté
     * @return : carte de crédit mise à jour
     */
    @PostMapping("/cc")
    public ResponseEntity<?> addCreditCard(@RequestBody CreditCard creditCard,
                                           @CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("addCreditCard({}, {})", creditCard, email);

        try {
            if (creditCard == null) {
                return ResponseEntity.badRequest().body(new ApiResponse("Body are required", 400, "Bad Request", "Body are required", Instant.now()));
            }

            String error = this.checkCreditCardRequest(creditCard);
            if (error != null) {
                return ResponseEntity.badRequest().body(new ApiResponse(error, 400, "Bad Request", error, Instant.now()));
            }

            return ResponseEntity.ok(this.userService.addCreditCard(creditCard, email));
        } catch (Exception e) {
            logger.error("addCreditCard()", e);
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    @DeleteMapping("/cc/{cardId}")
    public ResponseEntity<?> deleteCreditCard(@PathVariable Integer cardId,
                                              @CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("deleteCreditCard({}, {})", cardId, email);

        try {
            if (cardId == null) {
                return ResponseEntity.badRequest().body(new ApiResponse("Card id are required", 400, "Bad Request", "Card id are required", Instant.now()));
            }

            this.userService.deleteCreditCard(cardId, email);

            return ResponseEntity.ok(new ApiResponse("Credit card deleted", 200, null, null, Instant.now()));
        } catch (Exception e) {
            logger.error("deleteCreditCard()", e);
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    /**
     * Vérifie que les informations de l'utilisateur sont correctes
     *
     * @param user : utilisateur
     * @return : erreur ou null
     */
    private String checkRegisterRequest(User user) {
        if (user == null) {
            return "User are required";
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return "Email are required";
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return "Password are required";
        }

        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            return "Last name are required";
        }

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            return "First name are required";
        }

        if (user.getBirthdate() == null) {
            return "Birthdate are required";
        }

        if (user.getSex() == null || user.getSex().isEmpty()) {
            return "Sex are required";
        }

        if (user.getStreet() == null || user.getStreet().isEmpty()) {
            return "Street are required";
        }

        if (user.getZipCode() == null || user.getZipCode().isEmpty()) {
            return "Zip code are required";
        }

        if (user.getCity() == null || user.getCity().isEmpty()) {
            return "City are required";
        }

        if (user.getCountry() == null || user.getCountry().isEmpty()) {
            return "Country are required";
        }

        return null;
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
