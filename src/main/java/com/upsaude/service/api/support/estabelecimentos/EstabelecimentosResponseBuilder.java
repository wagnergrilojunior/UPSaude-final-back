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
            if (entity.getEndereco() != null) Hibernate.initialize(entity.getEndereco());
            if (entity.getResponsaveis() != null && entity.getResponsaveis().getResponsavelTecnico() != null) {
                Hibernate.initialize(entity.getResponsaveis().getResponsavelTecnico());
            }
            if (entity.getResponsaveis() != null && entity.getResponsaveis().getResponsavelAdministrativo() != null) {
                Hibernate.initialize(entity.getResponsaveis().getResponsavelAdministrativo());
            }
            if (entity.getEquipamentos() != null) Hibernate.initialize(entity.getEquipamentos());
        }
        return mapper.toResponse(entity);
    }
}

