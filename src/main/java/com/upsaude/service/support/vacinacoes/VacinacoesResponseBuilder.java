package com.upsaude.service.support.vacinacoes;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.saude_publica.vacina.VacinacoesResponse;
import com.upsaude.entity.saude_publica.vacina.Vacinacoes;
import com.upsaude.mapper.saude_publica.vacina.VacinacoesMapper;

import lombok.RequiredArgsConstructor;

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

