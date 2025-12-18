package com.upsaude.mapper.planejamento;

import com.upsaude.api.request.planejamento.PlanejamentoFamiliarRequest;
import com.upsaude.api.response.planejamento.PlanejamentoFamiliarResponse;
import com.upsaude.dto.PlanejamentoFamiliarDTO;
import com.upsaude.entity.planejamento.PlanejamentoFamiliar;
import com.upsaude.entity.equipe.EquipeSaude;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
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
