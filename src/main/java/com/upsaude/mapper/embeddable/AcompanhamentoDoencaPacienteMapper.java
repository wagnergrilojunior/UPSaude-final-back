package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.AcompanhamentoDoencaPacienteRequest;
import com.upsaude.api.response.embeddable.AcompanhamentoDoencaPacienteResponse;
import com.upsaude.entity.embeddable.AcompanhamentoDoencaPaciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface AcompanhamentoDoencaPacienteMapper {
    AcompanhamentoDoencaPaciente toEntity(AcompanhamentoDoencaPacienteRequest request);
    AcompanhamentoDoencaPacienteResponse toResponse(AcompanhamentoDoencaPaciente entity);
    void updateFromRequest(AcompanhamentoDoencaPacienteRequest request, @MappingTarget AcompanhamentoDoencaPaciente entity);

}
