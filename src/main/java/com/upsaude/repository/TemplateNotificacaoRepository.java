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

/**
 * Repositório para operações de banco de dados relacionadas a TemplateNotificacao.
 *
 * @author UPSaúde
 */
@Repository
public interface TemplateNotificacaoRepository extends JpaRepository<TemplateNotificacao, UUID> {

    /**
     * Busca todos os templates de um estabelecimento, ordenados por nome.
     */
    Page<TemplateNotificacao> findByEstabelecimentoIdOrderByNomeAsc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca templates por tipo de notificação e canal.
     */
    List<TemplateNotificacao> findByTipoNotificacaoAndCanal(
            TipoNotificacaoEnum tipoNotificacao, CanalNotificacaoEnum canal);

    /**
     * Busca template padrão (sem estabelecimento específico) por tipo e canal.
     */
    Optional<TemplateNotificacao> findByEstabelecimentoIdIsNullAndTipoNotificacaoAndCanalAndActiveTrue(
            TipoNotificacaoEnum tipoNotificacao, CanalNotificacaoEnum canal);

    /**
     * Busca template específico de um estabelecimento por tipo e canal.
     */
    Optional<TemplateNotificacao> findByEstabelecimentoIdAndTipoNotificacaoAndCanalAndActiveTrue(
            UUID estabelecimentoId, TipoNotificacaoEnum tipoNotificacao, CanalNotificacaoEnum canal);

    /**
     * Busca todos os templates ativos de um estabelecimento.
     */
    List<TemplateNotificacao> findByEstabelecimentoIdAndActiveTrueOrderByNomeAsc(UUID estabelecimentoId);

    /**
     * Busca todos os templates de um tenant.
     */
    Page<TemplateNotificacao> findByTenantOrderByNomeAsc(Tenant tenant, Pageable pageable);
}

