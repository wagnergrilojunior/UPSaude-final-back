package com.upsaude.mapper.sistema;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.sistema.LGPDConsentimentoRequest;
import com.upsaude.api.response.sistema.LGPDConsentimentoResponse;
import com.upsaude.dto.sistema.LGPDConsentimentoDTO;
import com.upsaude.entity.sistema.LGPDConsentimento;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;

@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface LGPDConsentimentoMapper extends EntityMapper<LGPDConsentimento, LGPDConsentimentoDTO> {

    @Mapping(target = "active", ignore = true)
    LGPDConsentimento toEntity(LGPDConsentimentoDTO dto);

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
