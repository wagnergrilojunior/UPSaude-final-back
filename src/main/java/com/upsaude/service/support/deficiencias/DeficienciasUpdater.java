package com.upsaude.service.support.deficiencias;

import com.upsaude.api.request.deficiencia.DeficienciasRequest;
import com.upsaude.entity.deficiencia.Deficiencias;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DeficienciasMapper;
import com.upsaude.repository.deficiencia.DeficienciasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeficienciasUpdater {

    private final DeficienciasRepository repository;
    private final DeficienciasMapper mapper;
    private final DeficienciasValidationService validationService;

    public Deficiencias atualizar(UUID id, DeficienciasRequest request) {
        validationService.validarObrigatorios(request);

        Deficiencias existente = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Deficiência não encontrada com ID: " + id));

        validationService.validarUnicidadeParaAtualizacao(id, request);
        mapper.updateFromRequest(request, existente);

        Deficiencias saved = repository.save(Objects.requireNonNull(existente));
        log.info("Deficiência atualizada com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

