package com.upsaude.service.support.receitasmedicas;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.medicacao.ReceitasMedicasResponse;
import com.upsaude.entity.clinica.medicacao.ReceitasMedicas;
import com.upsaude.mapper.clinica.medicacao.ReceitasMedicasMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceitasMedicasResponseBuilder {

    private final ReceitasMedicasMapper mapper;

    public ReceitasMedicasResponse build(ReceitasMedicas entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getEstabelecimento());
            Hibernate.initialize(entity.getMedico());
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getMedicacoes());
        }
        return mapper.toResponse(entity);
    }
}

