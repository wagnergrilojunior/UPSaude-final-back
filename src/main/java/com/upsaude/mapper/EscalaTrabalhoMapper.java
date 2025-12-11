package com.upsaude.mapper;

import com.upsaude.api.request.EscalaTrabalhoRequest;
import com.upsaude.api.response.EscalaTrabalhoResponse;
import com.upsaude.dto.EscalaTrabalhoDTO;
import com.upsaude.entity.EscalaTrabalho;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {MedicosMapper.class, ProfissionaisSaudeMapper.class})
public interface EscalaTrabalhoMapper extends EntityMapper<EscalaTrabalho, EscalaTrabalhoDTO> {

    @Mapping(target = "active", ignore = true)
    EscalaTrabalho toEntity(EscalaTrabalhoDTO dto);

    EscalaTrabalhoDTO toDTO(EscalaTrabalho entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    EscalaTrabalho fromRequest(EscalaTrabalhoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(EscalaTrabalhoRequest request, @MappingTarget EscalaTrabalho entity);

    EscalaTrabalhoResponse toResponse(EscalaTrabalho entity);
}
