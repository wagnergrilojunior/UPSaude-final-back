package com.upsaude.service.api.support.estabelecimentos;

import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstabelecimentosResponseBuilder {

    private final EstabelecimentosMapper mapper;

    public EstabelecimentosResponse build(Estabelecimentos entity) {
        if (entity != null) {
            if (entity.getEnderecoPrincipal() != null) Hibernate.initialize(entity.getEnderecoPrincipal());
            if (entity.getResponsavelTecnico() != null) Hibernate.initialize(entity.getResponsavelTecnico());
            if (entity.getResponsavelAdministrativo() != null) Hibernate.initialize(entity.getResponsavelAdministrativo());
            if (entity.getEnderecosSecundarios() != null) Hibernate.initialize(entity.getEnderecosSecundarios());
            if (entity.getServicos() != null) Hibernate.initialize(entity.getServicos());
            if (entity.getEquipamentos() != null) Hibernate.initialize(entity.getEquipamentos());
            if (entity.getInfraestrutura() != null) Hibernate.initialize(entity.getInfraestrutura());
            if (entity.getEquipes() != null) Hibernate.initialize(entity.getEquipes());
        }
        return mapper.toResponse(entity);
    }
}

