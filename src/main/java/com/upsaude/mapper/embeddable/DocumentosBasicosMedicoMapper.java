package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DocumentosBasicosMedicoRequest;
import com.upsaude.api.response.embeddable.DocumentosBasicosMedicoResponse;
import com.upsaude.entity.embeddable.DocumentosBasicosMedico;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DocumentosBasicosMedicoMapper {
    DocumentosBasicosMedico toEntity(DocumentosBasicosMedicoRequest request);
    DocumentosBasicosMedicoResponse toResponse(DocumentosBasicosMedico entity);
    void updateFromRequest(DocumentosBasicosMedicoRequest request, @MappingTarget DocumentosBasicosMedico entity);
}

