package com.upsaude.service.support.servicosestabelecimento;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.estabelecimento.ServicosEstabelecimentoRequest;
import com.upsaude.entity.estabelecimento.ServicosEstabelecimento;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.estabelecimento.ServicosEstabelecimentoMapper;
import com.upsaude.repository.estabelecimento.ServicosEstabelecimentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicosEstabelecimentoCreator {

    private final ServicosEstabelecimentoRepository repository;
    private final ServicosEstabelecimentoMapper mapper;
    private final ServicosEstabelecimentoValidationService validationService;
    private final ServicosEstabelecimentoRelacionamentosHandler relacionamentosHandler;

    public ServicosEstabelecimento criar(ServicosEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        ServicosEstabelecimento entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        ServicosEstabelecimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Serviço do estabelecimento criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

