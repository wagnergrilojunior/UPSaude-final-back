package com.upsaude.mapper.clinica.medicacao;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.medicacao.MedicacoesContinuasRequest;
import com.upsaude.api.response.clinica.medicacao.MedicacoesContinuasResponse;
import com.upsaude.dto.clinica.medicacao.MedicacoesContinuasDTO;
import com.upsaude.entity.clinica.medicacao.MedicacoesContinuas;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface MedicacoesContinuasMapper extends EntityMapper<MedicacoesContinuas, MedicacoesContinuasDTO> {

    @Mapping(target = "active", ignore = true)
    MedicacoesContinuas toEntity(MedicacoesContinuasDTO dto);

    MedicacoesContinuasDTO toDTO(MedicacoesContinuas entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    MedicacoesContinuas fromRequest(MedicacoesContinuasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(MedicacoesContinuasRequest request, @MappingTarget MedicacoesContinuas entity);

    MedicacoesContinuasResponse toResponse(MedicacoesContinuas entity);
}
