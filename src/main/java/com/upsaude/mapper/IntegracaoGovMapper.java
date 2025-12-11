package com.upsaude.mapper;

import com.upsaude.api.request.IntegracaoGovRequest;
import com.upsaude.api.response.IntegracaoGovResponse;
import com.upsaude.dto.IntegracaoGovDTO;
import com.upsaude.entity.IntegracaoGov;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface IntegracaoGovMapper extends EntityMapper<IntegracaoGov, IntegracaoGovDTO> {

    @Mapping(target = "active", ignore = true)
    IntegracaoGov toEntity(IntegracaoGovDTO dto);

    IntegracaoGovDTO toDTO(IntegracaoGov entity);

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

    IntegracaoGovResponse toResponse(IntegracaoGov entity);
}
