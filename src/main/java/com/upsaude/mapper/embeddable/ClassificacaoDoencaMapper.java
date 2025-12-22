package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ClassificacaoDoencaRequest;
import com.upsaude.api.response.embeddable.ClassificacaoDoencaResponse;
import com.upsaude.entity.embeddable.ClassificacaoDoenca;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ClassificacaoDoencaMapper {
    ClassificacaoDoenca toEntity(ClassificacaoDoencaRequest request);
    ClassificacaoDoencaResponse toResponse(ClassificacaoDoenca entity);
    void updateFromRequest(ClassificacaoDoencaRequest request, @MappingTarget ClassificacaoDoenca entity);

}
