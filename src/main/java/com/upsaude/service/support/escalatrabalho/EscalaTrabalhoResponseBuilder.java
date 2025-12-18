package com.upsaude.service.support.escalatrabalho;

import com.upsaude.api.response.equipe.EscalaTrabalhoResponse;
import com.upsaude.entity.equipe.EscalaTrabalho;
import com.upsaude.mapper.EscalaTrabalhoMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EscalaTrabalhoResponseBuilder {

    private final EscalaTrabalhoMapper mapper;

    public EscalaTrabalhoResponse build(EscalaTrabalho entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getProfissional());
            Hibernate.initialize(entity.getMedico());
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
