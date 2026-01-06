package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ContatoEstabelecimentoRequest;
import com.upsaude.api.response.embeddable.ContatoEstabelecimentoResponse;
import com.upsaude.entity.embeddable.ContatoEstabelecimento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ContatoEstabelecimentoMapper {
    ContatoEstabelecimento toEntity(ContatoEstabelecimentoRequest request);
    ContatoEstabelecimentoResponse toResponse(ContatoEstabelecimento entity);
    void updateFromRequest(ContatoEstabelecimentoRequest request, @MappingTarget ContatoEstabelecimento entity);
}
