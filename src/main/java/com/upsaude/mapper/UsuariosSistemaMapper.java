package com.upsaude.mapper;

import com.upsaude.api.request.UsuariosSistemaRequest;
import com.upsaude.api.response.UsuariosSistemaResponse;
import com.upsaude.dto.UsuariosSistemaDTO;
import com.upsaude.entity.UsuariosSistema;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de UsuariosSistema.
 * Entity ↔ DTO ↔ Request/Response
 * Relacionamentos são ignorados para evitar dependências circulares
 */
@Mapper(config = MappingConfig.class)
public interface UsuariosSistemaMapper extends EntityMapper<UsuariosSistema, UsuariosSistemaDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    UsuariosSistema toEntity(UsuariosSistemaDTO dto);

    /**
     * Converte Entity para DTO.
     */
    UsuariosSistemaDTO toDTO(UsuariosSistema entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimentosVinculados", ignore = true)
    UsuariosSistema fromRequest(UsuariosSistemaRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimentosVinculados", ignore = true)
    void updateFromRequest(UsuariosSistemaRequest request, @MappingTarget UsuariosSistema entity);

    /**
     * Converte Entity para Response.
     * Relacionamentos complexos são ignorados para evitar carregamento lazy.
     */
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "tenantNome", ignore = true)
    @Mapping(target = "tenantSlug", ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(target = "email", ignore = true)
    UsuariosSistemaResponse toResponse(UsuariosSistema entity);
}
