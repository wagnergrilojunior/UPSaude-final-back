package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ReacoesAdversasVacinaRequest;
import com.upsaude.api.response.embeddable.ReacoesAdversasVacinaResponse;
import com.upsaude.entity.embeddable.ReacoesAdversasVacina;
import com.upsaude.dto.embeddable.ReacoesAdversasVacinaDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ReacoesAdversasVacinaMapper {
    ReacoesAdversasVacina toEntity(ReacoesAdversasVacinaRequest request);
    ReacoesAdversasVacinaResponse toResponse(ReacoesAdversasVacina entity);
    void updateFromRequest(ReacoesAdversasVacinaRequest request, @MappingTarget ReacoesAdversasVacina entity);

    // Mapeamento entre DTO e Entity
    ReacoesAdversasVacina toEntity(com.upsaude.dto.embeddable.ReacoesAdversasVacinaDTO dto);
    com.upsaude.dto.embeddable.ReacoesAdversasVacinaDTO toDTO(ReacoesAdversasVacina entity);
    void updateFromDTO(com.upsaude.dto.embeddable.ReacoesAdversasVacinaDTO dto, @MappingTarget ReacoesAdversasVacina entity);
}
