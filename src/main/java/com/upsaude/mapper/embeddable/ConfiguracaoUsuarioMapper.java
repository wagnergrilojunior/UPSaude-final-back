package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ConfiguracaoUsuarioRequest;
import com.upsaude.api.response.embeddable.ConfiguracaoUsuarioResponse;
import com.upsaude.entity.embeddable.ConfiguracaoUsuario;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfig.class)
public interface ConfiguracaoUsuarioMapper {

    ConfiguracaoUsuario fromRequest(ConfiguracaoUsuarioRequest request);

    ConfiguracaoUsuarioResponse toResponse(ConfiguracaoUsuario entity);
}

