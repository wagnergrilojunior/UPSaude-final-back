package com.upsaude.mapper.sistema.integracao;

import com.upsaude.api.request.sistema.integracao.IntegracaoGovRequest;
import com.upsaude.api.response.sistema.integracao.IntegracaoGovResponse;
import com.upsaude.entity.sistema.integracao.IntegracaoGov;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface IntegracaoGovMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    IntegracaoGov fromRequest(IntegracaoGovRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(IntegracaoGovRequest request, @MappingTarget IntegracaoGov entity);

    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "toResponseCompleto")
    IntegracaoGovResponse toResponse(IntegracaoGov entity);
}
