package com.upsaude.mapper;

import com.upsaude.api.request.LGPDConsentimentoRequest;
import com.upsaude.api.response.LGPDConsentimentoResponse;
import com.upsaude.dto.LGPDConsentimentoDTO;
import com.upsaude.entity.LGPDConsentimento;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface LGPDConsentimentoMapper extends EntityMapper<LGPDConsentimento, LGPDConsentimentoDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    LGPDConsentimento toEntity(LGPDConsentimentoDTO dto);

    @Mapping(target = "pacienteId", source = "paciente.id")
    LGPDConsentimentoDTO toDTO(LGPDConsentimento entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    LGPDConsentimento fromRequest(LGPDConsentimentoRequest request);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "pacienteId", source = "paciente.id")
    LGPDConsentimentoResponse toResponse(LGPDConsentimento entity);

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }
}

