package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.InformacoesConsultaRequest;
import com.upsaude.api.response.embeddable.InformacoesConsultaResponse;
import com.upsaude.entity.embeddable.InformacoesConsulta;
import com.upsaude.dto.embeddable.InformacoesConsultaDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface InformacoesConsultaMapper {
    InformacoesConsulta toEntity(InformacoesConsultaRequest request);
    InformacoesConsultaResponse toResponse(InformacoesConsulta entity);
    void updateFromRequest(InformacoesConsultaRequest request, @MappingTarget InformacoesConsulta entity);

    InformacoesConsulta toEntity(com.upsaude.dto.embeddable.InformacoesConsultaDTO dto);
    com.upsaude.dto.embeddable.InformacoesConsultaDTO toDTO(InformacoesConsulta entity);
    void updateFromDTO(com.upsaude.dto.embeddable.InformacoesConsultaDTO dto, @MappingTarget InformacoesConsulta entity);
}
