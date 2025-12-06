package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DosagemAdministracaoMedicamentoRequest;
import com.upsaude.api.response.embeddable.DosagemAdministracaoMedicamentoResponse;
import com.upsaude.entity.embeddable.DosagemAdministracaoMedicamento;
import com.upsaude.dto.embeddable.DosagemAdministracaoMedicamentoDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DosagemAdministracaoMedicamentoMapper {
    DosagemAdministracaoMedicamento toEntity(DosagemAdministracaoMedicamentoRequest request);
    DosagemAdministracaoMedicamentoResponse toResponse(DosagemAdministracaoMedicamento entity);
    void updateFromRequest(DosagemAdministracaoMedicamentoRequest request, @MappingTarget DosagemAdministracaoMedicamento entity);

    // Mapeamento entre DTO e Entity
    DosagemAdministracaoMedicamento toEntity(com.upsaude.dto.embeddable.DosagemAdministracaoMedicamentoDTO dto);
    com.upsaude.dto.embeddable.DosagemAdministracaoMedicamentoDTO toDTO(DosagemAdministracaoMedicamento entity);
    void updateFromDTO(com.upsaude.dto.embeddable.DosagemAdministracaoMedicamentoDTO dto, @MappingTarget DosagemAdministracaoMedicamento entity);
}
