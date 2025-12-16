package com.upsaude.mapper;

import com.upsaude.api.request.PermissoesRequest;
import com.upsaude.api.response.PermissoesResponse;
import com.upsaude.dto.PermissoesDTO;
import com.upsaude.entity.Permissoes;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface PermissoesMapper extends EntityMapper<Permissoes, PermissoesDTO> {

    @Mapping(target = "active", ignore = true)
    Permissoes toEntity(PermissoesDTO dto);

    PermissoesDTO toDTO(Permissoes entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Permissoes fromRequest(PermissoesRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(PermissoesRequest request, @MappingTarget Permissoes entity);

    PermissoesResponse toResponse(Permissoes entity);
}
