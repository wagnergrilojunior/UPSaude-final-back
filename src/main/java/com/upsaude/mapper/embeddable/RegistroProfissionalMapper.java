package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.RegistroProfissionalRequest;
import com.upsaude.api.response.embeddable.RegistroProfissionalResponse;
import com.upsaude.entity.embeddable.RegistroProfissional;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface RegistroProfissionalMapper {
    RegistroProfissional toEntity(RegistroProfissionalRequest request);

    RegistroProfissionalResponse toResponse(RegistroProfissional entity);

    void updateFromRequest(RegistroProfissionalRequest request, @MappingTarget RegistroProfissional entity);
}
