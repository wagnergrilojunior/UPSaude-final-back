package com.upsaude.mapper.alergia;

import org.mapstruct.Mapper;
import com.upsaude.dto.alergia.CategoriaAgenteAlergiaResponse;
import com.upsaude.entity.alergia.CategoriaAgenteAlergia;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface CategoriaAgenteAlergiaMapper {
    CategoriaAgenteAlergiaResponse toResponse(CategoriaAgenteAlergia entity);
}
