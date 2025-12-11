package com.upsaude.repository;

import com.upsaude.entity.TemplateNotificacao;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateNotificacaoRepository extends JpaRepository<TemplateNotificacao, UUID> {

    Page<TemplateNotificacao> findByEstabelecimentoIdOrderByNomeAsc(UUID estabelecimentoId, Pageable pageable);

    List<TemplateNotificacao> findByTipoNotificacaoAndCanal(
            TipoNotificacaoEnum tipoNotificacao, CanalNotificacaoEnum canal);

    Optional<TemplateNotificacao> findByEstabelecimentoIdIsNullAndTipoNotificacaoAndCanalAndActiveTrue(
            TipoNotificacaoEnum tipoNotificacao, CanalNotificacaoEnum canal);

    Optional<TemplateNotificacao> findByEstabelecimentoIdAndTipoNotificacaoAndCanalAndActiveTrue(
            UUID estabelecimentoId, TipoNotificacaoEnum tipoNotificacao, CanalNotificacaoEnum canal);

    List<TemplateNotificacao> findByEstabelecimentoIdAndActiveTrueOrderByNomeAsc(UUID estabelecimentoId);

    Page<TemplateNotificacao> findByTenantOrderByNomeAsc(Tenant tenant, Pageable pageable);
}
