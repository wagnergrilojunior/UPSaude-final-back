package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.RegistroProfissionalMedicoRequest;
import com.upsaude.api.response.embeddable.RegistroProfissionalMedicoResponse;
import com.upsaude.entity.embeddable.RegistroProfissionalMedico;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface RegistroProfissionalMedicoMapper {
    RegistroProfissionalMedico toEntity(RegistroProfissionalMedicoRequest request);
    RegistroProfissionalMedicoResponse toResponse(RegistroProfissionalMedico entity);
    void updateFromRequest(RegistroProfissionalMedicoRequest request, @MappingTarget RegistroProfissionalMedico entity);

}
