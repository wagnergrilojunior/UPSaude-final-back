package com.upsaude.mapper;

import com.upsaude.api.request.TratamentosOdontologicosRequest;
import com.upsaude.api.response.TratamentosOdontologicosResponse;
import com.upsaude.dto.TratamentosOdontologicosDTO;
import com.upsaude.entity.TratamentosOdontologicos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface TratamentosOdontologicosMapper extends EntityMapper<TratamentosOdontologicos, TratamentosOdontologicosDTO> {

    @Mapping(target = "tenant", ignore = true)
    TratamentosOdontologicos toEntity(TratamentosOdontologicosDTO dto);

    TratamentosOdontologicosDTO toDTO(TratamentosOdontologicos entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    TratamentosOdontologicos fromRequest(TratamentosOdontologicosRequest request);

    TratamentosOdontologicosResponse toResponse(TratamentosOdontologicos entity);
}

