package fr.apptrade.backend.api.v1.user.controller;

import fr.apptrade.backend.api.v1.config.model.ApiResponse;
import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.service.IRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("${api.base-url.v1}/user/register")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final IRegistrationService registrationService;

    @Autowired
    public RegistrationController(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Endpoint d'inscription
     *
     * @param user : utilisateur
     * @return : ok ou erreur
     */
    @PostMapping("")
    public ResponseEntity<?> register(@RequestBody User user) {
        logger.info("register({})", this.userToString(user));

        try {
            String error = this.checkRegisterRequest(user);
            if (error != null) {
                return ResponseEntity.badRequest().body(new ApiResponse(error, 400, "Bad Request", error, Instant.now()));
            }

            this.registrationService.register(user);
            return ResponseEntity.ok(new ApiResponse("User created", 200, null, null, Instant.now()));
        } catch (Exception e) {
            logger.error("register({}, exception: {})", this.userToString(user), e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    /**
     * Permet de ne pas afficher le mot de passe dans les logs
     *
     * @param user : utilisateur
     * @return : utilisateur en string
     */
    private String userToString(User user) {
        if (user == null) {
            return "null";
        }
        return user.toString().replaceAll("password=.*?,", "password=***,");
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

}
