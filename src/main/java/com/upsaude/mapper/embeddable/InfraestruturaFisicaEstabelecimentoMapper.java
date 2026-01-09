package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.InfraestruturaFisicaEstabelecimentoRequest;
import com.upsaude.api.response.embeddable.InfraestruturaFisicaEstabelecimentoResponse;
import com.upsaude.entity.embeddable.InfraestruturaFisicaEstabelecimento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface InfraestruturaFisicaEstabelecimentoMapper {
    InfraestruturaFisicaEstabelecimento toEntity(InfraestruturaFisicaEstabelecimentoRequest request);
    InfraestruturaFisicaEstabelecimentoResponse toResponse(InfraestruturaFisicaEstabelecimento entity);
    void updateFromRequest(InfraestruturaFisicaEstabelecimentoRequest request, @MappingTarget InfraestruturaFisicaEstabelecimento entity);
}
