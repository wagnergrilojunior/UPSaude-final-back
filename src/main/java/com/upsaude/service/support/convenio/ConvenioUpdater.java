package com.upsaude.service.support.convenio;

import com.upsaude.api.request.convenio.ConvenioRequest;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.ConvenioMapper;
import com.upsaude.repository.convenio.ConvenioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvenioUpdater {

    private final ConvenioRepository repository;
    private final ConvenioMapper mapper;
    private final ConvenioTenantEnforcer tenantEnforcer;
    private final ConvenioValidationService validationService;
    private final ConvenioRelacionamentosHandler relacionamentosHandler;

    public Convenio atualizar(UUID id, ConvenioRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Convenio existente = tenantEnforcer.validarAcesso(id, tenantId);
        validationService.validarUnicidadeParaAtualizacao(id, request, repository, tenantId);

        mapper.updateFromRequest(request, existente);
        existente.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para atualizar convênio"));

        relacionamentosHandler.processarRelacionamentos(existente, request, tenantId);

        Convenio salvo = repository.save(Objects.requireNonNull(existente));
        log.info("Convênio atualizado com sucesso. ID: {}, tenant: {}", salvo.getId(), tenantId);
        return salvo;
    }
}

