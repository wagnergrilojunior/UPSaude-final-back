package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosPessoaisBasicosProfissionalRequest;
import com.upsaude.api.response.embeddable.DadosPessoaisBasicosProfissionalResponse;
import com.upsaude.entity.embeddable.DadosPessoaisBasicosProfissional;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosPessoaisBasicosProfissionalMapper {
    DadosPessoaisBasicosProfissional toEntity(DadosPessoaisBasicosProfissionalRequest request);
    DadosPessoaisBasicosProfissionalResponse toResponse(DadosPessoaisBasicosProfissional entity);
    void updateFromRequest(DadosPessoaisBasicosProfissionalRequest request, @MappingTarget DadosPessoaisBasicosProfissional entity);
}

