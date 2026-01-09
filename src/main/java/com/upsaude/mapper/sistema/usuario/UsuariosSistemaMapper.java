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
import org.mapstruct.factory.Mappers;

@Mapper(config = MappingConfig.class, uses = {
        DadosIdentificacaoUsuarioMapper.class,
        DadosExibicaoUsuarioMapper.class,
        ConfiguracaoUsuarioMapper.class
})
public interface UsuariosSistemaMapper {

    DadosIdentificacaoUsuarioMapper dadosIdentificacaoMapper = Mappers.getMapper(DadosIdentificacaoUsuarioMapper.class);
    DadosExibicaoUsuarioMapper dadosExibicaoMapper = Mappers.getMapper(DadosExibicaoUsuarioMapper.class);
    ConfiguracaoUsuarioMapper configuracaoMapper = Mappers.getMapper(ConfiguracaoUsuarioMapper.class);

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
    @Mapping(target = "dadosIdentificacao", ignore = true)
    @Mapping(target = "dadosExibicao", ignore = true)
    @Mapping(target = "configuracao", ignore = true)
    void updateFromRequest(UsuariosSistemaRequest request, @MappingTarget UsuariosSistema entity);

    /**
     * Atualiza os dados de identificação, exibição e configuração preservando valores existentes
     */
    default void updateEmbeddablesFromRequest(UsuariosSistemaRequest request, @MappingTarget UsuariosSistema entity) {
        if (request == null) {
            return;
        }

        // Garantir que os objetos embeddados existam
        if (entity.getDadosIdentificacao() == null) {
            entity.setDadosIdentificacao(new com.upsaude.entity.embeddable.DadosIdentificacaoUsuario());
        }
        if (entity.getDadosExibicao() == null) {
            entity.setDadosExibicao(new com.upsaude.entity.embeddable.DadosExibicaoUsuario());
        }
        if (entity.getConfiguracao() == null) {
            entity.setConfiguracao(new com.upsaude.entity.embeddable.ConfiguracaoUsuario());
        }

        // Atualizar dados de identificação preservando valores existentes
        if (request.getDadosIdentificacao() != null) {
            dadosIdentificacaoMapper.updateFromRequest(request.getDadosIdentificacao(), entity.getDadosIdentificacao());
        }

        // Atualizar dados de exibição preservando valores existentes
        if (request.getDadosExibicao() != null) {
            dadosExibicaoMapper.updateFromRequest(request.getDadosExibicao(), entity.getDadosExibicao());
        }

        // Atualizar configuração preservando valores existentes
        if (request.getConfiguracao() != null) {
            configuracaoMapper.updateFromRequest(request.getConfiguracao(), entity.getConfiguracao());
        }
    }

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "tenantNome", ignore = true)
    @Mapping(target = "tenantSlug", ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    UsuariosSistemaResponse toResponse(UsuariosSistema entity);
}
