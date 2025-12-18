package com.upsaude.service.support.tratamentosodontologicos;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.odontologia.TratamentosOdontologicosResponse;
import com.upsaude.entity.odontologia.TratamentosOdontologicos;
import com.upsaude.mapper.odontologia.TratamentosOdontologicosMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TratamentosOdontologicosResponseBuilder {

    private final TratamentosOdontologicosMapper mapper;

    public TratamentosOdontologicosResponse build(TratamentosOdontologicos entity) {
        if (entity != null) {
            if (entity.getPaciente() != null) Hibernate.initialize(entity.getPaciente());
            if (entity.getProfissional() != null) Hibernate.initialize(entity.getProfissional());
            if (entity.getEstabelecimento() != null) Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
