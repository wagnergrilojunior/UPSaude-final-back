package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SigtapDetalheRepository extends JpaRepository<SigtapDetalhe, UUID> {
    Optional<SigtapDetalhe> findByCodigoOficialAndCompetenciaInicial(String codigoOficial, String competenciaInicial);
    boolean existsByCodigoOficialAndCompetenciaInicial(String codigoOficial, String competenciaInicial);
    List<SigtapDetalhe> findByCodigoOficialIn(List<String> codigos);
}
