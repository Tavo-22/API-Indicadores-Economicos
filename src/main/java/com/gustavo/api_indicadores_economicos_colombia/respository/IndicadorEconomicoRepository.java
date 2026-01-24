package com.gustavo.api_indicadores_economicos_colombia.respository;

import com.gustavo.api_indicadores_economicos_colombia.model.IndicadorEconomico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndicadorEconomicoRepository extends JpaRepository<IndicadorEconomico, Long> {

    Optional<IndicadorEconomico> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);
}
