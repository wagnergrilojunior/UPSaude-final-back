package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.InformacoesConvenioPacienteRequest;
import com.upsaude.api.response.embeddable.InformacoesConvenioPacienteResponse;
import com.upsaude.entity.embeddable.InformacoesConvenioPaciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface InformacoesConvenioPacienteMapper {
    InformacoesConvenioPaciente toEntity(InformacoesConvenioPacienteRequest request);
    InformacoesConvenioPacienteResponse toResponse(InformacoesConvenioPaciente entity);
    void updateFromRequest(InformacoesConvenioPacienteRequest request, @MappingTarget InformacoesConvenioPaciente entity);
}

