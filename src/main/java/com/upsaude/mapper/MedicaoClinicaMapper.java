package com.upsaude.mapper;

import com.upsaude.api.request.MedicaoClinicaRequest;
import com.upsaude.api.response.MedicaoClinicaResponse;
import com.upsaude.dto.MedicaoClinicaDTO;
import com.upsaude.entity.MedicaoClinica;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

/**
 * Mapper para conversão entre entidades, DTOs, Requests e Responses de Medição Clínica.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface MedicaoClinicaMapper extends EntityMapper<MedicaoClinica, MedicaoClinicaDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    MedicaoClinica toEntity(MedicaoClinicaDTO dto);

    @Mapping(target = "pacienteId", source = "paciente.id")
    MedicaoClinicaDTO toDTO(MedicaoClinica entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    MedicaoClinica fromRequest(MedicaoClinicaRequest request);

    @Mapping(target = "pacienteId", source = "paciente.id")
    MedicaoClinicaResponse toResponse(MedicaoClinica entity);

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }
}

