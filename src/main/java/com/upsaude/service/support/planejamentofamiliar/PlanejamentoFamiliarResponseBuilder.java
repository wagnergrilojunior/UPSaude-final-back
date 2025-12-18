package com.upsaude.service.support.planejamentofamiliar;

import com.upsaude.api.response.planejamento.PlanejamentoFamiliarResponse;
import com.upsaude.entity.planejamento.PlanejamentoFamiliar;
import com.upsaude.mapper.PlanejamentoFamiliarMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanejamentoFamiliarResponseBuilder {

    private final PlanejamentoFamiliarMapper mapper;

    public PlanejamentoFamiliarResponse build(PlanejamentoFamiliar entity) {
        if (entity != null) {
            if (entity.getPaciente() != null) Hibernate.initialize(entity.getPaciente());
            if (entity.getProfissionalResponsavel() != null) Hibernate.initialize(entity.getProfissionalResponsavel());
            if (entity.getEquipeSaude() != null) Hibernate.initialize(entity.getEquipeSaude());
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}

