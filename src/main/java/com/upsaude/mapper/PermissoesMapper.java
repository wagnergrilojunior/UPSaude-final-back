package com.upsaude.mapper;

import com.upsaude.api.request.PermissoesRequest;
import com.upsaude.api.response.PermissoesResponse;
import com.upsaude.dto.PermissoesDTO;
import com.upsaude.entity.Permissoes;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface PermissoesMapper extends EntityMapper<Permissoes, PermissoesDTO> {

    @Mapping(target = "tenant", ignore = true)
    Permissoes toEntity(PermissoesDTO dto);

    PermissoesDTO toDTO(Permissoes entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    Permissoes fromRequest(PermissoesRequest request);

    PermissoesResponse toResponse(Permissoes entity);
}

