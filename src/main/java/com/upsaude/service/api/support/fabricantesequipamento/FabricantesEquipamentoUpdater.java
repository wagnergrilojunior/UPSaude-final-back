package com.upsaude.service.api.support.fabricantesequipamento;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.estabelecimento.equipamento.FabricantesEquipamentoRequest;
import com.upsaude.entity.estabelecimento.equipamento.FabricantesEquipamento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.estabelecimento.equipamento.FabricantesEquipamentoMapper;
import com.upsaude.repository.estabelecimento.equipamento.FabricantesEquipamentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesEquipamentoUpdater {

    private final FabricantesEquipamentoRepository repository;
    private final FabricantesEquipamentoMapper mapper;
    private final FabricantesEquipamentoValidationService validationService;

    public FabricantesEquipamento atualizar(UUID id, FabricantesEquipamentoRequest request) {
        validationService.validarObrigatorios(request);

        FabricantesEquipamento entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fabricante de equipamento não encontrado com ID: " + id));

        mapper.updateFromRequest(request, entity);

        FabricantesEquipamento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Fabricante de equipamento atualizado com sucesso. ID: {}", saved.getId());
        return saved;
    }
}
