package com.upsaude.mapper;

import com.upsaude.api.request.ConselhosProfissionaisRequest;
import com.upsaude.api.response.ConselhosProfissionaisResponse;
import com.upsaude.dto.ConselhosProfissionaisDTO;
import com.upsaude.entity.ConselhosProfissionais;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ConselhosProfissionaisMapper extends EntityMapper<ConselhosProfissionais, ConselhosProfissionaisDTO> {

    @Mapping(target = "active", ignore = true)
    ConselhosProfissionais toEntity(ConselhosProfissionaisDTO dto);

    ConselhosProfissionaisDTO toDTO(ConselhosProfissionais entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    ConselhosProfissionais fromRequest(ConselhosProfissionaisRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(ConselhosProfissionaisRequest request, @MappingTarget ConselhosProfissionais entity);

    ConselhosProfissionaisResponse toResponse(ConselhosProfissionais entity);
}
