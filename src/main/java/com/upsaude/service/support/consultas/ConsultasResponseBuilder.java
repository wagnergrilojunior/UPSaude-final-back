package com.upsaude.service.support.consultas;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.atendimento.ConsultasResponse;
import com.upsaude.entity.clinica.atendimento.Consultas;
import com.upsaude.mapper.clinica.atendimento.ConsultasMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultasResponseBuilder {

    private final ConsultasMapper mapper;

    public ConsultasResponse build(Consultas entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getMedico());
            Hibernate.initialize(entity.getProfissionalSaude());
            Hibernate.initialize(entity.getConvenio());
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
