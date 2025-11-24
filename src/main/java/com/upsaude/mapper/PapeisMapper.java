package com.upsaude.mapper;

import com.upsaude.api.request.PapeisRequest;
import com.upsaude.api.response.PapeisResponse;
import com.upsaude.dto.PapeisDTO;
import com.upsaude.entity.Papeis;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface PapeisMapper extends EntityMapper<Papeis, PapeisDTO> {

    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToLong")
    Papeis toEntity(PapeisDTO dto);

    @Mapping(target = "id", source = "id", qualifiedByName = "longToUUID")
    PapeisDTO toDTO(Papeis entity);

    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToLong")
    Papeis fromRequest(PapeisRequest request);

    @Mapping(target = "id", source = "id", qualifiedByName = "longToUUID")
    PapeisResponse toResponse(Papeis entity);

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

