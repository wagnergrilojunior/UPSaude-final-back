package com.upsaude.mapper;

import com.upsaude.api.request.ConsultaPuericulturaRequest;
import com.upsaude.api.response.ConsultaPuericulturaResponse;
import com.upsaude.dto.ConsultaPuericulturaDTO;
import com.upsaude.entity.ConsultaPuericultura;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Puericultura;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {ProfissionaisSaudeMapper.class, PuericulturaMapper.class})
public interface ConsultaPuericulturaMapper extends EntityMapper<ConsultaPuericultura, ConsultaPuericulturaDTO> {

    @Mapping(target = "active", ignore = true)
    ConsultaPuericultura toEntity(ConsultaPuericulturaDTO dto);

    ConsultaPuericulturaDTO toDTO(ConsultaPuericultura entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "puericultura", ignore = true)
    ConsultaPuericultura fromRequest(ConsultaPuericulturaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "puericultura", ignore = true)
    void updateFromRequest(ConsultaPuericulturaRequest request, @MappingTarget ConsultaPuericultura entity);

    ConsultaPuericulturaResponse toResponse(ConsultaPuericultura entity);
}
