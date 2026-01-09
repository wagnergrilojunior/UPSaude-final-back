package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapCompatibilidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface SigtapCompatibilidadeRepository extends JpaRepository<SigtapCompatibilidade, UUID>, JpaSpecificationExecutor<SigtapCompatibilidade> {

    Optional<SigtapCompatibilidade> findByCompatibilidadePossivelIdAndProcedimentoPrincipalIdAndProcedimentoSecundarioIdAndCompetenciaInicial(
            UUID compatibilidadePossivelId,
            UUID procedimentoPrincipalId,
            UUID procedimentoSecundarioId,
            String competenciaInicial
    );

    Page<SigtapCompatibilidade> findByProcedimentoPrincipalCodigoOficial(String codigoProcedimentoPrincipal, Pageable pageable);
}

