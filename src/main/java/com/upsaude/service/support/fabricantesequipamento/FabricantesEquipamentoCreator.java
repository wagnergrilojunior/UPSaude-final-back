package com.upsaude.service.support.fabricantesequipamento;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.estabelecimento.equipamento.FabricantesEquipamentoRequest;
import com.upsaude.entity.estabelecimento.equipamento.FabricantesEquipamento;
import com.upsaude.mapper.estabelecimento.equipamento.FabricantesEquipamentoMapper;
import com.upsaude.repository.estabelecimento.equipamento.FabricantesEquipamentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesEquipamentoCreator {

    private final FabricantesEquipamentoRepository repository;
    private final FabricantesEquipamentoMapper mapper;
    private final FabricantesEquipamentoValidationService validationService;

    public FabricantesEquipamento criar(FabricantesEquipamentoRequest request) {
        validationService.validarObrigatorios(request);

        FabricantesEquipamento entity = mapper.fromRequest(request);
        entity.setActive(true);

        FabricantesEquipamento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Fabricante de equipamento criado com sucesso. ID: {}", saved.getId());
        return saved;
    }
}
