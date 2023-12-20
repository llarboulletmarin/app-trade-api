package fr.apptrade.backend.api.v1.user.controller;

import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.model.request.LoginRequest;
import fr.apptrade.backend.api.v1.user.service.IUserService;
import fr.apptrade.backend.api.v1.user.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            if (user == null) {
                return ResponseEntity.badRequest().body("Body are required");
            }

            String error = this.checkRegisterRequest(user);
            if (error != null) {
                return ResponseEntity.badRequest().body(error);
            }

            this.userService.register(user);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("login()", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint de connexion (est utilisé en réalité pour récupérer les informations de l'utilisateur)
     *
     * @param body : email
     * @return : informations de l'utilisateur
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest body) {
        logger.info("login({})", body);

        try {
            if (body == null || body.getEmail() == null || body.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("Email are required");
            }

            return ResponseEntity.ok(this.userService.getUserByEmail(body.getEmail()));
        } catch (Exception e) {
            logger.error("login()", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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
