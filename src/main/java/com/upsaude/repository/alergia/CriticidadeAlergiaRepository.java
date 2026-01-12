package com.upsaude.repository.alergia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.alergia.CriticidadeAlergia;

@Repository
public interface CriticidadeAlergiaRepository extends JpaRepository<CriticidadeAlergia, UUID> {

    Optional<CriticidadeAlergia> findByCodigoFhir(String codigoFhir);

    boolean existsByCodigoFhir(String codigoFhir);
}
