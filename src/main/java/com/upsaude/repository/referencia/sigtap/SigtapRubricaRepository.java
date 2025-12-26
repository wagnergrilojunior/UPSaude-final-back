package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapRubrica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SigtapRubricaRepository extends JpaRepository<SigtapRubrica, UUID> {
    Optional<SigtapRubrica> findByCodigoOficialAndCompetenciaInicial(String codigoOficial, String competenciaInicial);
}
