package com.upsaude.service.api.support.historicoclinico;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.prontuario.HistoricoClinicoResponse;
import com.upsaude.entity.clinica.prontuario.HistoricoClinico;
import com.upsaude.mapper.clinica.prontuario.HistoricoClinicoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoricoClinicoResponseBuilder {

    private final HistoricoClinicoMapper mapper;

    public HistoricoClinicoResponse build(HistoricoClinico entity) {
        if (entity != null) {
            if (entity.getPaciente() != null) Hibernate.initialize(entity.getPaciente());
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getAtendimento() != null) Hibernate.initialize(entity.getAtendimento());
            if (entity.getAgendamento() != null) Hibernate.initialize(entity.getAgendamento());
            if (entity.getCirurgia() != null) Hibernate.initialize(entity.getCirurgia());
        }
        return mapper.toResponse(entity);
    }
}
