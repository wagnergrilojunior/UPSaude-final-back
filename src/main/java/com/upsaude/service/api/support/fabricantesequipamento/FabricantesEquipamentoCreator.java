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
public class FabricantesEquipamentoCreator {

    private final FabricantesEquipamentoRepository repository;
    private final FabricantesEquipamentoMapper mapper;
    private final FabricantesEquipamentoValidationService validationService;
    private final EnderecoRepository enderecoRepository;

    public FabricantesEquipamento criar(FabricantesEquipamentoRequest request) {
        validationService.validarObrigatorios(request);

        FabricantesEquipamento entity = mapper.fromRequest(request);
        entity.setActive(true);

        if (request.getEndereco() != null) {
            UUID enderecoId = Objects.requireNonNull(request.getEndereco());
            Endereco endereco = enderecoRepository.findById(enderecoId)
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado com ID: " + enderecoId));
            entity.setEndereco(endereco);
        }

        FabricantesEquipamento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Fabricante de equipamento criado com sucesso. ID: {}", saved.getId());
        return saved;
    }
}
