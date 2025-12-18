package com.upsaude.service.support.configuracaoestabelecimento;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.estabelecimento.ConfiguracaoEstabelecimentoRequest;
import com.upsaude.entity.estabelecimento.ConfiguracaoEstabelecimento;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.estabelecimento.ConfiguracaoEstabelecimentoMapper;
import com.upsaude.repository.estabelecimento.ConfiguracaoEstabelecimentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfiguracaoEstabelecimentoUpdater {

    private final ConfiguracaoEstabelecimentoRepository repository;
    private final ConfiguracaoEstabelecimentoMapper mapper;
    private final ConfiguracaoEstabelecimentoTenantEnforcer tenantEnforcer;
    private final ConfiguracaoEstabelecimentoValidationService validationService;
    private final ConfiguracaoEstabelecimentoRelacionamentosHandler relacionamentosHandler;

    public ConfiguracaoEstabelecimento atualizar(UUID id, ConfiguracaoEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        ConfiguracaoEstabelecimento entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        ConfiguracaoEstabelecimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Configuração de estabelecimento atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

