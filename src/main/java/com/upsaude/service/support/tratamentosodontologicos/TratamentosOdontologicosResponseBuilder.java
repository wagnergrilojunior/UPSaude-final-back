package com.upsaude.service.support.tratamentosodontologicos;

import com.upsaude.api.response.odontologia.TratamentosOdontologicosResponse;
import com.upsaude.entity.odontologia.TratamentosOdontologicos;
import com.upsaude.mapper.TratamentosOdontologicosMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TratamentosOdontologicosResponseBuilder {

    private final TratamentosOdontologicosMapper mapper;

    public TratamentosOdontologicosResponse build(TratamentosOdontologicos entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getEstabelecimento());
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getProfissional());
        }
        return mapper.toResponse(entity);
    }
}

