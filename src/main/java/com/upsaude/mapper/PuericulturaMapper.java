package com.upsaude.mapper;

import com.upsaude.api.request.PuericulturaRequest;
import com.upsaude.api.response.PuericulturaResponse;
import com.upsaude.dto.PuericulturaDTO;
import com.upsaude.entity.Puericultura;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EquipeSaudeMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface PuericulturaMapper extends EntityMapper<Puericultura, PuericulturaDTO> {

    @Mapping(target = "active", ignore = true)
    Puericultura toEntity(PuericulturaDTO dto);

    PuericulturaDTO toDTO(Puericultura entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    Puericultura fromRequest(PuericulturaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    void updateFromRequest(PuericulturaRequest request, @MappingTarget Puericultura entity);

    PuericulturaResponse toResponse(Puericultura entity);
}
