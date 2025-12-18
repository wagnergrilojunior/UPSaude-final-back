package com.upsaude.service.support.vacinas;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.saude_publica.vacina.VacinasRequest;
import com.upsaude.entity.saude_publica.vacina.Vacinas;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.saude_publica.vacina.VacinasMapper;
import com.upsaude.repository.saude_publica.vacina.VacinasRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
