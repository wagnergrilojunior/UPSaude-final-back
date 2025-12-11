package com.upsaude.mapper;

import com.upsaude.api.request.LGPDConsentimentoRequest;
import com.upsaude.api.response.LGPDConsentimentoResponse;
import com.upsaude.dto.LGPDConsentimentoDTO;
import com.upsaude.entity.LGPDConsentimento;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface LGPDConsentimentoMapper extends EntityMapper<LGPDConsentimento, LGPDConsentimentoDTO> {

    @Mapping(target = "active", ignore = true)
    LGPDConsentimento toEntity(LGPDConsentimentoDTO dto);

    LGPDConsentimentoDTO toDTO(LGPDConsentimento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    LGPDConsentimento fromRequest(LGPDConsentimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(LGPDConsentimentoRequest request, @MappingTarget LGPDConsentimento entity);

    LGPDConsentimentoResponse toResponse(LGPDConsentimento entity);
}
