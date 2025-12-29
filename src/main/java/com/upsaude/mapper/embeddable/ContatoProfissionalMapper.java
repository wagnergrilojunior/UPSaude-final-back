package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ContatoProfissionalRequest;
import com.upsaude.api.response.embeddable.ContatoProfissionalResponse;
import com.upsaude.entity.embeddable.ContatoProfissional;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ContatoProfissionalMapper {
    ContatoProfissional toEntity(ContatoProfissionalRequest request);
    ContatoProfissionalResponse toResponse(ContatoProfissional entity);
    void updateFromRequest(ContatoProfissionalRequest request, @MappingTarget ContatoProfissional entity);
}

