package fr.apptrade.backend.api.repository;

import fr.apptrade.backend.api.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICurrencyRepository extends JpaRepository<Currency, Integer> {

    List<Currency> findByCode(String code);

}
