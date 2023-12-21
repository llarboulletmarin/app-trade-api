package fr.apptrade.backend.api.v1.user.controller;

import fr.apptrade.backend.api.v1.config.model.ApiResponse;
import fr.apptrade.backend.api.v1.user.service.IFavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("${api.base-url.v1}/user/favorites")
public class FavoriteController {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    private final IFavoriteService favoriteService;

    @Autowired
    public FavoriteController(IFavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * Endpoint de récupération des favoris de l'utilisateur
     *
     * @param email : email de l'utilisateur connecté
     * @return : favoris de l'utilisateur
     */
    @GetMapping("")
    public ResponseEntity<?> getFavorites(@CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("getFavorites({})", email);

        try {
            return ResponseEntity.ok(this.favoriteService.getFavorites(email));
        } catch (Exception e) {
            logger.error("getFavorites({}, exception: {})", email, e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    /**
     * Permet d'ajouter un nouveau favoris
     *
     * @param email        : email de l'utilisateur connecté
     * @param currencyCode : code de la devise
     * @return : favoris de l'utilisateur
     */
    @PostMapping("/{currencyCode}")
    public ResponseEntity<?> addFavorite(@PathVariable String currencyCode,
                                         @CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("addFavorite({}, currencyCode: {})", email, currencyCode);

        try {
            if (currencyCode == null || currencyCode.isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse("Currency code are required", 400, "Bad Request", "Currency code are required", Instant.now()));
            }

            return ResponseEntity.ok(this.favoriteService.addFavorite(email, currencyCode));
        } catch (Exception e) {
            logger.error("addFavorite({}, currencyCode: {}, exception: {})", email, currencyCode, e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

    /**
     * Permet de retirer un favoris
     *
     * @param email        : email de l'utilisateur connecté
     * @param currencyCode : code de la devise
     * @return : ok ou erreur
     */
    @DeleteMapping("/{currencyCode}")
    public ResponseEntity<?> deleteFavorite(@PathVariable String currencyCode,
                                            @CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("deleteFavorite({}, currencyCode: {})", email, currencyCode);

        try {
            if (currencyCode == null || currencyCode.isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse("Currency code are required", 400, "Bad Request", "Currency code are required", Instant.now()));
            }

            this.favoriteService.deleteFavorite(email, currencyCode);

            return ResponseEntity.ok(new ApiResponse("Favorite deleted", 200, null, null, Instant.now()));
        } catch (Exception e) {
            logger.error("deleteFavorite({}, currencyCode: {}, exception: {})", email, currencyCode, e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }

}
