package com.upsaude.service.support.receitasmedicas;

import com.upsaude.api.response.ReceitasMedicasResponse;
import com.upsaude.entity.ReceitasMedicas;
import com.upsaude.mapper.ReceitasMedicasMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceitasMedicasResponseBuilder {

    private final ReceitasMedicasMapper mapper;

    public ReceitasMedicasResponse build(ReceitasMedicas entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getEstabelecimento());
            Hibernate.initialize(entity.getMedico());
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getCidPrincipal());
            Hibernate.initialize(entity.getMedicacoes());
        }
        return mapper.toResponse(entity);
    }
}

