package com.upsaude.mapper.sistema.usuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.sistema.usuario.UsuariosSistemaRequest;
import com.upsaude.api.response.sistema.usuario.UsuariosSistemaResponse;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface UsuariosSistemaMapper  {

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
