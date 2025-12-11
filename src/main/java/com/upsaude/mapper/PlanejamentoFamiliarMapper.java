package com.upsaude.mapper;

import com.upsaude.api.request.PlanejamentoFamiliarRequest;
import com.upsaude.api.response.PlanejamentoFamiliarResponse;
import com.upsaude.dto.PlanejamentoFamiliarDTO;
import com.upsaude.entity.PlanejamentoFamiliar;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EquipeSaudeMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface PlanejamentoFamiliarMapper extends EntityMapper<PlanejamentoFamiliar, PlanejamentoFamiliarDTO> {

    @Mapping(target = "active", ignore = true)
    PlanejamentoFamiliar toEntity(PlanejamentoFamiliarDTO dto);

    PlanejamentoFamiliarDTO toDTO(PlanejamentoFamiliar entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    PlanejamentoFamiliar fromRequest(PlanejamentoFamiliarRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    void updateFromRequest(PlanejamentoFamiliarRequest request, @MappingTarget PlanejamentoFamiliar entity);

    PlanejamentoFamiliarResponse toResponse(PlanejamentoFamiliar entity);
}
