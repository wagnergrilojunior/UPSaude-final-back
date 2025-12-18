package com.upsaude.mapper.profissional;

import com.upsaude.api.request.profissional.AtividadeProfissionalRequest;
import com.upsaude.api.response.profissional.AtividadeProfissionalResponse;
import com.upsaude.dto.AtividadeProfissionalDTO;
import com.upsaude.entity.profissional.AtividadeProfissional;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {AtendimentoMapper.class, CirurgiaMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, EstabelecimentosMapper.class})
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
    @Mapping(target = "estabelecimento", ignore = true)
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
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(AtividadeProfissionalRequest request, @MappingTarget AtividadeProfissional entity);

    AtividadeProfissionalResponse toResponse(AtividadeProfissional entity);
}
