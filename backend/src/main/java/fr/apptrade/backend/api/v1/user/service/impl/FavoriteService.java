package fr.apptrade.backend.api.v1.user.service.impl;

import fr.apptrade.backend.api.v1.currency.model.Currency;
import fr.apptrade.backend.api.v1.currency.model.response.CurrencyResponse;
import fr.apptrade.backend.api.v1.currency.service.ICurrencyService;
import fr.apptrade.backend.api.v1.user.model.Favorite;
import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.repository.IFavoriteRepository;
import fr.apptrade.backend.api.v1.user.service.IFavoriteService;
import fr.apptrade.backend.api.v1.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService implements IFavoriteService {

    private final IFavoriteRepository favoriteRepository;
    private final IUserService userService;
    private final ICurrencyService currencyService;

    @Autowired
    public FavoriteService(IFavoriteRepository favoriteRepository,
                           IUserService userService,
                           ICurrencyService currencyService) {
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
        this.currencyService = currencyService;
    }

    @Override
    public List<CurrencyResponse> getFavorites(String email) {
        User user = this.userService.getUserByEmail(email);

        List<Favorite> favorites = this.favoriteRepository.findAllByFkidUser(user.getId());

        return favorites.stream()
                .map(favorite -> new CurrencyResponse(favorite.getCurrency()))
                .toList();
    }

    @Override
    public CurrencyResponse addFavorite(String email, String code) {
        User user = this.userService.getUserByEmail(email);

        if (this.favoriteRepository.findByFkidUserAndCurrencyCode(user.getId(), code).isPresent()) {
            throw new IllegalArgumentException("Currency already in favorite");
        }

        Currency currency = this.currencyService.findCurrencyByCode(code);
        Favorite favorite = new Favorite();
        favorite.setFkidUser(user.getId());
        favorite.setCurrency(currency);

        return new CurrencyResponse(this.favoriteRepository.save(favorite).getCurrency());
    }

    @Override
    public void deleteFavorite(String email, String code) throws Exception {
        User user = this.userService.getUserByEmail(email);
        Favorite favorite = this.favoriteRepository.findByFkidUserAndCurrencyCode(user.getId(), code).orElseThrow(() -> new Exception("Favorite not found"));

        this.favoriteRepository.delete(favorite);
    }

}
