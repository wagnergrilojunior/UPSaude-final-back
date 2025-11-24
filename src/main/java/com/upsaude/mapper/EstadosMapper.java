package com.upsaude.mapper;

import com.upsaude.api.request.EstadosRequest;
import com.upsaude.api.response.EstadosResponse;
import com.upsaude.dto.EstadosDTO;
import com.upsaude.entity.Estados;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface EstadosMapper extends EntityMapper<Estados, EstadosDTO> {

    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToLong")
    Estados toEntity(EstadosDTO dto);

    @Mapping(target = "id", source = "id", qualifiedByName = "longToUUID")
    EstadosDTO toDTO(Estados entity);

    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToLong")
    Estados fromRequest(EstadosRequest request);

    @Mapping(target = "id", source = "id", qualifiedByName = "longToUUID")
    EstadosResponse toResponse(Estados entity);

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

