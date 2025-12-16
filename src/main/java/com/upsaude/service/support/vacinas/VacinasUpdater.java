package com.upsaude.service.support.vacinas;

import com.upsaude.api.request.VacinasRequest;
import com.upsaude.entity.Vacinas;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.VacinasMapper;
import com.upsaude.repository.VacinasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacinasUpdater {

    private final VacinasRepository repository;
    private final VacinasMapper mapper;
    private final VacinasValidationService validationService;
    private final VacinasRelacionamentosHandler relacionamentosHandler;

    public Vacinas atualizar(UUID id, VacinasRequest request) {
        Vacinas existente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacina não encontrada com ID: " + id));

        validationService.validarObrigatorios(request);
        validationService.validarDuplicidade(id, request);

        mapper.updateFromRequest(request, existente);
        relacionamentosHandler.processarRelacionamentos(existente, request);

        Vacinas salvo = repository.save(Objects.requireNonNull(existente));
        log.info("Vacina atualizada com sucesso. ID: {}", salvo.getId());
        return salvo;
    }
}
