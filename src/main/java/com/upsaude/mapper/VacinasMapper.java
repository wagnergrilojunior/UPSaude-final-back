package com.upsaude.mapper;

import com.upsaude.api.request.VacinasRequest;
import com.upsaude.api.response.VacinasResponse;
import com.upsaude.dto.VacinasDTO;
import com.upsaude.entity.Vacinas;
import com.upsaude.entity.FabricantesVacina;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Vacinas.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {FabricantesVacinaMapper.class, com.upsaude.mapper.embeddable.EsquemaVacinalMapper.class, com.upsaude.mapper.embeddable.IdadeAplicacaoVacinaMapper.class, com.upsaude.mapper.embeddable.ContraindicacoesVacinaMapper.class, com.upsaude.mapper.embeddable.ConservacaoVacinaMapper.class, com.upsaude.mapper.embeddable.ComposicaoVacinaMapper.class, com.upsaude.mapper.embeddable.EficaciaVacinaMapper.class, com.upsaude.mapper.embeddable.ReacoesAdversasVacinaMapper.class, com.upsaude.mapper.embeddable.CalendarioVacinalMapper.class, com.upsaude.mapper.embeddable.IntegracaoGovernamentalVacinaMapper.class})
public interface VacinasMapper extends EntityMapper<Vacinas, VacinasDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Vacinas toEntity(VacinasDTO dto);

    /**
     * Converte Entity para DTO.
     */
    VacinasDTO toDTO(Vacinas entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", ignore = true)
    Vacinas fromRequest(VacinasRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", ignore = true)
    void updateFromRequest(VacinasRequest request, @MappingTarget Vacinas entity);

    /**
     * Converte Entity para Response.
     */
    VacinasResponse toResponse(Vacinas entity);
}
