package com.upsaude.mapper.sistema.lgpd;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.sistema.lgpd.LGPDConsentimentoRequest;
import com.upsaude.api.response.sistema.lgpd.LGPDConsentimentoResponse;
import com.upsaude.entity.sistema.lgpd.LGPDConsentimento;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;

@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface LGPDConsentimentoMapper  {

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
