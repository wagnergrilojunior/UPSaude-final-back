package com.upsaude.mapper;

import com.upsaude.api.request.ConvenioRequest;
import com.upsaude.api.response.ConvenioResponse;
import com.upsaude.dto.ConvenioDTO;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.Endereco;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Convenio.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EnderecoMapper.class, com.upsaude.mapper.embeddable.ContatoConvenioMapper.class, com.upsaude.mapper.embeddable.RegistroANSConvenioMapper.class, com.upsaude.mapper.embeddable.CoberturaConvenioMapper.class, com.upsaude.mapper.embeddable.InformacoesFinanceirasConvenioMapper.class, com.upsaude.mapper.embeddable.IntegracaoGovernamentalConvenioMapper.class})
public interface ConvenioMapper extends EntityMapper<Convenio, ConvenioDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Convenio toEntity(ConvenioDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ConvenioDTO toDTO(Convenio entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    Convenio fromRequest(ConvenioRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    void updateFromRequest(ConvenioRequest request, @MappingTarget Convenio entity);

    /**
     * Converte Entity para Response.
     */
    ConvenioResponse toResponse(Convenio entity);
}
