package com.upsaude.mapper;

import com.upsaude.api.request.TratamentosOdontologicosRequest;
import com.upsaude.api.response.TratamentosOdontologicosResponse;
import com.upsaude.dto.TratamentosOdontologicosDTO;
import com.upsaude.entity.TratamentosOdontologicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de TratamentosOdontologicos.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface TratamentosOdontologicosMapper extends EntityMapper<TratamentosOdontologicos, TratamentosOdontologicosDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    TratamentosOdontologicos toEntity(TratamentosOdontologicosDTO dto);

    /**
     * Converte Entity para DTO.
     */
    TratamentosOdontologicosDTO toDTO(TratamentosOdontologicos entity);

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
    @Mapping(target = "profissional", ignore = true)
    TratamentosOdontologicos fromRequest(TratamentosOdontologicosRequest request);

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
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(TratamentosOdontologicosRequest request, @MappingTarget TratamentosOdontologicos entity);

    /**
     * Converte Entity para Response.
     */
    TratamentosOdontologicosResponse toResponse(TratamentosOdontologicos entity);
}
