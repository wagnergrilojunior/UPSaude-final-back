package com.upsaude.mapper.enfermagem;

import com.upsaude.api.request.enfermagem.CuidadosEnfermagemRequest;
import com.upsaude.api.response.enfermagem.CuidadosEnfermagemResponse;
import com.upsaude.dto.CuidadosEnfermagemDTO;
import com.upsaude.entity.enfermagem.CuidadosEnfermagem;
import com.upsaude.entity.atendimento.Atendimento;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {AtendimentoMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface CuidadosEnfermagemMapper extends EntityMapper<CuidadosEnfermagem, CuidadosEnfermagemDTO> {

    @Mapping(target = "active", ignore = true)
    CuidadosEnfermagem toEntity(CuidadosEnfermagemDTO dto);

    CuidadosEnfermagemDTO toDTO(CuidadosEnfermagem entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    CuidadosEnfermagem fromRequest(CuidadosEnfermagemRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(CuidadosEnfermagemRequest request, @MappingTarget CuidadosEnfermagem entity);

    CuidadosEnfermagemResponse toResponse(CuidadosEnfermagem entity);
}
