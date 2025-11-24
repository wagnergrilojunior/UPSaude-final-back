package com.upsaude.mapper;

import com.upsaude.api.request.CidadesRequest;
import com.upsaude.api.response.CidadesResponse;
import com.upsaude.dto.CidadesDTO;
import com.upsaude.entity.Cidades;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface CidadesMapper extends EntityMapper<Cidades, CidadesDTO> {

    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToLong")
    Cidades toEntity(CidadesDTO dto);

    @Mapping(target = "id", source = "id", qualifiedByName = "longToUUID")
    CidadesDTO toDTO(Cidades entity);

    Cidades fromRequest(CidadesRequest request);

    @Mapping(target = "id", source = "id", qualifiedByName = "longToUUID")
    CidadesResponse toResponse(Cidades entity);

    @Named("longToUUID")
    default UUID longToUUID(Long id) {
        if (id == null) return null;
        return new UUID(0, id);
    }

    @Named("uuidToLong")
    default Long uuidToLong(UUID id) {
        if (id == null) return null;
        return id.getLeastSignificantBits();
    }
}

