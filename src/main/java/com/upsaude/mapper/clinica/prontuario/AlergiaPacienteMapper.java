package com.upsaude.mapper.clinica.prontuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.prontuario.AlergiaPacienteRequest;
import com.upsaude.api.response.clinica.prontuario.AlergiaPacienteResponse;
import com.upsaude.entity.clinica.prontuario.AlergiaPaciente;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.referencia.cid.Cid10SubcategoriaMapper;

@Mapper(config = MappingConfig.class, uses = {Cid10SubcategoriaMapper.class})
public interface AlergiaPacienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "prontuario", ignore = true)
    @Mapping(target = "diagnosticoRelacionado", ignore = true)
    AlergiaPaciente fromRequest(AlergiaPacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "prontuario", ignore = true)
    @Mapping(target = "diagnosticoRelacionado", ignore = true)
    void updateFromRequest(AlergiaPacienteRequest request, @MappingTarget AlergiaPaciente entity);

    AlergiaPacienteResponse toResponse(AlergiaPaciente entity);
}

