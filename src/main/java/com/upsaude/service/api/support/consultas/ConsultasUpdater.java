package com.upsaude.service.api.support.consultas;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.atendimento.ConsultaRequest;
import com.upsaude.entity.clinica.atendimento.Consulta;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.clinica.atendimento.ConsultaMapper;
import com.upsaude.repository.clinica.atendimento.ConsultasRepository;
import com.upsaude.service.sistema.integracao.IntegracaoEventoGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultasUpdater {

    private final ConsultasRepository repository;
    private final ConsultaMapper mapper;
    private final ConsultasTenantEnforcer tenantEnforcer;
    private final ConsultasValidationService validationService;
    private final ConsultasRelacionamentosHandler relacionamentosHandler;
    private final ConsultasDomainService domainService;
    private final IntegracaoEventoGenerator eventoGenerator;

    public Consulta atualizar(UUID id, ConsultaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Consulta entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (request.getInformacoes() != null && request.getInformacoes().getStatusConsulta() != null) {
            com.upsaude.enums.StatusConsultaEnum statusAtual = entity.getInformacoes() != null ? entity.getInformacoes().getStatusConsulta() : null;
            if (statusAtual != null && !statusAtual.equals(request.getInformacoes().getStatusConsulta())) {
                domainService.validarTransicaoStatus(entity, request.getInformacoes().getStatusConsulta());
            }
        }

        mapper.updateFromRequest(request, entity);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Consulta saved = repository.save(Objects.requireNonNull(entity));
        eventoGenerator.gerarEventosParaConsulta(saved);
        log.info("Consulta atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
