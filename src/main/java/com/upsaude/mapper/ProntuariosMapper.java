package com.upsaude.mapper;

import com.upsaude.api.request.ProntuariosRequest;
import com.upsaude.api.response.ProntuariosResponse;
import com.upsaude.dto.ProntuariosDTO;
import com.upsaude.entity.Prontuarios;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class})
public interface ProntuariosMapper extends EntityMapper<Prontuarios, ProntuariosDTO> {

    @Mapping(target = "active", ignore = true)
    Prontuarios toEntity(ProntuariosDTO dto);

    ProntuariosDTO toDTO(Prontuarios entity);

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

    ProntuariosResponse toResponse(Prontuarios entity);
}
