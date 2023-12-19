package fr.apptrade.backend.api.controller;

import fr.apptrade.backend.api.service.ICurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private final ICurrencyService currencyService;

    @Autowired
    public CurrencyController(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("")
    public ResponseEntity<?> getCurrency(@RequestParam(required = false) String code) {
        if (code != null && !code.isEmpty()) {
            logger.info("getCurrencyByCode({})", code);
            return ResponseEntity.ok(this.currencyService.getCurrencyByCode(code));
        } else {
            logger.info("getCurrency()");
            return ResponseEntity.ok(this.currencyService.getCurrency());
        }
    }
}
