package com.upsaude.mapper.sistema.usuario;

import com.upsaude.api.request.sistema.usuario.UsuariosSistemaRequest;
import com.upsaude.api.response.sistema.usuario.UsuariosSistemaResponse;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.embeddable.ConfiguracaoUsuarioMapper;
import com.upsaude.mapper.embeddable.DadosExibicaoUsuarioMapper;
import com.upsaude.mapper.embeddable.DadosIdentificacaoUsuarioMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {
        DadosIdentificacaoUsuarioMapper.class,
        DadosExibicaoUsuarioMapper.class,
        ConfiguracaoUsuarioMapper.class
})
public interface UsuariosSistemaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimentosVinculados", ignore = true)
    UsuariosSistema fromRequest(UsuariosSistemaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimentosVinculados", ignore = true)
    void updateFromRequest(UsuariosSistemaRequest request, @MappingTarget UsuariosSistema entity);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "tenantNome", ignore = true)
    @Mapping(target = "tenantSlug", ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    UsuariosSistemaResponse toResponse(UsuariosSistema entity);
}
