package com.upsaude.mapper.clinica.atendimento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.atendimento.ConsultaPuericulturaRequest;
import com.upsaude.api.response.clinica.atendimento.ConsultaPuericulturaResponse;
import com.upsaude.dto.clinica.atendimento.ConsultaPuericulturaDTO;
import com.upsaude.entity.clinica.atendimento.ConsultaPuericultura;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import com.upsaude.mapper.saude_publica.puericultura.PuericulturaMapper;

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
