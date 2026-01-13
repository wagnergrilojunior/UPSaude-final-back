package com.upsaude.mapper.clinica.atendimento;

import com.upsaude.api.request.clinica.atendimento.SinalVitalRequest;
import com.upsaude.api.response.clinica.atendimento.SinalVitalResponse;
import com.upsaude.entity.clinica.atendimento.SinalVital;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface SinalVitalMapper {

    SinalVitalResponse toResponse(SinalVital entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "active", ignore = true)
    SinalVital toEntity(SinalVitalRequest request);

    void updateEntityFromRequest(SinalVitalRequest request, @MappingTarget SinalVital entity);
}
