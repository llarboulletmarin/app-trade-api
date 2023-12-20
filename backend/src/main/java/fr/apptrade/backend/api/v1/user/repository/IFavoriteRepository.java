package fr.apptrade.backend.api.v1.user.repository;

import fr.apptrade.backend.api.v1.user.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IFavoriteRepository extends JpaRepository<Favorite, Integer> {
    List<Favorite> findAllByFkidUser(Integer fkidUser);

    Optional<Favorite> findByFkidUserAndCurrencyCode(Integer fkidUser, String currencyCode);
}
