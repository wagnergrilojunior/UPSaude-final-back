package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ClassificacaoAlergiaRequest;
import com.upsaude.api.response.embeddable.ClassificacaoAlergiaResponse;
import com.upsaude.dto.embeddable.ClassificacaoAlergiaDTO;
import com.upsaude.entity.embeddable.ClassificacaoAlergia;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ClassificacaoAlergiaMapper {
    ClassificacaoAlergia toEntity(ClassificacaoAlergiaRequest request);
    ClassificacaoAlergiaResponse toResponse(ClassificacaoAlergia entity);
    void updateFromRequest(ClassificacaoAlergiaRequest request, @MappingTarget ClassificacaoAlergia entity);
    
    // Mapeamento entre DTO e Entity
    ClassificacaoAlergia toEntity(ClassificacaoAlergiaDTO dto);
    ClassificacaoAlergiaDTO toDTO(ClassificacaoAlergia entity);
    void updateFromDTO(ClassificacaoAlergiaDTO dto, @MappingTarget ClassificacaoAlergia entity);
}
