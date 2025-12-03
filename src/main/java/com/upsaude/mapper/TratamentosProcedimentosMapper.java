package com.upsaude.mapper;

import com.upsaude.api.request.TratamentosProcedimentosRequest;
import com.upsaude.api.response.TratamentosProcedimentosResponse;
import com.upsaude.dto.TratamentosProcedimentosDTO;
import com.upsaude.entity.TratamentosProcedimentos;
import com.upsaude.entity.ProcedimentosOdontologicos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.TratamentosOdontologicos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de TratamentosProcedimentos.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {ProcedimentosOdontologicosMapper.class, ProfissionaisSaudeMapper.class, TratamentosOdontologicosMapper.class})
public interface TratamentosProcedimentosMapper extends EntityMapper<TratamentosProcedimentos, TratamentosProcedimentosDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    TratamentosProcedimentos toEntity(TratamentosProcedimentosDTO dto);

    /**
     * Converte Entity para DTO.
     */
    TratamentosProcedimentosDTO toDTO(TratamentosProcedimentos entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "procedimento", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "tratamento", ignore = true)
    TratamentosProcedimentos fromRequest(TratamentosProcedimentosRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "procedimento", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "tratamento", ignore = true)
    void updateFromRequest(TratamentosProcedimentosRequest request, @MappingTarget TratamentosProcedimentos entity);

    /**
     * Converte Entity para Response.
     */
    TratamentosProcedimentosResponse toResponse(TratamentosProcedimentos entity);
}
