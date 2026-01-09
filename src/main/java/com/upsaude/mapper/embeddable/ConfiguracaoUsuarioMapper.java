package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ConfiguracaoUsuarioRequest;
import com.upsaude.api.response.embeddable.ConfiguracaoUsuarioResponse;
import com.upsaude.entity.embeddable.ConfiguracaoUsuario;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ConfiguracaoUsuarioMapper {

    ConfiguracaoUsuario fromRequest(ConfiguracaoUsuarioRequest request);

    ConfiguracaoUsuarioResponse toResponse(ConfiguracaoUsuario entity);

    /**
     * Atualiza a entidade embeddada preservando valores existentes quando o request não fornece esses valores
     */
    default void updateFromRequest(ConfiguracaoUsuarioRequest request, @MappingTarget ConfiguracaoUsuario entity) {
        if (request == null) {
            return;
        }
        
        // Preservar valores existentes se o request não fornecer
        if (request.getAdminTenant() != null) {
            entity.setAdminTenant(request.getAdminTenant());
        }
        
        if (request.getTipoVinculo() != null && !request.getTipoVinculo().trim().isEmpty()) {
            entity.setTipoVinculo(request.getTipoVinculo());
        }
    }
}
