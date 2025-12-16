package com.upsaude.mapper;

import com.upsaude.api.request.CidDoencasRequest;
import com.upsaude.api.response.CidDoencasResponse;
import com.upsaude.dto.CidDoencasDTO;
import com.upsaude.entity.CidDoencas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface CidDoencasMapper extends EntityMapper<CidDoencas, CidDoencasDTO> {

    @Mapping(target = "active", ignore = true)
    CidDoencas toEntity(CidDoencasDTO dto);

    CidDoencasDTO toDTO(CidDoencas entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    CidDoencas fromRequest(CidDoencasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(CidDoencasRequest request, @MappingTarget CidDoencas entity);

    CidDoencasResponse toResponse(CidDoencas entity);
}
