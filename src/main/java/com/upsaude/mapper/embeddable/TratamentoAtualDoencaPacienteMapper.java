package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.TratamentoAtualDoencaPacienteRequest;
import com.upsaude.api.response.embeddable.TratamentoAtualDoencaPacienteResponse;
import com.upsaude.entity.embeddable.TratamentoAtualDoencaPaciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface TratamentoAtualDoencaPacienteMapper {
    TratamentoAtualDoencaPaciente toEntity(TratamentoAtualDoencaPacienteRequest request);
    TratamentoAtualDoencaPacienteResponse toResponse(TratamentoAtualDoencaPaciente entity);
    void updateFromRequest(TratamentoAtualDoencaPacienteRequest request, @MappingTarget TratamentoAtualDoencaPaciente entity);

}
