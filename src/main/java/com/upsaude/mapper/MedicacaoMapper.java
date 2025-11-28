package com.upsaude.mapper;

import com.upsaude.api.request.MedicacaoRequest;
import com.upsaude.api.response.MedicacaoResponse;
import com.upsaude.dto.MedicacaoDTO;
import com.upsaude.entity.Medicacao;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para conversão entre entidades, DTOs, Requests e Responses de Medicações.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface MedicacaoMapper extends EntityMapper<Medicacao, MedicacaoDTO> {

    @Mapping(target = "tenant", ignore = true)
    Medicacao toEntity(MedicacaoDTO dto);

    MedicacaoDTO toDTO(Medicacao entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    Medicacao fromRequest(MedicacaoRequest request);

    MedicacaoResponse toResponse(Medicacao entity);
}

