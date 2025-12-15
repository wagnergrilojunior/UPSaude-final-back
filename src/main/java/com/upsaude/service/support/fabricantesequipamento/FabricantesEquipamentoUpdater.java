package com.upsaude.service.support.fabricantesequipamento;

import com.upsaude.api.request.FabricantesEquipamentoRequest;
import com.upsaude.entity.FabricantesEquipamento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.FabricantesEquipamentoMapper;
import com.upsaude.repository.FabricantesEquipamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesEquipamentoUpdater {

    private final FabricantesEquipamentoRepository repository;
    private final FabricantesEquipamentoMapper mapper;
    private final FabricantesEquipamentoValidationService validationService;

    public FabricantesEquipamento atualizar(UUID id, FabricantesEquipamentoRequest request) {
        validationService.validarObrigatorios(request);

        FabricantesEquipamento existente = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Fabricante de equipamento não encontrado com ID: " + id));

        validationService.validarUnicidadeParaAtualizacao(id, request);
        mapper.updateFromRequest(request, existente);

        FabricantesEquipamento saved = repository.save(Objects.requireNonNull(existente));
        log.info("Fabricante de equipamento atualizado com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

