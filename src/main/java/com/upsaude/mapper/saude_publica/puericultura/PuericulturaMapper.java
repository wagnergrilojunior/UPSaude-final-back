package com.upsaude.mapper.saude_publica.puericultura;

import com.upsaude.api.request.saude_publica.puericultura.PuericulturaRequest;
import com.upsaude.api.response.saude_publica.puericultura.PuericulturaResponse;
import com.upsaude.dto.saude_publica.puericultura.PuericulturaDTO;
import com.upsaude.entity.saude_publica.puericultura.Puericultura;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.equipe.EquipeSaudeMapper;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
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
