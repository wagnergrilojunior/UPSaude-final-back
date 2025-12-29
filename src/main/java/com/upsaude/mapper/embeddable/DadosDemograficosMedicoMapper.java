package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosDemograficosMedicoRequest;
import com.upsaude.api.response.embeddable.DadosDemograficosMedicoResponse;
import com.upsaude.entity.embeddable.DadosDemograficosMedico;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosDemograficosMedicoMapper {
    DadosDemograficosMedico toEntity(DadosDemograficosMedicoRequest request);
    DadosDemograficosMedicoResponse toResponse(DadosDemograficosMedico entity);
    void updateFromRequest(DadosDemograficosMedicoRequest request, @MappingTarget DadosDemograficosMedico entity);
}

