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
public class ConvenioCreator {

    private final ConvenioRepository repository;
    private final ConvenioMapper mapper;
    private final ConvenioValidationService validationService;
    private final ConvenioRelacionamentosHandler relacionamentosHandler;

    public Convenio criar(ConvenioRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request, repository, tenantId);

        Convenio convenio = mapper.fromRequest(request);
        convenio.setActive(true);
        convenio.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para criar convênio"));

        relacionamentosHandler.processarRelacionamentos(convenio, request, tenantId);

        Convenio salvo = repository.save(Objects.requireNonNull(convenio));
        log.info("Convênio criado com sucesso. ID: {}, tenant: {}", salvo.getId(), tenantId);
        return salvo;
    }
}

