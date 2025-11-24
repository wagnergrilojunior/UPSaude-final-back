package com.upsaude.mapper;

import com.upsaude.api.request.CidadesRequest;
import com.upsaude.api.response.CidadesResponse;
import com.upsaude.dto.CidadesDTO;
import com.upsaude.entity.Cidades;
import com.upsaude.entity.Estados;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface CidadesMapper extends EntityMapper<Cidades, CidadesDTO> {

    @Mapping(target = "active", ignore = true)
    Cidades toEntity(CidadesDTO dto);

    CidadesDTO toDTO(Cidades entity);

    @Mapping(target = "active", ignore = true)
    Cidades fromRequest(CidadesRequest request);

    CidadesResponse toResponse(Cidades entity);

    @Named("estadoFromId")
    default Estados estadoFromId(UUID id) {
        if (id == null) return null;
        Estados e = new Estados();
        e.setId(id);
        return e;
    }
}

