package com.upsaude.service.api.support.fabricantesequipamento;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.estabelecimento.equipamento.FabricantesEquipamentoRequest;
import com.upsaude.entity.estabelecimento.equipamento.FabricantesEquipamento;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.estabelecimento.equipamento.FabricantesEquipamentoMapper;
import com.upsaude.repository.estabelecimento.equipamento.FabricantesEquipamentoRepository;
import com.upsaude.repository.paciente.EnderecoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesEquipamentoUpdater {

    private final FabricantesEquipamentoRepository repository;
    private final FabricantesEquipamentoMapper mapper;
    private final FabricantesEquipamentoValidationService validationService;
    private final EnderecoRepository enderecoRepository;

    public FabricantesEquipamento atualizar(UUID id, FabricantesEquipamentoRequest request) {
        validationService.validarObrigatorios(request);

        UUID entityId = Objects.requireNonNull(id);
        FabricantesEquipamento entity = repository.findById(entityId)
                .orElseThrow(() -> new NotFoundException("Fabricante de equipamento não encontrado com ID: " + entityId));

        mapper.updateFromRequest(request, entity);

        if (request.getEndereco() != null) {
            UUID enderecoId = Objects.requireNonNull(request.getEndereco());
            Endereco endereco = enderecoRepository.findById(enderecoId)
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado com ID: " + enderecoId));
            entity.setEndereco(endereco);
        } else {
            entity.setEndereco(null);
        }

        FabricantesEquipamento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Fabricante de equipamento atualizado com sucesso. ID: {}", saved.getId());
        return saved;
    }
}
