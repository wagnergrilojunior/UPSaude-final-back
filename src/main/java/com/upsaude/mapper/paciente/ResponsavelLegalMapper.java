package com.upsaude.mapper.paciente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.paciente.ResponsavelLegalRequest;
import com.upsaude.api.response.paciente.ResponsavelLegalResponse;
import com.upsaude.dto.paciente.ResponsavelLegalDTO;
import com.upsaude.entity.paciente.ResponsavelLegal;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;

@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class, EstabelecimentosMapper.class})
public interface ResponsavelLegalMapper extends EntityMapper<ResponsavelLegal, ResponsavelLegalDTO> {

    @Mapping(target = "active", ignore = true)
    ResponsavelLegal toEntity(ResponsavelLegalDTO dto);

    ResponsavelLegalDTO toDTO(ResponsavelLegal entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    ResponsavelLegal fromRequest(ResponsavelLegalRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(ResponsavelLegalRequest request, @MappingTarget ResponsavelLegal entity);

    @Mapping(target = "paciente.responsavelLegal", ignore = true)
    ResponsavelLegalResponse toResponse(ResponsavelLegal entity);
}
