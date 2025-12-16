package com.upsaude.mapper;

import com.upsaude.api.request.MedicacoesContinuasRequest;
import com.upsaude.api.response.MedicacoesContinuasResponse;
import com.upsaude.dto.MedicacoesContinuasDTO;
import com.upsaude.entity.MedicacoesContinuas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface MedicacoesContinuasMapper extends EntityMapper<MedicacoesContinuas, MedicacoesContinuasDTO> {

    @Mapping(target = "active", ignore = true)
    MedicacoesContinuas toEntity(MedicacoesContinuasDTO dto);

    MedicacoesContinuasDTO toDTO(MedicacoesContinuas entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    MedicacoesContinuas fromRequest(MedicacoesContinuasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(MedicacoesContinuasRequest request, @MappingTarget MedicacoesContinuas entity);

    MedicacoesContinuasResponse toResponse(MedicacoesContinuas entity);
}
