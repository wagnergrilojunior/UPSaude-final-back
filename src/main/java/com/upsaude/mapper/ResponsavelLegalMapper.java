package com.upsaude.mapper;

import com.upsaude.api.request.ResponsavelLegalRequest;
import com.upsaude.api.response.ResponsavelLegalResponse;
import com.upsaude.dto.ResponsavelLegalDTO;
import com.upsaude.entity.ResponsavelLegal;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
