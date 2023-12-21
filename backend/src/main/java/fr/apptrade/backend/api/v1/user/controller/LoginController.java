package fr.apptrade.backend.api.v1.user.controller;

import fr.apptrade.backend.api.v1.config.model.ApiResponse;
import fr.apptrade.backend.api.v1.user.service.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("${api.base-url.v1}/user/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final ILoginService loginService;

    @Autowired
    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Endpoint de connexion (est utilisé en réalité pour récupérer les informations de l'utilisateur)
     *
     * @param email : email
     * @return : informations de l'utilisateur
     */
    @PostMapping("")
    public ResponseEntity<?> login(@CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("login({})", email);

        try {
            return ResponseEntity.ok(this.loginService.login(email));
        } catch (Exception e) {
            logger.error("login({}, exception: {})", email, e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

}
