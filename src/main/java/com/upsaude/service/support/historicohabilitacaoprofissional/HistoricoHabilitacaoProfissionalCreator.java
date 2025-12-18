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
public class HistoricoHabilitacaoProfissionalCreator {

    private final HistoricoHabilitacaoProfissionalRepository repository;
    private final HistoricoHabilitacaoProfissionalMapper mapper;
    private final HistoricoHabilitacaoProfissionalValidationService validationService;
    private final HistoricoHabilitacaoProfissionalRelacionamentosHandler relacionamentosHandler;

    public HistoricoHabilitacaoProfissional criar(HistoricoHabilitacaoProfissionalRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        HistoricoHabilitacaoProfissional entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        HistoricoHabilitacaoProfissional saved = repository.save(Objects.requireNonNull(entity));
        log.info("Histórico de habilitação profissional criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
