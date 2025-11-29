package com.upsaude.repository;

import com.upsaude.entity.Notificacao;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a Notificacao.
 *
 * @author UPSaúde
 */
@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, UUID> {

    /**
     * Busca todas as notificações de um paciente, ordenadas por data de envio decrescente.
     */
    Page<Notificacao> findByPacienteIdOrderByDataEnvioDesc(UUID pacienteId, Pageable pageable);

    /**
     * Busca todas as notificações de um profissional, ordenadas por data de envio decrescente.
     */
    Page<Notificacao> findByProfissionalIdOrderByDataEnvioDesc(UUID profissionalId, Pageable pageable);

    /**
     * Busca todas as notificações de um agendamento, ordenadas por data de envio decrescente.
     */
    Page<Notificacao> findByAgendamentoIdOrderByDataEnvioDesc(UUID agendamentoId, Pageable pageable);

    /**
     * Busca todas as notificações por tipo, ordenadas por data de envio decrescente.
     */
    Page<Notificacao> findByTipoNotificacaoOrderByDataEnvioDesc(TipoNotificacaoEnum tipoNotificacao, Pageable pageable);

    /**
     * Busca todas as notificações por canal, ordenadas por data de envio decrescente.
     */
    Page<Notificacao> findByCanalOrderByDataEnvioDesc(CanalNotificacaoEnum canal, Pageable pageable);

    /**
     * Busca todas as notificações por status de envio, ordenadas por data de envio decrescente.
     */
    Page<Notificacao> findByStatusEnvioOrderByDataEnvioDesc(String statusEnvio, Pageable pageable);

    /**
     * Busca todas as notificações de um estabelecimento, ordenadas por data de envio decrescente.
     */
    Page<Notificacao> findByEstabelecimentoIdOrderByDataEnvioDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as notificações enviadas em um período.
     */
    Page<Notificacao> findByDataEnvioBetweenOrderByDataEnvioDesc(
            OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    /**
     * Busca todas as notificações de um tenant.
     */
    Page<Notificacao> findByTenantOrderByDataEnvioDesc(Tenant tenant, Pageable pageable);
}

