package com.upsaude.service.support.vacinacoes;

import com.upsaude.api.response.vacina.VacinacoesResponse;
import com.upsaude.entity.vacina.Vacinacoes;
import com.upsaude.mapper.VacinacoesMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacinacoesResponseBuilder {

    private final VacinacoesMapper mapper;

    public VacinacoesResponse build(Vacinacoes entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getEstabelecimento());
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getVacina());
            Hibernate.initialize(entity.getFabricante());
        }
        return mapper.toResponse(entity);
    }
}

