package com.upsaude.service.support.configuracaoestabelecimento;

import com.upsaude.api.request.estabelecimento.ConfiguracaoEstabelecimentoRequest;
import com.upsaude.entity.estabelecimento.ConfiguracaoEstabelecimento;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.ConfiguracaoEstabelecimentoMapper;
import com.upsaude.repository.estabelecimento.ConfiguracaoEstabelecimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfiguracaoEstabelecimentoCreator {

    private final ConfiguracaoEstabelecimentoRepository repository;
    private final ConfiguracaoEstabelecimentoMapper mapper;
    private final ConfiguracaoEstabelecimentoValidationService validationService;
    private final ConfiguracaoEstabelecimentoRelacionamentosHandler relacionamentosHandler;

    public ConfiguracaoEstabelecimento criar(ConfiguracaoEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadePorEstabelecimento(request.getEstabelecimento(), tenantId);

        ConfiguracaoEstabelecimento entity = mapper.fromRequest(request);
        entity.setActive(true);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        ConfiguracaoEstabelecimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Configuração de estabelecimento criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

