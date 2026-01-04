package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.LicenciamentoEstabelecimentoRequest;
import com.upsaude.api.response.embeddable.LicenciamentoEstabelecimentoResponse;
import com.upsaude.entity.embeddable.LicenciamentoEstabelecimento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface LicenciamentoEstabelecimentoMapper {
    LicenciamentoEstabelecimento toEntity(LicenciamentoEstabelecimentoRequest request);
    LicenciamentoEstabelecimentoResponse toResponse(LicenciamentoEstabelecimento entity);
    void updateFromRequest(LicenciamentoEstabelecimentoRequest request, @MappingTarget LicenciamentoEstabelecimento entity);
}

