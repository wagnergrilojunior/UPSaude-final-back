package com.upsaude.service.api.support.alergias;

import com.upsaude.api.request.alergia.AlergiasRequest;
import com.upsaude.entity.paciente.alergia.Alergias;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.paciente.alergia.AlergiasMapper;
import com.upsaude.repository.paciente.alergia.AlergiasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlergiasUpdater {

    private final AlergiasRepository repository;
    private final AlergiasMapper mapper;
    private final AlergiasSanitizer sanitizer;
    private final AlergiasValidationService validationService;

    public Alergias atualizar(UUID id, AlergiasRequest request) {
        Alergias existente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Alergia não encontrada com ID: " + id));

        sanitizer.sanitizarRequest(request);
        validationService.validarObrigatorios(request);
        validationService.validarDuplicidade(id, request);

        mapper.updateFromRequest(request, existente);

        Alergias salvo = repository.save(Objects.requireNonNull(existente));
        log.info("Alergia atualizada com sucesso. ID: {}", salvo.getId());
        return salvo;
    }
}
