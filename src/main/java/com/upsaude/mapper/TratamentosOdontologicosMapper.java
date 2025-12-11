package com.upsaude.mapper;

import com.upsaude.api.request.TratamentosOdontologicosRequest;
import com.upsaude.api.response.TratamentosOdontologicosResponse;
import com.upsaude.dto.TratamentosOdontologicosDTO;
import com.upsaude.entity.TratamentosOdontologicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {PacienteMapper.class, ProfissionaisSaudeMapper.class})
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
    @Mapping(target = "procedimentos", ignore = true)
    TratamentosOdontologicos fromRequest(TratamentosOdontologicosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "procedimentos", ignore = true)
    void updateFromRequest(TratamentosOdontologicosRequest request, @MappingTarget TratamentosOdontologicos entity);

    TratamentosOdontologicosResponse toResponse(TratamentosOdontologicos entity);
}
