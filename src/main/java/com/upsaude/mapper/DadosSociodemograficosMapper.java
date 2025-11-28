package com.upsaude.mapper;

import com.upsaude.api.request.DadosSociodemograficosRequest;
import com.upsaude.api.response.DadosSociodemograficosResponse;
import com.upsaude.dto.DadosSociodemograficosDTO;
import com.upsaude.entity.DadosSociodemograficos;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface DadosSociodemograficosMapper extends EntityMapper<DadosSociodemograficos, DadosSociodemograficosDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    DadosSociodemograficos toEntity(DadosSociodemograficosDTO dto);

    @Mapping(target = "pacienteId", source = "paciente.id")
    DadosSociodemograficosDTO toDTO(DadosSociodemograficos entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    DadosSociodemograficos fromRequest(DadosSociodemograficosRequest request);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "pacienteId", source = "paciente.id")
    DadosSociodemograficosResponse toResponse(DadosSociodemograficos entity);

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }
}

