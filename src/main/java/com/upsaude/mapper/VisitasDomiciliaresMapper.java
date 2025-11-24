package com.upsaude.mapper;

import com.upsaude.api.request.VisitasDomiciliaresRequest;
import com.upsaude.api.response.VisitasDomiciliaresResponse;
import com.upsaude.dto.VisitasDomiciliaresDTO;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.VisitasDomiciliares;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface VisitasDomiciliaresMapper extends EntityMapper<VisitasDomiciliares, VisitasDomiciliaresDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    VisitasDomiciliares toEntity(VisitasDomiciliaresDTO dto);

    @Mapping(target = "pacienteId", source = "paciente.id")
    VisitasDomiciliaresDTO toDTO(VisitasDomiciliares entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    VisitasDomiciliares fromRequest(VisitasDomiciliaresRequest request);

    @Mapping(target = "pacienteId", source = "paciente.id")
    VisitasDomiciliaresResponse toResponse(VisitasDomiciliares entity);

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }
}

