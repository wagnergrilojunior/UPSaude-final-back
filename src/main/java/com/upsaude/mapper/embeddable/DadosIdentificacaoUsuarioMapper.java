package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosIdentificacaoUsuarioRequest;
import com.upsaude.api.response.embeddable.DadosIdentificacaoUsuarioResponse;
import com.upsaude.entity.embeddable.DadosIdentificacaoUsuario;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosIdentificacaoUsuarioMapper {

    DadosIdentificacaoUsuario fromRequest(DadosIdentificacaoUsuarioRequest request);

    DadosIdentificacaoUsuarioResponse toResponse(DadosIdentificacaoUsuario entity);

    /**
     * Atualiza a entidade embeddada preservando valores existentes quando o request não fornece esses valores
     */
    default void updateFromRequest(DadosIdentificacaoUsuarioRequest request, @MappingTarget DadosIdentificacaoUsuario entity) {
        if (request == null) {
            return;
        }
        
        // Preservar valores existentes se o request não fornecer
        if (request.getUsername() != null && !request.getUsername().trim().isEmpty()) {
            entity.setUsername(request.getUsername());
        }
        
        if (request.getCpf() != null && !request.getCpf().trim().isEmpty()) {
            entity.setCpf(request.getCpf());
        }
    }
}
