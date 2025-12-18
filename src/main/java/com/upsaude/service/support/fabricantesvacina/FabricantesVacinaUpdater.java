package com.upsaude.service.support.fabricantesvacina;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.saude_publica.vacina.FabricantesVacinaRequest;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.saude_publica.vacina.FabricantesVacina;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.saude_publica.vacina.FabricantesVacinaMapper;
import com.upsaude.repository.saude_publica.vacina.FabricantesVacinaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesVacinaUpdater {

    private final FabricantesVacinaRepository repository;
    private final FabricantesVacinaMapper mapper;
    private final FabricantesVacinaValidationService validationService;
    private final FabricantesVacinaEnderecoHandler enderecoHandler;

    public FabricantesVacina atualizar(UUID id, FabricantesVacinaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        FabricantesVacina existente = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("FabricantesVacina não encontrado com ID: " + id));

        validationService.validarUnicidadeParaAtualizacao(id, request);
        mapper.updateFromRequest(request, existente);

        if (request.getEndereco() != null) {
            Endereco endereco = enderecoHandler.processarEndereco(request.getEndereco(), tenantId, tenant);
            existente.setEndereco(endereco);
        } else {
            existente.setEndereco(null);
        }

        FabricantesVacina saved = repository.save(Objects.requireNonNull(existente));
        log.info("FabricantesVacina atualizado com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

