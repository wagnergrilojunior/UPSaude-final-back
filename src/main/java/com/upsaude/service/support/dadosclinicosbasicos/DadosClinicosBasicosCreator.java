package com.upsaude.service.support.dadosclinicosbasicos;

import com.upsaude.api.request.paciente.DadosClinicosBasicosRequest;
import com.upsaude.entity.paciente.DadosClinicosBasicos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.paciente.DadosClinicosBasicosMapper;
import com.upsaude.repository.paciente.DadosClinicosBasicosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DadosClinicosBasicosCreator {

    private final DadosClinicosBasicosRepository repository;
    private final DadosClinicosBasicosMapper mapper;
    private final DadosClinicosBasicosValidationService validationService;
    private final DadosClinicosBasicosRelacionamentosHandler relacionamentosHandler;

    public DadosClinicosBasicos criar(DadosClinicosBasicosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarDuplicidadeParaCriacao(request.getPaciente(), tenantId);

        DadosClinicosBasicos entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para criar dados clínicos básicos"));

        relacionamentosHandler.processarRelacionamentos(entity, request, tenantId);

        DadosClinicosBasicos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Dados clínicos básicos criados. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

