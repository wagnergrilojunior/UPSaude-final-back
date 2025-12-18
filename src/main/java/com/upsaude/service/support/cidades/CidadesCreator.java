package com.upsaude.service.support.cidades;

import com.upsaude.api.request.geografico.CidadesRequest;
import com.upsaude.entity.geografico.Cidades;
import com.upsaude.mapper.CidadesMapper;
import com.upsaude.repository.referencia.geografico.CidadesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CidadesCreator {

    private final CidadesRepository repository;
    private final CidadesMapper mapper;
    private final CidadesValidationService validationService;
    private final CidadesRelacionamentosHandler relacionamentosHandler;

    public Cidades criar(CidadesRequest request) {
        validationService.validarObrigatorios(request);

        Cidades entity = mapper.fromRequest(request);
        entity.setActive(true);
        relacionamentosHandler.processarRelacionamentos(entity, request);

        Cidades saved = repository.save(Objects.requireNonNull(entity));
        log.info("Cidade criada com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

