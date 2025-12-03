package com.upsaude.mapper;

import com.upsaude.api.request.VacinacoesRequest;
import com.upsaude.api.response.VacinacoesResponse;
import com.upsaude.dto.VacinacoesDTO;
import com.upsaude.entity.Vacinacoes;
import com.upsaude.entity.FabricantesVacina;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Vacinas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Vacinacoes.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {FabricantesVacinaMapper.class, PacienteMapper.class, VacinasMapper.class})
public interface VacinacoesMapper extends EntityMapper<Vacinacoes, VacinacoesDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Vacinacoes toEntity(VacinacoesDTO dto);

    /**
     * Converte Entity para DTO.
     */
    VacinacoesDTO toDTO(Vacinacoes entity);

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
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "vacina", ignore = true)
    Vacinacoes fromRequest(VacinacoesRequest request);

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
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "vacina", ignore = true)
    void updateFromRequest(VacinacoesRequest request, @MappingTarget Vacinacoes entity);

    /**
     * Converte Entity para Response.
     */
    VacinacoesResponse toResponse(Vacinacoes entity);
}
