package com.upsaude.service.support.falta;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.profissional.equipe.FaltaRequest;
import com.upsaude.entity.profissional.equipe.Falta;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.profissional.equipe.FaltaMapper;
import com.upsaude.repository.profissional.equipe.FaltaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaltaCreator {

    private final FaltaRepository repository;
    private final FaltaMapper mapper;
    private final FaltaValidationService validationService;
    private final FaltaRelacionamentosHandler relacionamentosHandler;

    public Falta criar(FaltaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Falta entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Falta saved = repository.save(Objects.requireNonNull(entity));
        log.info("Falta criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
