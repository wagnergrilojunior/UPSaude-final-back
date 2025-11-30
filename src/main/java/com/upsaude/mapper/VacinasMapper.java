package com.upsaude.mapper;

import com.upsaude.api.request.VacinasRequest;
import com.upsaude.api.response.VacinasResponse;
import com.upsaude.dto.VacinasDTO;
import com.upsaude.entity.FabricantesVacina;
import com.upsaude.entity.Vacinas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface VacinasMapper extends EntityMapper<Vacinas, VacinasDTO> {

    @Mapping(target = "fabricante", source = "fabricanteId", qualifiedByName = "fabricanteFromId")
    Vacinas toEntity(VacinasDTO dto);

    @Mapping(target = "fabricanteId", source = "fabricante.id")
    VacinasDTO toDTO(Vacinas entity);

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", source = "fabricanteId", qualifiedByName = "fabricanteFromId")
    Vacinas fromRequest(VacinasRequest request);

    @Mapping(target = "fabricanteId", source = "fabricante.id")
    @Mapping(target = "fabricanteNome", source = "fabricante.nome")
    VacinasResponse toResponse(Vacinas entity);

    @Named("fabricanteFromId")
    default FabricantesVacina fabricanteFromId(UUID id) {
        if (id == null) return null;
        FabricantesVacina f = new FabricantesVacina();
        f.setId(id);
        return f;
    }
}

