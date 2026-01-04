package com.upsaude.mapper.paciente;

import com.upsaude.api.request.paciente.DadosClinicosBasicosRequest;
import com.upsaude.api.response.paciente.DadosClinicosBasicosResponse;
import com.upsaude.entity.paciente.DadosClinicosBasicos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosClinicosBasicosMapper {

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente.responsavelLegal.estabelecimento.responsaveis.responsavelTecnico.sigtapOcupacao", ignore = true)
    @Mapping(target = "paciente.responsavelLegal.estabelecimento.responsaveis.responsavelAdministrativo.sigtapOcupacao", ignore = true)
    DadosClinicosBasicos toEntity(DadosClinicosBasicosResponse dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    DadosClinicosBasicos fromRequest(DadosClinicosBasicosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(DadosClinicosBasicosRequest request, @MappingTarget DadosClinicosBasicos entity);

    @Mapping(target = "paciente", ignore = true)
    DadosClinicosBasicosResponse toResponse(DadosClinicosBasicos entity);
}
