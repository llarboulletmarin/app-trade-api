package fr.apptrade.backend.api.v1.currency.controller;

import fr.apptrade.backend.api.v1.config.model.ApiResponse;
import fr.apptrade.backend.api.v1.currency.model.response.CurrencyResponse;
import fr.apptrade.backend.api.v1.currency.service.ICurrencyService;
import fr.apptrade.backend.api.v1.user.model.request.TransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("${api.base-url.v1}/currencies")
public class CurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private final ICurrencyService currencyService;

    @Autowired
    public CurrencyController(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * Endpoint de récupération des devises (toutes)
     *
     * @return la liste des devises
     */
    @GetMapping("")
    public ResponseEntity<?> getCurrencies() {
        logger.info("getCurrencies()");
        return ResponseEntity.ok(this.currencyService.getCurrencies());
    }

    /**
     * Endpoint de récupération d'une devise par son code
     *
     * @param currencyCode : code de la devise
     * @return la devise
     */
    @GetMapping("/{currencyCode}")
    public ResponseEntity<?> getCurrencyByCode(@PathVariable String currencyCode) {
        logger.info("getCurrencyById({})", currencyCode);

        List<CurrencyResponse> currencies = this.currencyService.getCurrencyByCode(currencyCode);
        if (currencies.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Error while getting currency", 404, "Currency with code " + currencyCode + " not found", null, Instant.now()));
        }

        return ResponseEntity.ok(currencies);
    }

    /**
     * Endpoint de récupération de l'historique d'une devise par son code
     *
     * @param currencyCode : code de la devise
     * @param days         : nombre de jours à récupérer
     * @return l'historique de la devise
     */
    @GetMapping("/{currencyCode}/history")
    public ResponseEntity<?> getCurrencyHistoryByCode(@PathVariable String currencyCode,
                                                      @RequestParam(required = false) Integer days) {
        logger.info("getCurrencyHistoryByCode({})", currencyCode);

        try {
            if (days == null) {
                days = 7;
            }

            return ResponseEntity.ok(this.currencyService.getCurrencyHistoryByCode(currencyCode, days));
        } catch (Exception e) {
            logger.error("Error while getting currency history", e);
            return ResponseEntity.badRequest().body(new ApiResponse("Error while getting currency history", 404, e.getMessage(), null, Instant.now()));
        }
    }

    /**
     * Endpoint permettant d'acheter une devise
     *
     * @param currencyCode : code de la devise
     * @param buyRequest   : montant à acheter
     * @param email        : email de l'utilisateur connecté
     * @return : montant acheté
     */
    @PostMapping("/{currencyCode}/buy")
    public ResponseEntity<?> buyCurrency(@PathVariable String currencyCode,
                                         @RequestBody TransactionRequest buyRequest,
                                         @CurrentSecurityContext(expression = "authentication.name") String email) {
        logger.info("buyCurrency({}, currencyCode: {}, buyRequest: {})", email, currencyCode, buyRequest);

        try {
            if (BigDecimal.ZERO.compareTo(buyRequest.getAmount()) >= 0) {
                throw new RuntimeException("Amount must be greater than 0");
            }

            return ResponseEntity.ok(this.currencyService.buyCurrency(email, currencyCode, buyRequest));
        } catch (Exception e) {
            logger.error("buyCurrency({}, currencyCode: {}, buyRequest: {}, exception: {})", email, currencyCode, buyRequest, e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), 400, "Bad Request", e.getLocalizedMessage(), Instant.now()));
        }
    }
}
