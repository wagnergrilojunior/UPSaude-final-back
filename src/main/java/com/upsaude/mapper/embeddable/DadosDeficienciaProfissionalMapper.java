package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosDeficienciaProfissionalRequest;
import com.upsaude.api.response.embeddable.DadosDeficienciaProfissionalResponse;
import com.upsaude.entity.embeddable.DadosDeficienciaProfissional;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosDeficienciaProfissionalMapper {
    DadosDeficienciaProfissional toEntity(DadosDeficienciaProfissionalRequest request);
    DadosDeficienciaProfissionalResponse toResponse(DadosDeficienciaProfissional entity);
    void updateFromRequest(DadosDeficienciaProfissionalRequest request, @MappingTarget DadosDeficienciaProfissional entity);
}

