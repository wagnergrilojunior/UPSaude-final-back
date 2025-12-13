package com.upsaude.service.support.fabricantesmedicamento;

import com.upsaude.api.request.FabricantesMedicamentoRequest;
import com.upsaude.entity.FabricantesMedicamento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.FabricantesMedicamentoMapper;
import com.upsaude.repository.FabricantesMedicamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesMedicamentoUpdater {

    private final FabricantesMedicamentoRepository repository;
    private final FabricantesMedicamentoMapper mapper;
    private final FabricantesMedicamentoValidationService validationService;

    public FabricantesMedicamento atualizar(UUID id, FabricantesMedicamentoRequest request) {
        validationService.validarObrigatorios(request);

        FabricantesMedicamento existente = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("FabricantesMedicamento não encontrado com ID: " + id));

        validationService.validarUnicidadeParaAtualizacao(id, request);
        mapper.updateFromRequest(request, existente);

        FabricantesMedicamento saved = repository.save(Objects.requireNonNull(existente));
        log.info("FabricantesMedicamento atualizado com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

