package com.upsaude.mapper;

import com.upsaude.api.request.AcaoPromocaoPrevencaoRequest;
import com.upsaude.api.response.AcaoPromocaoPrevencaoResponse;
import com.upsaude.dto.AcaoPromocaoPrevencaoDTO;
import com.upsaude.entity.AcaoPromocaoPrevencao;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de AcaoPromocaoPrevencao.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EquipeSaudeMapper.class, ProfissionaisSaudeMapper.class})
public interface AcaoPromocaoPrevencaoMapper extends EntityMapper<AcaoPromocaoPrevencao, AcaoPromocaoPrevencaoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    AcaoPromocaoPrevencao toEntity(AcaoPromocaoPrevencaoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    AcaoPromocaoPrevencaoDTO toDTO(AcaoPromocaoPrevencao entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "profissionaisParticipantes", ignore = true)
    AcaoPromocaoPrevencao fromRequest(AcaoPromocaoPrevencaoRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "profissionaisParticipantes", ignore = true)
    void updateFromRequest(AcaoPromocaoPrevencaoRequest request, @MappingTarget AcaoPromocaoPrevencao entity);

    /**
     * Converte Entity para Response.
     */
    AcaoPromocaoPrevencaoResponse toResponse(AcaoPromocaoPrevencao entity);
}
