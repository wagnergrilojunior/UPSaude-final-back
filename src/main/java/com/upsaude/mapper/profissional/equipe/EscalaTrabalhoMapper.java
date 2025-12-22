package com.upsaude.mapper.profissional.equipe;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.profissional.equipe.EscalaTrabalhoRequest;
import com.upsaude.api.response.profissional.equipe.EscalaTrabalhoResponse;
import com.upsaude.entity.profissional.equipe.EscalaTrabalho;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

@Mapper(config = MappingConfig.class, uses = {MedicosMapper.class, ProfissionaisSaudeMapper.class})
public interface EscalaTrabalhoMapper  {

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
