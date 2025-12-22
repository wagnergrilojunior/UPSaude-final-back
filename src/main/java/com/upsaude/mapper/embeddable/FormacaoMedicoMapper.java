package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.FormacaoMedicoRequest;
import com.upsaude.api.response.embeddable.FormacaoMedicoResponse;
import com.upsaude.entity.embeddable.FormacaoMedico;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface FormacaoMedicoMapper {
    FormacaoMedico toEntity(FormacaoMedicoRequest request);
    FormacaoMedicoResponse toResponse(FormacaoMedico entity);
    void updateFromRequest(FormacaoMedicoRequest request, @MappingTarget FormacaoMedico entity);

}
