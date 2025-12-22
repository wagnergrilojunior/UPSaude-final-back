package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ReacoesAlergiaRequest;
import com.upsaude.api.response.embeddable.ReacoesAlergiaResponse;
import com.upsaude.entity.embeddable.ReacoesAlergia;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ReacoesAlergiaMapper {
    ReacoesAlergia toEntity(ReacoesAlergiaRequest request);
    ReacoesAlergiaResponse toResponse(ReacoesAlergia entity);
    void updateFromRequest(ReacoesAlergiaRequest request, @MappingTarget ReacoesAlergia entity);
}
