package com.upsaude.service.support.fabricantesvacina;

import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.saude_publica.vacina.FabricantesVacinaResponse;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.saude_publica.vacina.FabricantesVacina;
import com.upsaude.mapper.saude_publica.vacina.FabricantesVacinaMapper;

import lombok.RequiredArgsConstructor;

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

