package com.upsaude.mapper;

import com.upsaude.api.request.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.api.response.HistoricoHabilitacaoProfissionalResponse;
import com.upsaude.dto.HistoricoHabilitacaoProfissionalDTO;
import com.upsaude.entity.HistoricoHabilitacaoProfissional;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
