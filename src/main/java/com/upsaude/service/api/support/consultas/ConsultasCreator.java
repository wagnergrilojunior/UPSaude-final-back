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
public class ConsultasCreator {

    private final ConsultasRepository repository;
    private final ConsultaMapper mapper;
    private final ConsultasValidationService validationService;
    private final ConsultasRelacionamentosHandler relacionamentosHandler;
    private final ConsultasDomainService domainService;
    private final IntegracaoEventoGenerator eventoGenerator;

    public Consulta criar(ConsultaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Consulta entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Consulta saved = repository.save(Objects.requireNonNull(entity));
        eventoGenerator.gerarEventosParaConsulta(saved);
        log.info("Consulta criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
