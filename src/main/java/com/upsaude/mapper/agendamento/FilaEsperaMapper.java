package com.upsaude.mapper.agendamento;

import com.upsaude.api.request.agendamento.FilaEsperaRequest;
import com.upsaude.api.response.agendamento.FilaEsperaResponse;
import com.upsaude.dto.FilaEsperaDTO;
import com.upsaude.entity.agendamento.FilaEspera;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {AgendamentoMapper.class, EspecialidadesMedicasMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, EstabelecimentosMapper.class})
public interface FilaEsperaMapper extends EntityMapper<FilaEspera, FilaEsperaDTO> {

    @Mapping(target = "active", ignore = true)
    FilaEspera toEntity(FilaEsperaDTO dto);

    FilaEsperaDTO toDTO(FilaEspera entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
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
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(FilaEsperaRequest request, @MappingTarget FilaEspera entity);

    FilaEsperaResponse toResponse(FilaEspera entity);
}
