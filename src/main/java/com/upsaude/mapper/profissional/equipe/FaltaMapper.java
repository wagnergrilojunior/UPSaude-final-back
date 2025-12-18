package com.upsaude.mapper.profissional.equipe;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.profissional.equipe.FaltaRequest;
import com.upsaude.api.response.profissional.equipe.FaltaResponse;
import com.upsaude.dto.profissional.equipe.FaltaDTO;
import com.upsaude.entity.profissional.equipe.Falta;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

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
