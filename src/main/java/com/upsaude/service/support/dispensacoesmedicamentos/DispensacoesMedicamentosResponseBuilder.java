package com.upsaude.service.support.dispensacoesmedicamentos;

import com.upsaude.api.response.clinica.medicacao.DispensacoesMedicamentosResponse;
import com.upsaude.entity.clinica.medicacao.DispensacoesMedicamentos;
import com.upsaude.mapper.clinica.medicacao.DispensacoesMedicamentosMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DispensacoesMedicamentosResponseBuilder {

    private final DispensacoesMedicamentosMapper mapper;

    public DispensacoesMedicamentosResponse build(DispensacoesMedicamentos entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getPaciente());
            Hibernate.initialize(entity.getMedicacao());
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
