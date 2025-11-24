package com.upsaude.mapper;

import com.upsaude.api.request.MedicacoesContinuasRequest;
import com.upsaude.api.response.MedicacoesContinuasResponse;
import com.upsaude.dto.MedicacoesContinuasDTO;
import com.upsaude.entity.MedicacoesContinuas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface MedicacoesContinuasMapper extends EntityMapper<MedicacoesContinuas, MedicacoesContinuasDTO> {

    @Mapping(target = "tenant", ignore = true)
    MedicacoesContinuas toEntity(MedicacoesContinuasDTO dto);

    MedicacoesContinuasDTO toDTO(MedicacoesContinuas entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    MedicacoesContinuas fromRequest(MedicacoesContinuasRequest request);

    MedicacoesContinuasResponse toResponse(MedicacoesContinuas entity);
}

