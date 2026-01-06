package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosPessoaisBasicosMedicoRequest;
import com.upsaude.api.response.embeddable.DadosPessoaisBasicosMedicoResponse;
import com.upsaude.entity.embeddable.DadosPessoaisBasicosMedico;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosPessoaisBasicosMedicoMapper {
    DadosPessoaisBasicosMedico toEntity(DadosPessoaisBasicosMedicoRequest request);
    DadosPessoaisBasicosMedicoResponse toResponse(DadosPessoaisBasicosMedico entity);
    void updateFromRequest(DadosPessoaisBasicosMedicoRequest request, @MappingTarget DadosPessoaisBasicosMedico entity);
}
