package com.upsaude.mapper;

import com.upsaude.api.request.AtividadeProfissionalRequest;
import com.upsaude.api.response.AtividadeProfissionalResponse;
import com.upsaude.dto.AtividadeProfissionalDTO;
import com.upsaude.entity.AtividadeProfissional;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.Cirurgia;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {AtendimentoMapper.class, CirurgiaMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface AtividadeProfissionalMapper extends EntityMapper<AtividadeProfissional, AtividadeProfissionalDTO> {

    @Mapping(target = "active", ignore = true)
    AtividadeProfissional toEntity(AtividadeProfissionalDTO dto);

    AtividadeProfissionalDTO toDTO(AtividadeProfissional entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    AtividadeProfissional fromRequest(AtividadeProfissionalRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(AtividadeProfissionalRequest request, @MappingTarget AtividadeProfissional entity);

    AtividadeProfissionalResponse toResponse(AtividadeProfissional entity);
}
