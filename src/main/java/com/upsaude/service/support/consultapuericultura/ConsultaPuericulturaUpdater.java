package com.upsaude.service.support.consultapuericultura;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.atendimento.ConsultaPuericulturaRequest;
import com.upsaude.entity.clinica.atendimento.ConsultaPuericultura;
import com.upsaude.entity.saude_publica.puericultura.Puericultura;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.clinica.atendimento.ConsultaPuericulturaMapper;
import com.upsaude.repository.clinica.atendimento.ConsultaPuericulturaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaPuericulturaUpdater {

    private final ConsultaPuericulturaRepository repository;
    private final ConsultaPuericulturaMapper mapper;
    private final ConsultaPuericulturaTenantEnforcer tenantEnforcer;
    private final ConsultaPuericulturaValidationService validationService;
    private final ConsultaPuericulturaRelacionamentosHandler relacionamentosHandler;
    private final ConsultaPuericulturaDomainService domainService;

    public ConsultaPuericultura atualizar(UUID id, ConsultaPuericulturaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        ConsultaPuericultura entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);

        ConsultaPuericultura saved = repository.save(Objects.requireNonNull(entity));

        Puericultura puericultura = saved.getPuericultura();
        domainService.recalcularNumeroConsultas(puericultura, tenantId);

        log.info("Consulta de puericultura atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
