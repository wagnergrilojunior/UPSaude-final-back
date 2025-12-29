package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosExibicaoUsuarioRequest;
import com.upsaude.api.response.embeddable.DadosExibicaoUsuarioResponse;
import com.upsaude.entity.embeddable.DadosExibicaoUsuario;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfig.class)
public interface DadosExibicaoUsuarioMapper {

    DadosExibicaoUsuario fromRequest(DadosExibicaoUsuarioRequest request);

    DadosExibicaoUsuarioResponse toResponse(DadosExibicaoUsuario entity);
}

