package com.upsaude.service.api.support.estados;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.referencia.geografico.EstadosRequest;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.mapper.referencia.geografico.EstadosMapper;
import com.upsaude.repository.referencia.geografico.EstadosRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstadosCreator {

    private final EstadosRepository repository;
    private final EstadosMapper mapper;
    private final EstadosValidationService validationService;

    public Estados criar(EstadosRequest request) {
        validationService.validarObrigatorios(request);

        Estados entity = mapper.fromRequest(request);
        entity.setActive(true);

        Estados saved = repository.save(Objects.requireNonNull(entity));
        log.info("Estado criado com sucesso. ID: {}", saved.getId());
        return saved;
    }
}
