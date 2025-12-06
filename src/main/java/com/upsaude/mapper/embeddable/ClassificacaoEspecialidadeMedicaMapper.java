package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ClassificacaoEspecialidadeMedicaRequest;
import com.upsaude.api.response.embeddable.ClassificacaoEspecialidadeMedicaResponse;
import com.upsaude.entity.embeddable.ClassificacaoEspecialidadeMedica;
import com.upsaude.dto.embeddable.ClassificacaoEspecialidadeMedicaDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ClassificacaoEspecialidadeMedicaMapper {
    ClassificacaoEspecialidadeMedica toEntity(ClassificacaoEspecialidadeMedicaRequest request);
    ClassificacaoEspecialidadeMedicaResponse toResponse(ClassificacaoEspecialidadeMedica entity);
    void updateFromRequest(ClassificacaoEspecialidadeMedicaRequest request, @MappingTarget ClassificacaoEspecialidadeMedica entity);

    // Mapeamento entre DTO e Entity
    ClassificacaoEspecialidadeMedica toEntity(com.upsaude.dto.embeddable.ClassificacaoEspecialidadeMedicaDTO dto);
    com.upsaude.dto.embeddable.ClassificacaoEspecialidadeMedicaDTO toDTO(ClassificacaoEspecialidadeMedica entity);
    void updateFromDTO(com.upsaude.dto.embeddable.ClassificacaoEspecialidadeMedicaDTO dto, @MappingTarget ClassificacaoEspecialidadeMedica entity);
}
