package com.upsaude.mapper;

import com.upsaude.api.request.IntegracaoGovRequest;
import com.upsaude.api.response.IntegracaoGovResponse;
import com.upsaude.dto.IntegracaoGovDTO;
import com.upsaude.entity.IntegracaoGov;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface IntegracaoGovMapper extends EntityMapper<IntegracaoGov, IntegracaoGovDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    IntegracaoGov toEntity(IntegracaoGovDTO dto);

    @Mapping(target = "pacienteId", source = "paciente.id")
    IntegracaoGovDTO toDTO(IntegracaoGov entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    IntegracaoGov fromRequest(IntegracaoGovRequest request);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "pacienteId", source = "paciente.id")
    IntegracaoGovResponse toResponse(IntegracaoGov entity);

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }
}

