package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosIdentificacaoEstabelecimentoRequest;
import com.upsaude.api.response.embeddable.DadosIdentificacaoEstabelecimentoResponse;
import com.upsaude.entity.embeddable.DadosIdentificacaoEstabelecimento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosIdentificacaoEstabelecimentoMapper {
    DadosIdentificacaoEstabelecimento toEntity(DadosIdentificacaoEstabelecimentoRequest request);
    DadosIdentificacaoEstabelecimentoResponse toResponse(DadosIdentificacaoEstabelecimento entity);
    void updateFromRequest(DadosIdentificacaoEstabelecimentoRequest request, @MappingTarget DadosIdentificacaoEstabelecimento entity);
}
