package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosIdentificacaoUsuarioRequest;
import com.upsaude.api.response.embeddable.DadosIdentificacaoUsuarioResponse;
import com.upsaude.entity.embeddable.DadosIdentificacaoUsuario;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfig.class)
public interface DadosIdentificacaoUsuarioMapper {

    DadosIdentificacaoUsuario fromRequest(DadosIdentificacaoUsuarioRequest request);

    DadosIdentificacaoUsuarioResponse toResponse(DadosIdentificacaoUsuario entity);
}
