package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.SintomasDoencaRequest;
import com.upsaude.api.response.embeddable.SintomasDoencaResponse;
import com.upsaude.entity.embeddable.SintomasDoenca;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface SintomasDoencaMapper {
    SintomasDoenca toEntity(SintomasDoencaRequest request);
    SintomasDoencaResponse toResponse(SintomasDoenca entity);
    void updateFromRequest(SintomasDoencaRequest request, @MappingTarget SintomasDoenca entity);

}
