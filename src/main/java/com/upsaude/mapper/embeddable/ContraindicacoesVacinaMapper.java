package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ContraindicacoesVacinaRequest;
import com.upsaude.api.response.embeddable.ContraindicacoesVacinaResponse;
import com.upsaude.entity.embeddable.ContraindicacoesVacina;
import com.upsaude.dto.embeddable.ContraindicacoesVacinaDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ContraindicacoesVacinaMapper {
    ContraindicacoesVacina toEntity(ContraindicacoesVacinaRequest request);
    ContraindicacoesVacinaResponse toResponse(ContraindicacoesVacina entity);
    void updateFromRequest(ContraindicacoesVacinaRequest request, @MappingTarget ContraindicacoesVacina entity);

    // Mapeamento entre DTO e Entity
    ContraindicacoesVacina toEntity(com.upsaude.dto.embeddable.ContraindicacoesVacinaDTO dto);
    com.upsaude.dto.embeddable.ContraindicacoesVacinaDTO toDTO(ContraindicacoesVacina entity);
    void updateFromDTO(com.upsaude.dto.embeddable.ContraindicacoesVacinaDTO dto, @MappingTarget ContraindicacoesVacina entity);
}
