package com.upsaude.service.support.historicohabilitacaoprofissional;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.profissional.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.entity.profissional.HistoricoHabilitacaoProfissional;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.profissional.HistoricoHabilitacaoProfissionalMapper;
import com.upsaude.repository.profissional.HistoricoHabilitacaoProfissionalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoricoHabilitacaoProfissionalUpdater {

    private final HistoricoHabilitacaoProfissionalRepository repository;
    private final HistoricoHabilitacaoProfissionalMapper mapper;
    private final HistoricoHabilitacaoProfissionalTenantEnforcer tenantEnforcer;
    private final HistoricoHabilitacaoProfissionalValidationService validationService;
    private final HistoricoHabilitacaoProfissionalRelacionamentosHandler relacionamentosHandler;

    public HistoricoHabilitacaoProfissional atualizar(UUID id, HistoricoHabilitacaoProfissionalRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        HistoricoHabilitacaoProfissional entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        HistoricoHabilitacaoProfissional saved = repository.save(Objects.requireNonNull(entity));
        log.info("Histórico de habilitação profissional atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
