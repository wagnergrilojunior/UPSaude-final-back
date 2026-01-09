package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapModalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface SigtapModalidadeRepository extends JpaRepository<SigtapModalidade, UUID>, JpaSpecificationExecutor<SigtapModalidade> {
    Optional<SigtapModalidade> findByCodigoOficialAndCompetenciaInicial(String codigoOficial, String competenciaInicial);
}
