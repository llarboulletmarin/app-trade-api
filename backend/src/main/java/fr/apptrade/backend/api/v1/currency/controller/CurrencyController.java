package fr.apptrade.backend.api.v1.currency.controller;

import fr.apptrade.backend.api.v1.currency.service.ICurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(this.currencyService.getCurrencyByCode(code));
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

        if (days == null) {
            days = 7;
        }

        return ResponseEntity.ok(this.currencyService.getCurrencyHistoryByCode(code, days));
    }
}
