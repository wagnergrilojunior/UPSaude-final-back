package com.upsaude.mapper.sistema;

import com.upsaude.api.request.sistema.UsuariosSistemaRequest;
import com.upsaude.api.response.sistema.UsuariosSistemaResponse;
import com.upsaude.dto.UsuariosSistemaDTO;
import com.upsaude.entity.sistema.UsuariosSistema;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface UsuariosSistemaMapper extends EntityMapper<UsuariosSistema, UsuariosSistemaDTO> {

    @Mapping(target = "active", ignore = true)
    UsuariosSistema toEntity(UsuariosSistemaDTO dto);

    UsuariosSistemaDTO toDTO(UsuariosSistema entity);

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
