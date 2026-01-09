package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.EncaminhamentoConsultaRequest;
import com.upsaude.api.response.embeddable.EncaminhamentoConsultaResponse;
import com.upsaude.entity.embeddable.EncaminhamentoConsulta;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface EncaminhamentoConsultaMapper {
    EncaminhamentoConsulta toEntity(EncaminhamentoConsultaRequest request);
    EncaminhamentoConsultaResponse toResponse(EncaminhamentoConsulta entity);
    void updateFromRequest(EncaminhamentoConsultaRequest request, @MappingTarget EncaminhamentoConsulta entity);

}
