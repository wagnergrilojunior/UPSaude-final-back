package com.upsaude.mapper.profissional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.profissional.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.api.response.profissional.HistoricoHabilitacaoProfissionalResponse;
import com.upsaude.dto.profissional.HistoricoHabilitacaoProfissionalDTO;
import com.upsaude.entity.profissional.HistoricoHabilitacaoProfissional;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class, uses = {ProfissionaisSaudeMapper.class})
public interface HistoricoHabilitacaoProfissionalMapper extends EntityMapper<HistoricoHabilitacaoProfissional, HistoricoHabilitacaoProfissionalDTO> {

    @Mapping(target = "active", ignore = true)
    HistoricoHabilitacaoProfissional toEntity(HistoricoHabilitacaoProfissionalDTO dto);

    HistoricoHabilitacaoProfissionalDTO toDTO(HistoricoHabilitacaoProfissional entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    HistoricoHabilitacaoProfissional fromRequest(HistoricoHabilitacaoProfissionalRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(HistoricoHabilitacaoProfissionalRequest request, @MappingTarget HistoricoHabilitacaoProfissional entity);

    HistoricoHabilitacaoProfissionalResponse toResponse(HistoricoHabilitacaoProfissional entity);
}
