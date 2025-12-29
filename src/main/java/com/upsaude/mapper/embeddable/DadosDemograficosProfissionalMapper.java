package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosDemograficosProfissionalRequest;
import com.upsaude.api.response.embeddable.DadosDemograficosProfissionalResponse;
import com.upsaude.entity.embeddable.DadosDemograficosProfissional;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosDemograficosProfissionalMapper {
    DadosDemograficosProfissional toEntity(DadosDemograficosProfissionalRequest request);
    DadosDemograficosProfissionalResponse toResponse(DadosDemograficosProfissional entity);
    void updateFromRequest(DadosDemograficosProfissionalRequest request, @MappingTarget DadosDemograficosProfissional entity);
}

