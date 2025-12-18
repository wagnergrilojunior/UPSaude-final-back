package com.upsaude.mapper.odontologia;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.odontologia.TratamentosOdontologicosRequest;
import com.upsaude.api.response.odontologia.TratamentosOdontologicosResponse;
import com.upsaude.dto.odontologia.TratamentosOdontologicosDTO;
import com.upsaude.entity.odontologia.TratamentosOdontologicos;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class, ProfissionaisSaudeMapper.class, EstabelecimentosMapper.class})
public interface TratamentosOdontologicosMapper extends EntityMapper<TratamentosOdontologicos, TratamentosOdontologicosDTO> {

    @Mapping(target = "active", ignore = true)
    TratamentosOdontologicos toEntity(TratamentosOdontologicosDTO dto);

    TratamentosOdontologicosDTO toDTO(TratamentosOdontologicos entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    TratamentosOdontologicos fromRequest(TratamentosOdontologicosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(TratamentosOdontologicosRequest request, @MappingTarget TratamentosOdontologicos entity);

    TratamentosOdontologicosResponse toResponse(TratamentosOdontologicos entity);
}
