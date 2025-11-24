package com.upsaude.mapper;

import com.upsaude.api.request.TratamentosProcedimentosRequest;
import com.upsaude.api.response.TratamentosProcedimentosResponse;
import com.upsaude.dto.TratamentosProcedimentosDTO;
import com.upsaude.entity.TratamentosProcedimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface TratamentosProcedimentosMapper extends EntityMapper<TratamentosProcedimentos, TratamentosProcedimentosDTO> {

    @Mapping(target = "tenant", ignore = true)
    TratamentosProcedimentos toEntity(TratamentosProcedimentosDTO dto);

    TratamentosProcedimentosDTO toDTO(TratamentosProcedimentos entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    TratamentosProcedimentos fromRequest(TratamentosProcedimentosRequest request);

    TratamentosProcedimentosResponse toResponse(TratamentosProcedimentos entity);
}

