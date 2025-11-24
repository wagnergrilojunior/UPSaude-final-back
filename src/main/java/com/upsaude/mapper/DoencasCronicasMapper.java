package com.upsaude.mapper;

import com.upsaude.api.request.DoencasCronicasRequest;
import com.upsaude.api.response.DoencasCronicasResponse;
import com.upsaude.dto.DoencasCronicasDTO;
import com.upsaude.entity.DoencasCronicas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface DoencasCronicasMapper extends EntityMapper<DoencasCronicas, DoencasCronicasDTO> {

    @Mapping(target = "tenant", ignore = true)
    DoencasCronicas toEntity(DoencasCronicasDTO dto);

    DoencasCronicasDTO toDTO(DoencasCronicas entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    DoencasCronicas fromRequest(DoencasCronicasRequest request);

    DoencasCronicasResponse toResponse(DoencasCronicas entity);
}

