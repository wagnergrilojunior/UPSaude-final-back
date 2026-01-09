package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosExibicaoUsuarioRequest;
import com.upsaude.api.response.embeddable.DadosExibicaoUsuarioResponse;
import com.upsaude.entity.embeddable.DadosExibicaoUsuario;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosExibicaoUsuarioMapper {

    DadosExibicaoUsuario fromRequest(DadosExibicaoUsuarioRequest request);

    DadosExibicaoUsuarioResponse toResponse(DadosExibicaoUsuario entity);

    /**
     * Atualiza a entidade embeddada preservando valores existentes quando o request não fornece esses valores
     */
    default void updateFromRequest(DadosExibicaoUsuarioRequest request, @MappingTarget DadosExibicaoUsuario entity) {
        if (request == null) {
            return;
        }
        
        // Preservar valores existentes se o request não fornecer
        if (request.getNomeExibicao() != null && !request.getNomeExibicao().trim().isEmpty()) {
            entity.setNomeExibicao(request.getNomeExibicao());
        }
        
        if (request.getFotoUrl() != null && !request.getFotoUrl().trim().isEmpty()) {
            entity.setFotoUrl(request.getFotoUrl());
        }
    }
}
