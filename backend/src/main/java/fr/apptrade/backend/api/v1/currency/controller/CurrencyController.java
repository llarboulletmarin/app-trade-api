package fr.apptrade.backend.api.v1.currency.controller;

import fr.apptrade.backend.api.v1.config.model.ApiResponse;
import fr.apptrade.backend.api.v1.currency.model.Currency;
import fr.apptrade.backend.api.v1.currency.service.ICurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param code : code de la devise
     * @return la devise
     */
    @GetMapping("/{code}")
    public ResponseEntity<?> getCurrencyByCode(@PathVariable String code) {
        logger.info("getCurrencyById()");

        List<Currency> currencies = this.currencyService.getCurrencyByCode(code);
        if (currencies.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Error while getting currency", 404, "Currency with code " + code + " not found", null, Instant.now()));
        }

        return ResponseEntity.ok(currencies);
    }

    /**
     * Endpoint de récupération de l'historique d'une devise par son code
     *
     * @param code : code de la devise
     * @param days : nombre de jours à récupérer
     * @return l'historique de la devise
     */
    @GetMapping("/{code}/history")
    public ResponseEntity<?> getCurrencyHistoryByCode(@PathVariable String code,
                                                      @RequestParam(required = false) Integer days) {
        logger.info("getCurrencyHistoryByCode()");

        try {
            if (days == null) {
                days = 7;
            }

            return ResponseEntity.ok(this.currencyService.getCurrencyHistoryByCode(code, days));
        } catch (Exception e) {
            logger.error("Error while getting currency history", e);
            return ResponseEntity.badRequest().body(new ApiResponse("Error while getting currency history", 404, e.getMessage(), null, Instant.now()));
        }
    }
}
