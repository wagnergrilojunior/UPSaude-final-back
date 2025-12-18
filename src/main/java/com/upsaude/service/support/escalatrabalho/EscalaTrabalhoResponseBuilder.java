package com.upsaude.service.support.escalatrabalho;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.profissional.equipe.EscalaTrabalhoResponse;
import com.upsaude.entity.profissional.equipe.EscalaTrabalho;
import com.upsaude.mapper.profissional.equipe.EscalaTrabalhoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EscalaTrabalhoResponseBuilder {

    private final EscalaTrabalhoMapper mapper;

    public EscalaTrabalhoResponse build(EscalaTrabalho entity) {
        if (entity != null) {
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getMedico() != null) Hibernate.initialize(entity.getMedico());
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
