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

/**
 * Mapper para conversões de LGPDConsentimento.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface LGPDConsentimentoMapper extends EntityMapper<LGPDConsentimento, LGPDConsentimentoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    LGPDConsentimento toEntity(LGPDConsentimentoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    LGPDConsentimentoDTO toDTO(LGPDConsentimento entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    LGPDConsentimento fromRequest(LGPDConsentimentoRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(LGPDConsentimentoRequest request, @MappingTarget LGPDConsentimento entity);

    /**
     * Converte Entity para Response.
     */
    LGPDConsentimentoResponse toResponse(LGPDConsentimento entity);
}
