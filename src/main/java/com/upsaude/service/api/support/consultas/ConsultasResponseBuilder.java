package com.upsaude.service.api.support.consultas;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.atendimento.ConsultaResponse;
import com.upsaude.entity.clinica.atendimento.Consulta;
import com.upsaude.mapper.clinica.atendimento.ConsultaMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultasResponseBuilder {

    private final ConsultaMapper mapper;

    public ConsultaResponse build(Consulta entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getAtendimento());
            if (entity.getAtendimento() != null) {
                Hibernate.initialize(entity.getAtendimento().getPaciente());
            }
            Hibernate.initialize(entity.getMedico());
        }
        return mapper.toResponse(entity);
    }
}
