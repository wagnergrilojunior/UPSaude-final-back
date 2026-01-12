package com.upsaude.mapper.alergia;

import org.mapstruct.Mapper;
import com.upsaude.dto.alergia.AlergenoResponse;
import com.upsaude.entity.alergia.Alergeno;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface AlergenoMapper {
    AlergenoResponse toResponse(Alergeno entity);
}
