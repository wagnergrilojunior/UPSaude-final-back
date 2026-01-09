package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.LocalizacaoEstabelecimentoRequest;
import com.upsaude.api.response.embeddable.LocalizacaoEstabelecimentoResponse;
import com.upsaude.entity.embeddable.LocalizacaoEstabelecimento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface LocalizacaoEstabelecimentoMapper {
    LocalizacaoEstabelecimento toEntity(LocalizacaoEstabelecimentoRequest request);
    LocalizacaoEstabelecimentoResponse toResponse(LocalizacaoEstabelecimento entity);
    void updateFromRequest(LocalizacaoEstabelecimentoRequest request, @MappingTarget LocalizacaoEstabelecimento entity);
}
