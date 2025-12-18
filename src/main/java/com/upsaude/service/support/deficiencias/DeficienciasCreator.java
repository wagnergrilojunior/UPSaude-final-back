package com.upsaude.service.support.deficiencias;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.deficiencia.DeficienciasRequest;
import com.upsaude.entity.paciente.deficiencia.Deficiencias;
import com.upsaude.mapper.paciente.deficiencia.DeficienciasMapper;
import com.upsaude.repository.paciente.deficiencia.DeficienciasRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeficienciasCreator {

    private final DeficienciasRepository repository;
    private final DeficienciasMapper mapper;
    private final DeficienciasValidationService validationService;

    public Deficiencias criar(DeficienciasRequest request) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request);

        Deficiencias entity = mapper.fromRequest(request);
        entity.setActive(true);

        Deficiencias saved = repository.save(Objects.requireNonNull(entity));
        log.info("Deficiência criada com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

