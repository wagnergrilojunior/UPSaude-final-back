package com.upsaude.service.support.filaespera;

import com.upsaude.api.response.agendamento.FilaEsperaResponse;
import com.upsaude.entity.agendamento.FilaEspera;
import com.upsaude.mapper.FilaEsperaMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilaEsperaResponseBuilder {

    private final FilaEsperaMapper mapper;

    public FilaEsperaResponse build(FilaEspera entity) {
        if (entity != null) {
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
            if (entity.getPaciente() != null) Hibernate.initialize(entity.getPaciente());
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getMedico() != null) Hibernate.initialize(entity.getMedico());
            if (entity.getEspecialidade() != null) Hibernate.initialize(entity.getEspecialidade());
            if (entity.getAgendamento() != null) Hibernate.initialize(entity.getAgendamento());
        }
        return mapper.toResponse(entity);
    }
}

