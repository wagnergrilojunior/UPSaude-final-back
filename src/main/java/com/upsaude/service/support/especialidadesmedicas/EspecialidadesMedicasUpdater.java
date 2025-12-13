package com.upsaude.service.support.especialidadesmedicas;

import com.upsaude.api.request.EspecialidadesMedicasRequest;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EspecialidadesMedicasMapper;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EspecialidadesMedicasUpdater {

    private final EspecialidadesMedicasRepository repository;
    private final EspecialidadesMedicasMapper mapper;
    private final EspecialidadesMedicasValidationService validationService;

    public EspecialidadesMedicas atualizar(UUID id, EspecialidadesMedicasRequest request) {
        validationService.validarObrigatorios(request);

        EspecialidadesMedicas existente = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Especialidade médica não encontrada com ID: " + id));

        mapper.updateFromRequest(request, existente);

        EspecialidadesMedicas saved = repository.save(Objects.requireNonNull(existente));
        log.info("Especialidade médica atualizada com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

