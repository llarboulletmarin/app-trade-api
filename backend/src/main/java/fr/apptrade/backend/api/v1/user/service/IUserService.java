package fr.apptrade.backend.api.v1.user.service;

import fr.apptrade.backend.api.v1.currency.model.response.CurrencyResponse;
import fr.apptrade.backend.api.v1.user.model.CreditCard;
import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.model.response.TransactionCardResponse;
import fr.apptrade.backend.api.v1.user.model.response.TransactionResponse;
import fr.apptrade.backend.api.v1.user.model.response.UserResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService {

    /**
     * Inscription d'un utilisateur
     *
     * @param user : utilisateur
     * @throws Exception : erreur
     */
    void register(User user) throws Exception;

    /**
     * Récupération d'un utilisateur par son email
     *
     * @param email : email
     * @return : utilisateur
     */
    UserResponse getUserByEmail(String email) throws UsernameNotFoundException;

    /**
     * Ajout d'une carte de crédit à un utilisateur
     *
     * @param creditCard : carte de crédit
     * @param email      : email de l'utilisateur
     * @return : carte de crédit
     */
    CreditCard addCreditCard(CreditCard creditCard, String email);

    /**
     * Suppression d'une carte de crédit
     *
     * @param id    : id de la carte de crédit
     * @param email : email de l'utilisateur
     * @throws Exception : erreur
     */
    void deleteCreditCard(int id, String email) throws Exception;

    /**
     * Récupération des transactions d'un utilisateur
     *
     * @param email : email de l'utilisateur
     * @return : transactions
     */
    List<TransactionResponse> getTransactions(String email);

    /**
     * Récupération des transactions d'un utilisateur pour une devise
     *
     * @param email : email de l'utilisateur
     * @return : transactions
     */
    List<TransactionResponse> getTransactionsByCode(String email, String code);

    /**
     * Récupération des transactions de carte de crédit d'un utilisateur
     *
     * @param email : email de l'utilisateur
     * @return : transactions de carte de crédit
     */
    List<TransactionCardResponse> getTransactionsCard(String email);

    /**
     * Récupération des transactions de carte de crédit d'un utilisateur pour une carte de crédit
     *
     * @param email : email de l'utilisateur
     * @param cardNumber : numéro de la carte de crédit
     * @return : transactions de carte de crédit
     */
    List<TransactionCardResponse> getTransactionsCardByCardNumber(String email, String cardNumber);

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
