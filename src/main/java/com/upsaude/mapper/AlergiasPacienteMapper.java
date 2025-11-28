package com.upsaude.mapper;

import com.upsaude.api.request.AlergiasPacienteRequest;
import com.upsaude.api.response.AlergiasPacienteResponse;
import com.upsaude.dto.AlergiasPacienteDTO;
import com.upsaude.entity.Alergias;
import com.upsaude.entity.AlergiasPaciente;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface AlergiasPacienteMapper extends EntityMapper<AlergiasPaciente, AlergiasPacienteDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "alergia", source = "alergiaId", qualifiedByName = "alergiaFromId")
    AlergiasPaciente toEntity(AlergiasPacienteDTO dto);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "alergiaId", source = "alergia.id")
    AlergiasPacienteDTO toDTO(AlergiasPaciente entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "alergia", source = "alergiaId", qualifiedByName = "alergiaFromId")
    AlergiasPaciente fromRequest(AlergiasPacienteRequest request);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "pacienteNome", source = "paciente", qualifiedByName = "pacienteNome")
    @Mapping(target = "alergiaId", source = "alergia.id")
    @Mapping(target = "alergiaNome", source = "alergia.nome")
    AlergiasPacienteResponse toResponse(AlergiasPaciente entity);

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }

    @Named("alergiaFromId")
    default Alergias alergiaFromId(UUID id) {
        if (id == null) return null;
        Alergias a = new Alergias();
        a.setId(id);
        return a;
    }

    @Named("pacienteNome")
    default String pacienteNome(Paciente paciente) {
        if (paciente == null) return null;
        return paciente.getNomeCompleto();
    }
}
