package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapHabilitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface SigtapHabilitacaoRepository extends JpaRepository<SigtapHabilitacao, UUID>, JpaSpecificationExecutor<SigtapHabilitacao> {
    Optional<SigtapHabilitacao> findByCodigoOficial(String codigoOficial);
    Optional<SigtapHabilitacao> findByCodigoOficialAndCompetenciaInicial(String codigoOficial, String competenciaInicial);
}
