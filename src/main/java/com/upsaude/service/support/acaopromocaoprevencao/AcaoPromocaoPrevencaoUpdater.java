package com.upsaude.service.support.acaopromocaoprevencao;

import com.upsaude.api.request.AcaoPromocaoPrevencaoRequest;
import com.upsaude.entity.AcaoPromocaoPrevencao;
import com.upsaude.mapper.AcaoPromocaoPrevencaoMapper;
import com.upsaude.repository.AcaoPromocaoPrevencaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcaoPromocaoPrevencaoUpdater {

    private final AcaoPromocaoPrevencaoValidationService validationService;
    private final AcaoPromocaoPrevencaoMapper mapper;
    private final AcaoPromocaoPrevencaoTenantEnforcer tenantEnforcer;
    private final AcaoPromocaoPrevencaoRelacionamentosHandler relacionamentosHandler;
    private final AcaoPromocaoPrevencaoRepository repository;

    public AcaoPromocaoPrevencao atualizar(UUID id, AcaoPromocaoPrevencaoRequest request, UUID tenantId) {
        validationService.validarObrigatorios(request);
        validationService.sanitizarDefaults(request);

        AcaoPromocaoPrevencao existente = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, existente);
        if (existente.getStatusAcao() == null) {
            existente.setStatusAcao("PLANEJADA");
        }
        if (existente.getAcaoContinua() == null) {
            existente.setAcaoContinua(false);
        }

        relacionamentosHandler.processarRelacionamentos(existente, request, tenantId);

        AcaoPromocaoPrevencao salvo = repository.save(Objects.requireNonNull(existente));
        log.info("Ação promoção/prevenção atualizada com sucesso. ID: {}, Tenant: {}", salvo.getId(), tenantId);
        return salvo;
    }
}
