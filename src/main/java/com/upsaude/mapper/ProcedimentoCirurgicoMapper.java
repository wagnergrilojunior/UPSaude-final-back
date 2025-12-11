package com.upsaude.mapper;

import com.upsaude.api.request.ProcedimentoCirurgicoRequest;
import com.upsaude.api.response.ProcedimentoCirurgicoResponse;
import com.upsaude.dto.ProcedimentoCirurgicoDTO;
import com.upsaude.entity.ProcedimentoCirurgico;
import com.upsaude.entity.Cirurgia;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {CirurgiaMapper.class})
public interface ProcedimentoCirurgicoMapper extends EntityMapper<ProcedimentoCirurgico, ProcedimentoCirurgicoDTO> {

    @Mapping(target = "active", ignore = true)
    ProcedimentoCirurgico toEntity(ProcedimentoCirurgicoDTO dto);

    ProcedimentoCirurgicoDTO toDTO(ProcedimentoCirurgico entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    ProcedimentoCirurgico fromRequest(ProcedimentoCirurgicoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    void updateFromRequest(ProcedimentoCirurgicoRequest request, @MappingTarget ProcedimentoCirurgico entity);

    ProcedimentoCirurgicoResponse toResponse(ProcedimentoCirurgico entity);
}
