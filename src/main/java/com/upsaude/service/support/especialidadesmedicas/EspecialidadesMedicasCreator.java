package com.upsaude.service.support.especialidadesmedicas;

import com.upsaude.api.request.profissional.EspecialidadesMedicasRequest;
import com.upsaude.entity.profissional.EspecialidadesMedicas;
import com.upsaude.mapper.EspecialidadesMedicasMapper;
import com.upsaude.repository.profissional.EspecialidadesMedicasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EspecialidadesMedicasCreator {

    private final EspecialidadesMedicasRepository repository;
    private final EspecialidadesMedicasMapper mapper;
    private final EspecialidadesMedicasValidationService validationService;

    public EspecialidadesMedicas criar(EspecialidadesMedicasRequest request) {
        validationService.validarObrigatorios(request);

        EspecialidadesMedicas entity = mapper.fromRequest(request);
        entity.setActive(true);

        EspecialidadesMedicas saved = repository.save(Objects.requireNonNull(entity));
        log.info("Especialidade médica criada com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

