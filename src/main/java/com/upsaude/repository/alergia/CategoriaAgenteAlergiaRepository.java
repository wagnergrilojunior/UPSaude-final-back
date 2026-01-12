package com.upsaude.repository.alergia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.alergia.CategoriaAgenteAlergia;

@Repository
public interface CategoriaAgenteAlergiaRepository extends JpaRepository<CategoriaAgenteAlergia, UUID> {

    Optional<CategoriaAgenteAlergia> findByCodigoFhir(String codigoFhir);

    boolean existsByCodigoFhir(String codigoFhir);
}
