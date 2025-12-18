package com.upsaude.service.support.fabricantesvacina;

import com.upsaude.api.response.vacina.FabricantesVacinaResponse;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.vacina.FabricantesVacina;
import com.upsaude.mapper.FabricantesVacinaMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FabricantesVacinaResponseBuilder {

    private final FabricantesVacinaMapper mapper;

    public FabricantesVacinaResponse build(FabricantesVacina entity, UUID tenantId) {
        if (entity != null && entity.getEndereco() != null) {
            Hibernate.initialize(entity.getEndereco());
        }

        FabricantesVacinaResponse response = mapper.toResponse(entity);

        // Oculta endereço cross-tenant
        if (entity != null) {
            Endereco endereco = entity.getEndereco();
            if (endereco != null && tenantId != null) {
                if (endereco.getTenant() != null && endereco.getTenant().getId() != null
                    && !tenantId.equals(endereco.getTenant().getId())) {
                    response.setEndereco(null);
                }
            } else if (endereco != null && tenantId == null) {
                // Sem tenant no contexto => fail-safe (não vazar)
                response.setEndereco(null);
            }
        }

        return response;
    }
}

