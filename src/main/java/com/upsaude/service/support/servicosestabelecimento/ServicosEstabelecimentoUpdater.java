package com.upsaude.service.support.servicosestabelecimento;

import com.upsaude.api.request.ServicosEstabelecimentoRequest;
import com.upsaude.entity.ServicosEstabelecimento;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.ServicosEstabelecimentoMapper;
import com.upsaude.repository.ServicosEstabelecimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicosEstabelecimentoUpdater {

    private final ServicosEstabelecimentoRepository repository;
    private final ServicosEstabelecimentoMapper mapper;
    private final ServicosEstabelecimentoTenantEnforcer tenantEnforcer;
    private final ServicosEstabelecimentoValidationService validationService;
    private final ServicosEstabelecimentoRelacionamentosHandler relacionamentosHandler;

    public ServicosEstabelecimento atualizar(UUID id, ServicosEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        ServicosEstabelecimento entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        ServicosEstabelecimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Serviço do estabelecimento atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

