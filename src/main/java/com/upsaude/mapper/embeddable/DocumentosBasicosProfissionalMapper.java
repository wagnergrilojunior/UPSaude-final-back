package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DocumentosBasicosProfissionalRequest;
import com.upsaude.api.response.embeddable.DocumentosBasicosProfissionalResponse;
import com.upsaude.entity.embeddable.DocumentosBasicosProfissional;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DocumentosBasicosProfissionalMapper {
    DocumentosBasicosProfissional toEntity(DocumentosBasicosProfissionalRequest request);
    DocumentosBasicosProfissionalResponse toResponse(DocumentosBasicosProfissional entity);
    void updateFromRequest(DocumentosBasicosProfissionalRequest request, @MappingTarget DocumentosBasicosProfissional entity);
}

