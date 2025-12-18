package com.upsaude.service.support.vacinas;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.saude_publica.vacina.VacinasRequest;
import com.upsaude.entity.saude_publica.vacina.Vacinas;
import com.upsaude.mapper.saude_publica.vacina.VacinasMapper;
import com.upsaude.repository.saude_publica.vacina.VacinasRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacinasCreator {

    private final VacinasRepository repository;
    private final VacinasMapper mapper;
    private final VacinasValidationService validationService;
    private final VacinasRelacionamentosHandler relacionamentosHandler;

    public Vacinas criar(VacinasRequest request) {
        validationService.validarObrigatorios(request);
        validationService.validarDuplicidade(null, request);

        Vacinas vacina = mapper.fromRequest(request);
        vacina.setActive(true);

        relacionamentosHandler.processarRelacionamentos(vacina, request);

        Vacinas salvo = repository.save(Objects.requireNonNull(vacina));
        log.info("Vacina criada com sucesso. ID: {}", salvo.getId());
        return salvo;
    }
}
