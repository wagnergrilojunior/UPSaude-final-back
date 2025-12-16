package com.upsaude.mapper;

import com.upsaude.api.request.CidadesRequest;
import com.upsaude.api.response.CidadesResponse;
import com.upsaude.dto.CidadesDTO;
import com.upsaude.entity.Cidades;
import com.upsaude.entity.Estados;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EstadosMapper.class})
public interface CidadesMapper extends EntityMapper<Cidades, CidadesDTO> {

    @Mapping(target = "active", ignore = true)
    Cidades toEntity(CidadesDTO dto);

    CidadesDTO toDTO(Cidades entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Cidades fromRequest(CidadesRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void updateFromRequest(CidadesRequest request, @MappingTarget Cidades entity);

    CidadesResponse toResponse(Cidades entity);
}
