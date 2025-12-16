package com.upsaude.mapper;

import com.upsaude.api.request.DepartamentosRequest;
import com.upsaude.api.response.DepartamentosResponse;
import com.upsaude.dto.DepartamentosDTO;
import com.upsaude.entity.Departamentos;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface DepartamentosMapper extends EntityMapper<Departamentos, DepartamentosDTO> {

    @Mapping(target = "active", ignore = true)
    Departamentos toEntity(DepartamentosDTO dto);

    DepartamentosDTO toDTO(Departamentos entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    Departamentos fromRequest(DepartamentosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(DepartamentosRequest request, @MappingTarget Departamentos entity);

    DepartamentosResponse toResponse(Departamentos entity);
}
