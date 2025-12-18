package com.upsaude.repository.sistema.notificacao;

import com.upsaude.entity.sistema.notificacao.Notificacao;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, UUID> {

    Page<Notificacao> findByPacienteIdOrderByDataEnvioDesc(UUID pacienteId, Pageable pageable);

    Page<Notificacao> findByProfissionalIdOrderByDataEnvioDesc(UUID profissionalId, Pageable pageable);

    Page<Notificacao> findByAgendamentoIdOrderByDataEnvioDesc(UUID agendamentoId, Pageable pageable);

    Page<Notificacao> findByTipoNotificacaoOrderByDataEnvioDesc(TipoNotificacaoEnum tipoNotificacao, Pageable pageable);

    Page<Notificacao> findByCanalOrderByDataEnvioDesc(CanalNotificacaoEnum canal, Pageable pageable);

    Page<Notificacao> findByStatusEnvioOrderByDataEnvioDesc(String statusEnvio, Pageable pageable);

    Page<Notificacao> findByEstabelecimentoIdOrderByDataEnvioDesc(UUID estabelecimentoId, Pageable pageable);

    Page<Notificacao> findByDataEnvioBetweenOrderByDataEnvioDesc(
            OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<Notificacao> findByTenantOrderByDataEnvioDesc(Tenant tenant, Pageable pageable);

    @Query("SELECT n FROM Notificacao n WHERE n.id = :id AND n.tenant.id = :tenantId")
    Optional<Notificacao> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT n FROM Notificacao n WHERE n.tenant.id = :tenantId ORDER BY n.dataEnvio DESC")
    Page<Notificacao> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<Notificacao> findByEstabelecimentoIdAndTenantIdOrderByDataEnvioDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<Notificacao> findByPacienteIdAndTenantIdOrderByDataEnvioDesc(UUID pacienteId, UUID tenantId, Pageable pageable);

    Page<Notificacao> findByProfissionalIdAndTenantIdOrderByDataEnvioDesc(UUID profissionalId, UUID tenantId, Pageable pageable);

    Page<Notificacao> findByAgendamentoIdAndTenantIdOrderByDataEnvioDesc(UUID agendamentoId, UUID tenantId, Pageable pageable);

    Page<Notificacao> findByStatusEnvioAndTenantIdOrderByDataEnvioDesc(String statusEnvio, UUID tenantId, Pageable pageable);

    Page<Notificacao> findByDataEnvioBetweenAndTenantIdOrderByDataEnvioDesc(OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID tenantId, Pageable pageable);

    Page<Notificacao> findByDataEnvioPrevistaBetweenAndTenantIdOrderByDataEnvioPrevistaDesc(OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID tenantId, Pageable pageable);
}
