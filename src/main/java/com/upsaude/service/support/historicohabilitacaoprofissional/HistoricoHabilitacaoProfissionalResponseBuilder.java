package com.upsaude.service.support.historicohabilitacaoprofissional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.profissional.HistoricoHabilitacaoProfissionalResponse;
import com.upsaude.entity.profissional.HistoricoHabilitacaoProfissional;
import com.upsaude.mapper.profissional.HistoricoHabilitacaoProfissionalMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoricoHabilitacaoProfissionalResponseBuilder {

    private final HistoricoHabilitacaoProfissionalMapper mapper;

    public HistoricoHabilitacaoProfissionalResponse build(HistoricoHabilitacaoProfissional entity) {
        if (entity != null) {
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
        }
        return mapper.toResponse(entity);
    }
}
