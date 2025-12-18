package com.upsaude.service.support.exames;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.exame.ExamesResponse;
import com.upsaude.entity.clinica.exame.Exames;
import com.upsaude.mapper.clinica.exame.ExamesMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamesResponseBuilder {

    private final ExamesMapper mapper;

    public ExamesResponse build(Exames entity) {
        if (entity != null) {
            if (entity.getPaciente() != null) Hibernate.initialize(entity.getPaciente());
            if (entity.getCatalogoExame() != null) Hibernate.initialize(entity.getCatalogoExame());
            if (entity.getAtendimento() != null) Hibernate.initialize(entity.getAtendimento());
            if (entity.getConsulta() != null) Hibernate.initialize(entity.getConsulta());
            if (entity.getProfissionalSolicitante() != null) Hibernate.initialize(entity.getProfissionalSolicitante());
            if (entity.getMedicoSolicitante() != null) Hibernate.initialize(entity.getMedicoSolicitante());
            if (entity.getEstabelecimentoRealizador() != null) Hibernate.initialize(entity.getEstabelecimentoRealizador());
            if (entity.getProfissionalResponsavel() != null) Hibernate.initialize(entity.getProfissionalResponsavel());
            if (entity.getMedicoResponsavel() != null) Hibernate.initialize(entity.getMedicoResponsavel());
        }

        return mapper.toResponse(entity);
    }
}

