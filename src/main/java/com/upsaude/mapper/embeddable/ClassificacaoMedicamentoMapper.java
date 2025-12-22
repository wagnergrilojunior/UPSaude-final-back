package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ClassificacaoMedicamentoRequest;
import com.upsaude.api.response.embeddable.ClassificacaoMedicamentoResponse;
import com.upsaude.entity.embeddable.ClassificacaoMedicamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ClassificacaoMedicamentoMapper {
    ClassificacaoMedicamento toEntity(ClassificacaoMedicamentoRequest request);
    ClassificacaoMedicamentoResponse toResponse(ClassificacaoMedicamento entity);
    void updateFromRequest(ClassificacaoMedicamentoRequest request, @MappingTarget ClassificacaoMedicamento entity);

}
