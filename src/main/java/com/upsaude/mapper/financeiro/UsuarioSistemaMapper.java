package com.upsaude.mapper.financeiro;

import com.upsaude.api.response.sistema.usuario.UsuarioSistemaSimplificadoResponse;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class)
public interface UsuarioSistemaMapper {

    @Named("mapUsuarioSistemaSimplificado")
    default UsuarioSistemaSimplificadoResponse mapUsuarioSistemaSimplificado(com.upsaude.entity.sistema.usuario.UsuariosSistema usuario) {
        if (usuario == null) {
            return null;
        }
        try {
            return UsuarioSistemaSimplificadoResponse.builder()
                    .id(usuario.getId())
                    .nomeExibicao(usuario.getDadosExibicao() != null ? usuario.getDadosExibicao().getNomeExibicao() : null)
                    .username(usuario.getDadosIdentificacao() != null ? usuario.getDadosIdentificacao().getUsername() : null)
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }
}
