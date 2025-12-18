package com.upsaude.service.support.medicacao;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.medicacao.MedicacaoRequest;
import com.upsaude.entity.clinica.medicacao.Medicacao;
import com.upsaude.mapper.clinica.medicacao.MedicacaoMapper;
import com.upsaude.repository.clinica.medicacao.MedicacaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicacaoCreator {

    private final MedicacaoRepository repository;
    private final MedicacaoMapper mapper;
    private final MedicacaoValidationService validationService;
    private final MedicacaoRelacionamentosHandler relacionamentosHandler;
    private final MedicacaoSanitizer sanitizer;

    public Medicacao criar(MedicacaoRequest request) {
        validationService.validarObrigatorios(request);

        Medicacao medicacao = mapper.fromRequest(request);
        medicacao.setActive(true);

        relacionamentosHandler.processarRelacionamentos(medicacao, request);
        sanitizer.garantirValoresPadrao(medicacao);

        Medicacao salvo = repository.save(Objects.requireNonNull(medicacao));
        log.info("Medicação criada com sucesso. ID: {}", salvo.getId());
        return salvo;
    }
}

