package com.upsaude.mapper;

import com.upsaude.api.request.TratamentosProcedimentosRequest;
import com.upsaude.api.response.TratamentosProcedimentosResponse;
import com.upsaude.dto.TratamentosProcedimentosDTO;
import com.upsaude.entity.TratamentosProcedimentos;
import com.upsaude.entity.ProcedimentosOdontologicos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.TratamentosOdontologicos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {ProcedimentosOdontologicosMapper.class, ProfissionaisSaudeMapper.class, TratamentosOdontologicosMapper.class})
public interface TratamentosProcedimentosMapper extends EntityMapper<TratamentosProcedimentos, TratamentosProcedimentosDTO> {

    @Mapping(target = "active", ignore = true)
    TratamentosProcedimentos toEntity(TratamentosProcedimentosDTO dto);

    TratamentosProcedimentosDTO toDTO(TratamentosProcedimentos entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "procedimento", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "tratamento", ignore = true)
    TratamentosProcedimentos fromRequest(TratamentosProcedimentosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "procedimento", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "tratamento", ignore = true)
    void updateFromRequest(TratamentosProcedimentosRequest request, @MappingTarget TratamentosProcedimentos entity);

    TratamentosProcedimentosResponse toResponse(TratamentosProcedimentos entity);
}
