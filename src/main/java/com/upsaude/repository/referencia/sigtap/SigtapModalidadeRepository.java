package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapModalidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SigtapModalidadeRepository extends JpaRepository<SigtapModalidade, UUID> {
    Optional<SigtapModalidade> findByCodigoOficialAndCompetenciaInicial(String codigoOficial, String competenciaInicial);
}
