package com.upsaude.mapper.equipe;

import com.upsaude.api.request.equipe.FaltaRequest;
import com.upsaude.api.response.equipe.FaltaResponse;
import com.upsaude.dto.FaltaDTO;
import com.upsaude.entity.equipe.Falta;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {MedicosMapper.class, ProfissionaisSaudeMapper.class, EstabelecimentosMapper.class})
public interface FaltaMapper extends EntityMapper<Falta, FaltaDTO> {

    @Mapping(target = "active", ignore = true)
    Falta toEntity(FaltaDTO dto);

    FaltaDTO toDTO(Falta entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    Falta fromRequest(FaltaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(FaltaRequest request, @MappingTarget Falta entity);

    FaltaResponse toResponse(Falta entity);
}
