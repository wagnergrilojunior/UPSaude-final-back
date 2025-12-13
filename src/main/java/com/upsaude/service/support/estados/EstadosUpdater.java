package com.upsaude.service.support.estados;

import com.upsaude.api.request.EstadosRequest;
import com.upsaude.entity.Estados;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EstadosMapper;
import com.upsaude.repository.EstadosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstadosUpdater {

    private final EstadosRepository repository;
    private final EstadosMapper mapper;
    private final EstadosValidationService validationService;

    public Estados atualizar(UUID id, EstadosRequest request) {
        validationService.validarObrigatorios(request);

        Estados existente = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + id));

        mapper.updateFromRequest(request, existente);

        Estados saved = repository.save(Objects.requireNonNull(existente));
        log.info("Estado atualizado com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

