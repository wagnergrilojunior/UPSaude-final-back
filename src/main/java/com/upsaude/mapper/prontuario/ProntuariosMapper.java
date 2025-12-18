package com.upsaude.mapper.prontuario;

import com.upsaude.api.request.prontuario.ProntuariosRequest;
import com.upsaude.api.response.prontuario.ProntuariosResponse;
import com.upsaude.dto.ProntuariosDTO;
import com.upsaude.entity.prontuario.Prontuarios;
import com.upsaude.entity.paciente.Paciente;
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
