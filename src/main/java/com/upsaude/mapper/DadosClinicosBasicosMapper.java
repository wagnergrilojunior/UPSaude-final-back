package com.upsaude.mapper;

import com.upsaude.api.request.DadosClinicosBasicosRequest;
import com.upsaude.api.response.DadosClinicosBasicosResponse;
import com.upsaude.dto.DadosClinicosBasicosDTO;
import com.upsaude.entity.DadosClinicosBasicos;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface DadosClinicosBasicosMapper extends EntityMapper<DadosClinicosBasicos, DadosClinicosBasicosDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    DadosClinicosBasicos toEntity(DadosClinicosBasicosDTO dto);

    @Mapping(target = "pacienteId", source = "paciente.id")
    DadosClinicosBasicosDTO toDTO(DadosClinicosBasicos entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    DadosClinicosBasicos fromRequest(DadosClinicosBasicosRequest request);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "pacienteId", source = "paciente.id")
    DadosClinicosBasicosResponse toResponse(DadosClinicosBasicos entity);

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }
}

