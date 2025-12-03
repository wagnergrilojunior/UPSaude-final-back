package com.upsaude.mapper;

import com.upsaude.api.request.EnderecoRequest;
import com.upsaude.api.response.EnderecoResponse;
import com.upsaude.dto.EnderecoDTO;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.Cidades;
import com.upsaude.entity.Estados;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Endereco.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {CidadesMapper.class, EstadosMapper.class})
public interface EnderecoMapper extends EntityMapper<Endereco, EnderecoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Endereco toEntity(EnderecoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    EnderecoDTO toDTO(Endereco entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidade", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Endereco fromRequest(EnderecoRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidade", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void updateFromRequest(EnderecoRequest request, @MappingTarget Endereco entity);

    /**
     * Converte Entity para Response.
     */
    EnderecoResponse toResponse(Endereco entity);
}
