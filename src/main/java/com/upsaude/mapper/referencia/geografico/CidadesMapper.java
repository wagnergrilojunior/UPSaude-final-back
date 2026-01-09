package com.upsaude.mapper.referencia.geografico;

import com.upsaude.api.request.referencia.geografico.CidadesRequest;
import com.upsaude.api.response.referencia.geografico.CidadesResponse;
import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EstadosMapper.class})
public interface CidadesMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Cidades fromRequest(CidadesRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void updateFromRequest(CidadesRequest request, @MappingTarget Cidades entity);

    CidadesResponse toResponse(Cidades entity);
}
