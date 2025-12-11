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
}
