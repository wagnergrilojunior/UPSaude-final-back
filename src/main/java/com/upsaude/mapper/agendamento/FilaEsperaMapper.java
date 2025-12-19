package com.upsaude.mapper.agendamento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.agendamento.FilaEsperaRequest;
import com.upsaude.api.response.agendamento.FilaEsperaResponse;
import com.upsaude.entity.agendamento.FilaEspera;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

@Mapper(config = MappingConfig.class, uses = {AgendamentoMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, EstabelecimentosMapper.class})
public interface FilaEsperaMapper {

    @Mapping(target = "active", ignore = true)
    FilaEspera toEntity(FilaEsperaResponse dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    FilaEspera fromRequest(FilaEsperaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(FilaEsperaRequest request, @MappingTarget FilaEspera entity);

    FilaEsperaResponse toResponse(FilaEspera entity);
}
