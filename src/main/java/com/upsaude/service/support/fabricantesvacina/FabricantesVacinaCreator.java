package com.upsaude.service.support.fabricantesvacina;

import com.upsaude.api.request.vacina.FabricantesVacinaRequest;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.vacina.FabricantesVacina;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.FabricantesVacinaMapper;
import com.upsaude.repository.saude_publica.vacina.FabricantesVacinaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesVacinaCreator {

    private final FabricantesVacinaRepository repository;
    private final FabricantesVacinaMapper mapper;
    private final FabricantesVacinaValidationService validationService;
    private final FabricantesVacinaEnderecoHandler enderecoHandler;

    public FabricantesVacina criar(FabricantesVacinaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request);

        FabricantesVacina entity = mapper.fromRequest(request);
        entity.setActive(true);

        if (request.getEndereco() != null) {
            Endereco endereco = enderecoHandler.processarEndereco(request.getEndereco(), tenantId, tenant);
            entity.setEndereco(endereco);
        } else {
            entity.setEndereco(null);
        }

        FabricantesVacina saved = repository.save(Objects.requireNonNull(entity));
        log.info("FabricantesVacina criado com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

