package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosPessoaisMedicoRequest;
import com.upsaude.api.response.embeddable.DadosPessoaisMedicoResponse;
import com.upsaude.entity.embeddable.DadosPessoaisMedico;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosPessoaisMedicoMapper {
    DadosPessoaisMedico toEntity(DadosPessoaisMedicoRequest request);
    DadosPessoaisMedicoResponse toResponse(DadosPessoaisMedico entity);
    void updateFromRequest(DadosPessoaisMedicoRequest request, @MappingTarget DadosPessoaisMedico entity);

}
