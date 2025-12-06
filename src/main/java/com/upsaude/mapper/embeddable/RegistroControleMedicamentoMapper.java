package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.RegistroControleMedicamentoRequest;
import com.upsaude.api.response.embeddable.RegistroControleMedicamentoResponse;
import com.upsaude.entity.embeddable.RegistroControleMedicamento;
import com.upsaude.dto.embeddable.RegistroControleMedicamentoDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface RegistroControleMedicamentoMapper {
    RegistroControleMedicamento toEntity(RegistroControleMedicamentoRequest request);
    RegistroControleMedicamentoResponse toResponse(RegistroControleMedicamento entity);
    void updateFromRequest(RegistroControleMedicamentoRequest request, @MappingTarget RegistroControleMedicamento entity);

    // Mapeamento entre DTO e Entity
    RegistroControleMedicamento toEntity(com.upsaude.dto.embeddable.RegistroControleMedicamentoDTO dto);
    com.upsaude.dto.embeddable.RegistroControleMedicamentoDTO toDTO(RegistroControleMedicamento entity);
    void updateFromDTO(com.upsaude.dto.embeddable.RegistroControleMedicamentoDTO dto, @MappingTarget RegistroControleMedicamento entity);
}
