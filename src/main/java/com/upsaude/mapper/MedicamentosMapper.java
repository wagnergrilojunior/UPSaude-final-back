package com.upsaude.mapper;

import com.upsaude.api.request.MedicamentosRequest;
import com.upsaude.api.response.MedicamentosResponse;
import com.upsaude.dto.MedicamentosDTO;
import com.upsaude.entity.Medicamentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface MedicamentosMapper extends EntityMapper<Medicamentos, MedicamentosDTO> {

    @Mapping(target = "tenant", ignore = true)
    Medicamentos toEntity(MedicamentosDTO dto);

    MedicamentosDTO toDTO(Medicamentos entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    Medicamentos fromRequest(MedicamentosRequest request);

    MedicamentosResponse toResponse(Medicamentos entity);
}

