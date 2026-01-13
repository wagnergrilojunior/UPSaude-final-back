package com.upsaude.service.sistema.integracao;

import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.sistema.integracao.IntegracaoEvento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.SistemaIntegracaoEnum;
import com.upsaude.enums.StatusIntegracaoEventoEnum;
import com.upsaude.enums.TipoRecursoIntegracaoEnum;
import com.upsaude.repository.sistema.integracao.IntegracaoEventoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegracaoEventoService {

    private final IntegracaoEventoRepository repository;

    @Transactional
    public IntegracaoEvento criarEvento(
            TipoRecursoIntegracaoEnum entidadeTipo,
            UUID entidadeId,
            SistemaIntegracaoEnum sistema,
            String recurso,
            Tenant tenant,
            Estabelecimentos estabelecimento,
            String payloadRequest,
            String correlationId
    ) {
        Integer proximaVersao = repository.findMaxVersao(entidadeTipo, entidadeId, sistema, recurso);
        if (proximaVersao == null) {
            proximaVersao = 0;
        }
        proximaVersao = proximaVersao + 1;

        IntegracaoEvento evento = new IntegracaoEvento();
        evento.setEntidadeTipo(entidadeTipo);
        evento.setEntidadeId(entidadeId);
        evento.setSistema(sistema);
        evento.setRecurso(recurso);
        evento.setVersao(proximaVersao);
        evento.setStatus(StatusIntegracaoEventoEnum.PENDENTE);
        evento.setPayloadRequest(payloadRequest);
        evento.setCorrelationId(correlationId != null ? correlationId : UUID.randomUUID().toString());
        evento.setTenant(tenant);
        evento.setEstabelecimento(estabelecimento);

        IntegracaoEvento saved = repository.save(evento);
        log.info("Evento de integração criado: {} - {} - {} - versão {}", entidadeTipo, entidadeId, sistema, proximaVersao);
        return saved;
    }

    @Transactional
    public IntegracaoEvento atualizarEvento(IntegracaoEvento evento) {
        return repository.save(evento);
    }

    public Optional<IntegracaoEvento> buscarUltimaVersao(
            TipoRecursoIntegracaoEnum entidadeTipo,
            UUID entidadeId,
            SistemaIntegracaoEnum sistema,
            String recurso
    ) {
        return repository.findFirstByEntidadeTipoAndEntidadeIdAndSistemaAndRecursoOrderByVersaoDesc(
                entidadeTipo, entidadeId, sistema, recurso
        );
    }

    public boolean existeEventoRecente(
            TipoRecursoIntegracaoEnum entidadeTipo,
            UUID entidadeId,
            SistemaIntegracaoEnum sistema,
            String recurso,
            int horasProtecao
    ) {
        Optional<IntegracaoEvento> ultimoEvento = buscarUltimaVersao(entidadeTipo, entidadeId, sistema, recurso);
        if (ultimoEvento.isEmpty()) {
            return false;
        }
        OffsetDateTime limite = OffsetDateTime.now().minusHours(horasProtecao);
        return ultimoEvento.get().getCreatedAt().isAfter(limite);
    }
}
