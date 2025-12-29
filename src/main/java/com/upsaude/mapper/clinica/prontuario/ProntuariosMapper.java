package com.upsaude.mapper.clinica.prontuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.prontuario.ProntuariosRequest;
import com.upsaude.api.response.clinica.prontuario.ProntuariosResponse;
import com.upsaude.entity.clinica.prontuario.Prontuarios;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;

@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface ProntuariosMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    Prontuarios fromRequest(ProntuariosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(ProntuariosRequest request, @MappingTarget Prontuarios entity);

    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "toResponseCompleto")
    ProntuariosResponse toResponse(Prontuarios entity);
}
