package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ContatoMedicoRequest;
import com.upsaude.api.response.embeddable.ContatoMedicoResponse;
import com.upsaude.entity.embeddable.ContatoMedico;
import com.upsaude.dto.embeddable.ContatoMedicoDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ContatoMedicoMapper {
    ContatoMedico toEntity(ContatoMedicoRequest request);
    ContatoMedicoResponse toResponse(ContatoMedico entity);
    void updateFromRequest(ContatoMedicoRequest request, @MappingTarget ContatoMedico entity);

    // Mapeamento entre DTO e Entity
    ContatoMedico toEntity(com.upsaude.dto.embeddable.ContatoMedicoDTO dto);
    com.upsaude.dto.embeddable.ContatoMedicoDTO toDTO(ContatoMedico entity);
    void updateFromDTO(com.upsaude.dto.embeddable.ContatoMedicoDTO dto, @MappingTarget ContatoMedico entity);
}
