package com.upsaude.mapper;

import com.upsaude.api.request.ResponsavelLegalRequest;
import com.upsaude.api.response.ResponsavelLegalResponse;
import com.upsaude.dto.ResponsavelLegalDTO;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ResponsavelLegal;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface ResponsavelLegalMapper extends EntityMapper<ResponsavelLegal, ResponsavelLegalDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    ResponsavelLegal toEntity(ResponsavelLegalDTO dto);

    @Mapping(target = "pacienteId", source = "paciente.id")
    ResponsavelLegalDTO toDTO(ResponsavelLegal entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    ResponsavelLegal fromRequest(ResponsavelLegalRequest request);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "pacienteId", source = "paciente.id")
    ResponsavelLegalResponse toResponse(ResponsavelLegal entity);

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }
}

