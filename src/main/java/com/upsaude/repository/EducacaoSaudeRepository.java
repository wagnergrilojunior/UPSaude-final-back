package com.upsaude.repository;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.EducacaoSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoEducacaoSaudeEnum;

public interface EducacaoSaudeRepository extends JpaRepository<EducacaoSaude, UUID> {

    Page<EducacaoSaude> findByEstabelecimentoIdOrderByDataHoraInicioDesc(UUID estabelecimentoId, Pageable pageable);

    Page<EducacaoSaude> findByTenantOrderByDataHoraInicioDesc(Tenant tenant, Pageable pageable);

    Page<EducacaoSaude> findByTipoAtividadeAndEstabelecimentoIdOrderByDataHoraInicioDesc(TipoEducacaoSaudeEnum tipo, UUID estabelecimentoId, Pageable pageable);

    Page<EducacaoSaude> findByDataHoraInicioBetweenAndEstabelecimentoIdOrderByDataHoraInicioDesc(OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID estabelecimentoId, Pageable pageable);

    Page<EducacaoSaude> findByAtividadeRealizadaAndEstabelecimentoIdOrderByDataHoraInicioDesc(Boolean realizada, UUID estabelecimentoId, Pageable pageable);

    Page<EducacaoSaude> findByProfissionalResponsavelIdOrderByDataHoraInicioDesc(UUID profissionalId, Pageable pageable);
}
