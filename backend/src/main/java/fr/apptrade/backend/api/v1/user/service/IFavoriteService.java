package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.currency.model.response.CurrencyResponse;

import java.util.List;

public interface IFavoriteService {

    /**
     * Récupération des favoris d'un utilisateur
     *
     * @param email : email de l'utilisateur
     * @return : favoris
     */
    List<CurrencyResponse> getFavorites(String email);

    /**
     * Ajout d'un favori à un utilisateur
     *
     * @param email : email de l'utilisateur
     * @param code  : code de la devise
     * @return : devise
     */
    CurrencyResponse addFavorite(String email, String code);

    /**
     * Suppression d'un favori à un utilisateur
     *
     * @param email : email de l'utilisateur
     * @param code  : code de la devise
     */
    void deleteFavorite(String email, String code) throws Exception;

}
