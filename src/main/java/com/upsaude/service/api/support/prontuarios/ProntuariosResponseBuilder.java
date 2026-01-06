package com.upsaude.service.api.support.prontuarios;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.prontuario.ProntuariosResponse;
import com.upsaude.entity.clinica.prontuario.Prontuarios;
import com.upsaude.mapper.clinica.prontuario.ProntuariosMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProntuariosResponseBuilder {

    private final ProntuariosMapper mapper;

    public ProntuariosResponse build(Prontuarios entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getPaciente());
        }
        return mapper.toResponse(entity);
    }
}
