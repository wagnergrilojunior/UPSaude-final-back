package com.upsaude.service.api.support.prontuarios;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.prontuario.ProntuarioResponse;
import com.upsaude.entity.clinica.prontuario.Prontuario;
import com.upsaude.mapper.clinica.prontuario.ProntuarioMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProntuarioResponseBuilder {

    private final ProntuarioMapper mapper;

    public ProntuarioResponse build(Prontuario entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getProfissionalCriador());
            Hibernate.initialize(entity.getAlergias());
            Hibernate.initialize(entity.getVacinacoes());
            Hibernate.initialize(entity.getExames());
            Hibernate.initialize(entity.getDoencas());
        }
        return mapper.toResponse(entity);
    }
}

