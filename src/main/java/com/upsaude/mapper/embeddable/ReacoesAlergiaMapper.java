package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ReacoesAlergiaRequest;
import com.upsaude.api.response.embeddable.ReacoesAlergiaResponse;
import com.upsaude.dto.embeddable.ReacoesAlergiaDTO;
import com.upsaude.entity.embeddable.ReacoesAlergia;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ReacoesAlergiaMapper {
    ReacoesAlergia toEntity(ReacoesAlergiaRequest request);
    ReacoesAlergiaResponse toResponse(ReacoesAlergia entity);
    void updateFromRequest(ReacoesAlergiaRequest request, @MappingTarget ReacoesAlergia entity);
    
    // Mapeamento entre DTO e Entity
    ReacoesAlergia toEntity(ReacoesAlergiaDTO dto);
    ReacoesAlergiaDTO toDTO(ReacoesAlergia entity);
    void updateFromDTO(ReacoesAlergiaDTO dto, @MappingTarget ReacoesAlergia entity);
}
