package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ClassificacaoEspecialidadeMedicaRequest;
import com.upsaude.api.response.embeddable.ClassificacaoEspecialidadeMedicaResponse;
import com.upsaude.entity.embeddable.ClassificacaoEspecialidadeMedica;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ClassificacaoEspecialidadeMedicaMapper {
    ClassificacaoEspecialidadeMedica toEntity(ClassificacaoEspecialidadeMedicaRequest request);
    ClassificacaoEspecialidadeMedicaResponse toResponse(ClassificacaoEspecialidadeMedica entity);
    void updateFromRequest(ClassificacaoEspecialidadeMedicaRequest request, @MappingTarget ClassificacaoEspecialidadeMedica entity);

}
