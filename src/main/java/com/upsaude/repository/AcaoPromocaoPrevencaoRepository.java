package com.upsaude.repository;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.AcaoPromocaoPrevencao;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoAcaoPromocaoSaudeEnum;

public interface AcaoPromocaoPrevencaoRepository extends JpaRepository<AcaoPromocaoPrevencao, UUID> {

    Page<AcaoPromocaoPrevencao> findByEstabelecimentoIdOrderByDataInicioDesc(UUID estabelecimentoId, Pageable pageable);

    Page<AcaoPromocaoPrevencao> findByTenantOrderByDataInicioDesc(Tenant tenant, Pageable pageable);

    Page<AcaoPromocaoPrevencao> findByTipoAcaoAndEstabelecimentoIdOrderByDataInicioDesc(TipoAcaoPromocaoSaudeEnum tipo, UUID estabelecimentoId, Pageable pageable);

    Page<AcaoPromocaoPrevencao> findByStatusAcaoAndEstabelecimentoIdOrderByDataInicioDesc(String status, UUID estabelecimentoId, Pageable pageable);

    Page<AcaoPromocaoPrevencao> findByDataInicioBetweenAndEstabelecimentoIdOrderByDataInicioDesc(LocalDate dataInicio, LocalDate dataFim, UUID estabelecimentoId, Pageable pageable);

    Page<AcaoPromocaoPrevencao> findByAcaoContinuaAndEstabelecimentoIdOrderByDataInicioDesc(Boolean continua, UUID estabelecimentoId, Pageable pageable);

    Page<AcaoPromocaoPrevencao> findByProfissionalResponsavelIdOrderByDataInicioDesc(UUID profissionalId, Pageable pageable);
}
