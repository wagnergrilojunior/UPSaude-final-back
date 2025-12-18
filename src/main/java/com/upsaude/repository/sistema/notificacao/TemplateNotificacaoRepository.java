package com.upsaude.repository.sistema.notificacao;

import com.upsaude.entity.sistema.notificacao.TemplateNotificacao;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT t FROM TemplateNotificacao t WHERE t.id = :id AND t.tenant.id = :tenantId")
    Optional<TemplateNotificacao> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT t FROM TemplateNotificacao t WHERE t.tenant.id = :tenantId ORDER BY t.nome ASC")
    Page<TemplateNotificacao> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<TemplateNotificacao> findByEstabelecimentoIdAndTenantIdOrderByNomeAsc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<TemplateNotificacao> findByTipoNotificacaoAndTenantId(TipoNotificacaoEnum tipoNotificacao, UUID tenantId, Pageable pageable);

    Page<TemplateNotificacao> findByCanalAndTenantId(CanalNotificacaoEnum canal, UUID tenantId, Pageable pageable);

    Page<TemplateNotificacao> findByNomeContainingIgnoreCaseAndTenantId(String nome, UUID tenantId, Pageable pageable);

    Optional<TemplateNotificacao> findByEstabelecimentoIdIsNullAndTipoNotificacaoAndCanalAndTenantIdAndActiveTrue(
        TipoNotificacaoEnum tipoNotificacao, CanalNotificacaoEnum canal, UUID tenantId);

    Optional<TemplateNotificacao> findByEstabelecimentoIdAndTipoNotificacaoAndCanalAndTenantIdAndActiveTrue(
        UUID estabelecimentoId, TipoNotificacaoEnum tipoNotificacao, CanalNotificacaoEnum canal, UUID tenantId);
}
