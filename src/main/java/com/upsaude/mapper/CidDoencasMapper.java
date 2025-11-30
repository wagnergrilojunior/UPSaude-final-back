package com.upsaude.mapper;

import com.upsaude.api.request.CidDoencasRequest;
import com.upsaude.api.response.CidDoencasResponse;
import com.upsaude.dto.CidDoencasDTO;
import com.upsaude.entity.CidDoencas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface CidDoencasMapper extends EntityMapper<CidDoencas, CidDoencasDTO> {

    CidDoencas toEntity(CidDoencasDTO dto);

    CidDoencasDTO toDTO(CidDoencas entity);

    @Mapping(target = "active", ignore = true)
    CidDoencas fromRequest(CidDoencasRequest request);

    CidDoencasResponse toResponse(CidDoencas entity);
}

