package com.upsaude.mapper.clinica.prontuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.prontuario.DoencaPacienteRequest;
import com.upsaude.api.response.clinica.prontuario.DoencaPacienteResponse;
import com.upsaude.entity.clinica.prontuario.DoencaPaciente;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.referencia.cid.Cid10SubcategoriaMapper;

@Mapper(config = MappingConfig.class, uses = {Cid10SubcategoriaMapper.class})
public interface DoencaPacienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "prontuario", ignore = true)
    @Mapping(target = "diagnostico", ignore = true)
    DoencaPaciente fromRequest(DoencaPacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "prontuario", ignore = true)
    @Mapping(target = "diagnostico", ignore = true)
    void updateFromRequest(DoencaPacienteRequest request, @MappingTarget DoencaPaciente entity);

    DoencaPacienteResponse toResponse(DoencaPaciente entity);
}

