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
public class AcaoPromocaoPrevencaoCreator {

    private final AcaoPromocaoPrevencaoValidationService validationService;
    private final AcaoPromocaoPrevencaoMapper mapper;
    private final AcaoPromocaoPrevencaoRelacionamentosHandler relacionamentosHandler;
    private final AcaoPromocaoPrevencaoRepository repository;

    public AcaoPromocaoPrevencao criar(AcaoPromocaoPrevencaoRequest request, UUID tenantId) {
        validationService.validarObrigatorios(request);
        validationService.sanitizarDefaults(request);

        AcaoPromocaoPrevencao acao = mapper.fromRequest(request);
        acao.setActive(true);

        if (acao.getStatusAcao() == null) {
            acao.setStatusAcao("PLANEJADA");
        }
        if (acao.getAcaoContinua() == null) {
            acao.setAcaoContinua(false);
        }

        relacionamentosHandler.processarRelacionamentos(acao, request, tenantId);

        AcaoPromocaoPrevencao salvo = repository.save(Objects.requireNonNull(acao));
        log.info("Ação promoção/prevenção criada com sucesso. ID: {}, Tenant: {}", salvo.getId(), tenantId);
        return salvo;
    }
}
