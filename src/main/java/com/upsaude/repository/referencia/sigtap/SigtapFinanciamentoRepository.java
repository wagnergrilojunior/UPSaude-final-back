package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapFinanciamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SigtapFinanciamentoRepository extends JpaRepository<SigtapFinanciamento, UUID> {
    Optional<SigtapFinanciamento> findByCodigoOficialAndCompetenciaInicial(String codigoOficial, String competenciaInicial);
    boolean existsByCodigoOficialAndCompetenciaInicial(String codigoOficial, String competenciaInicial);
    List<SigtapFinanciamento> findByCodigoOficialIn(List<String> codigos);
}
