package com.upsaude.service.support.fabricantesmedicamento;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.referencia.fabricante.FabricantesMedicamentoRequest;
import com.upsaude.entity.referencia.fabricante.FabricantesMedicamento;
import com.upsaude.mapper.referencia.fabricante.FabricantesMedicamentoMapper;
import com.upsaude.repository.referencia.fabricante.FabricantesMedicamentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesMedicamentoCreator {

    private final FabricantesMedicamentoRepository repository;
    private final FabricantesMedicamentoMapper mapper;
    private final FabricantesMedicamentoValidationService validationService;

    public FabricantesMedicamento criar(FabricantesMedicamentoRequest request) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request);

        FabricantesMedicamento entity = mapper.fromRequest(request);
        entity.setActive(true);

        FabricantesMedicamento saved = repository.save(Objects.requireNonNull(entity));
        log.info("FabricantesMedicamento criado com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

