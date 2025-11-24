package com.upsaude.mapper;

import com.upsaude.api.request.DispensacoesMedicamentosRequest;
import com.upsaude.api.response.DispensacoesMedicamentosResponse;
import com.upsaude.dto.DispensacoesMedicamentosDTO;
import com.upsaude.entity.DispensacoesMedicamentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface DispensacoesMedicamentosMapper extends EntityMapper<DispensacoesMedicamentos, DispensacoesMedicamentosDTO> {

    @Mapping(target = "tenant", ignore = true)
    DispensacoesMedicamentos toEntity(DispensacoesMedicamentosDTO dto);

    DispensacoesMedicamentosDTO toDTO(DispensacoesMedicamentos entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    DispensacoesMedicamentos fromRequest(DispensacoesMedicamentosRequest request);

    DispensacoesMedicamentosResponse toResponse(DispensacoesMedicamentos entity);
}

