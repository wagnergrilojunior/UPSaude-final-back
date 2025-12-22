package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.IdentificacaoMedicamentoRequest;
import com.upsaude.api.response.embeddable.IdentificacaoMedicamentoResponse;
import com.upsaude.entity.embeddable.IdentificacaoMedicamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface IdentificacaoMedicamentoMapper {
    IdentificacaoMedicamento toEntity(IdentificacaoMedicamentoRequest request);
    IdentificacaoMedicamentoResponse toResponse(IdentificacaoMedicamento entity);
    void updateFromRequest(IdentificacaoMedicamentoRequest request, @MappingTarget IdentificacaoMedicamento entity);

}
