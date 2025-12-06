package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.TratamentoAtualDoencaPacienteRequest;
import com.upsaude.api.response.embeddable.TratamentoAtualDoencaPacienteResponse;
import com.upsaude.entity.embeddable.TratamentoAtualDoencaPaciente;
import com.upsaude.dto.embeddable.TratamentoAtualDoencaPacienteDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface TratamentoAtualDoencaPacienteMapper {
    TratamentoAtualDoencaPaciente toEntity(TratamentoAtualDoencaPacienteRequest request);
    TratamentoAtualDoencaPacienteResponse toResponse(TratamentoAtualDoencaPaciente entity);
    void updateFromRequest(TratamentoAtualDoencaPacienteRequest request, @MappingTarget TratamentoAtualDoencaPaciente entity);

    // Mapeamento entre DTO e Entity
    TratamentoAtualDoencaPaciente toEntity(com.upsaude.dto.embeddable.TratamentoAtualDoencaPacienteDTO dto);
    com.upsaude.dto.embeddable.TratamentoAtualDoencaPacienteDTO toDTO(TratamentoAtualDoencaPaciente entity);
    void updateFromDTO(com.upsaude.dto.embeddable.TratamentoAtualDoencaPacienteDTO dto, @MappingTarget TratamentoAtualDoencaPaciente entity);
}
