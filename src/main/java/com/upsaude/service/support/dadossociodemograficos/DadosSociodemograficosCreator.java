package com.upsaude.service.support.dadossociodemograficos;

import com.upsaude.api.request.paciente.DadosSociodemograficosRequest;
import com.upsaude.entity.paciente.DadosSociodemograficos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.DadosSociodemograficosMapper;
import com.upsaude.repository.paciente.DadosSociodemograficosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DadosSociodemograficosCreator {

    private final DadosSociodemograficosRepository repository;
    private final DadosSociodemograficosMapper mapper;
    private final DadosSociodemograficosValidationService validationService;
    private final DadosSociodemograficosRelacionamentosHandler relacionamentosHandler;

    public DadosSociodemograficos criar(DadosSociodemograficosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarDuplicidadeParaCriacao(request.getPaciente(), tenantId);

        DadosSociodemograficos entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para criar dados sociodemográficos"));

        relacionamentosHandler.processarRelacionamentos(entity, request, tenantId);

        DadosSociodemograficos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Dados sociodemográficos criados. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

