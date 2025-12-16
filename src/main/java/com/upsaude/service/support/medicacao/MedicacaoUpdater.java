package com.upsaude.service.support.medicacao;

import com.upsaude.api.request.MedicacaoRequest;
import com.upsaude.entity.Medicacao;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MedicacaoMapper;
import com.upsaude.repository.MedicacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicacaoUpdater {

    private final MedicacaoRepository repository;
    private final MedicacaoMapper mapper;
    private final MedicacaoValidationService validationService;
    private final MedicacaoRelacionamentosHandler relacionamentosHandler;
    private final MedicacaoSanitizer sanitizer;

    public Medicacao atualizar(UUID id, MedicacaoRequest request) {
        validationService.validarObrigatorios(request);

        Medicacao existente = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + id));

        mapper.updateFromRequest(request, existente);
        relacionamentosHandler.processarRelacionamentos(existente, request);
        sanitizer.garantirValoresPadrao(existente);

        Medicacao salvo = repository.save(Objects.requireNonNull(existente));
        log.info("Medicação atualizada com sucesso. ID: {}", salvo.getId());
        return salvo;
    }
}

