package com.upsaude.service.support.medicacao;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.medicacao.MedicacaoRequest;
import com.upsaude.entity.clinica.medicacao.Medicacao;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.clinica.medicacao.MedicacaoMapper;
import com.upsaude.repository.clinica.medicacao.MedicacaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

