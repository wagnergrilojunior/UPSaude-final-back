package com.upsaude.service.support.fabricantesvacina;

import com.upsaude.api.request.FabricantesVacinaRequest;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.FabricantesVacina;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.FabricantesVacinaMapper;
import com.upsaude.repository.FabricantesVacinaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

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

