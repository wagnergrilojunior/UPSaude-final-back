package com.upsaude.mapper;

import com.upsaude.api.request.FilaEsperaRequest;
import com.upsaude.api.response.FilaEsperaResponse;
import com.upsaude.dto.FilaEsperaDTO;
import com.upsaude.entity.FilaEspera;
import com.upsaude.entity.Agendamento;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {AgendamentoMapper.class, EspecialidadesMedicasMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface FilaEsperaMapper extends EntityMapper<FilaEspera, FilaEsperaDTO> {

    @Mapping(target = "active", ignore = true)
    FilaEspera toEntity(FilaEsperaDTO dto);

    FilaEsperaDTO toDTO(FilaEspera entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
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
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(FilaEsperaRequest request, @MappingTarget FilaEspera entity);

    FilaEsperaResponse toResponse(FilaEspera entity);
}
