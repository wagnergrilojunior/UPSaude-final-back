package com.upsaude.mapper;

import com.upsaude.api.request.FaltaRequest;
import com.upsaude.api.response.FaltaResponse;
import com.upsaude.dto.FaltaDTO;
import com.upsaude.entity.Falta;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {MedicosMapper.class, ProfissionaisSaudeMapper.class})
public interface FaltaMapper extends EntityMapper<Falta, FaltaDTO> {

    @Mapping(target = "active", ignore = true)
    Falta toEntity(FaltaDTO dto);

    FaltaDTO toDTO(Falta entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    Falta fromRequest(FaltaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(FaltaRequest request, @MappingTarget Falta entity);

    FaltaResponse toResponse(Falta entity);
}
