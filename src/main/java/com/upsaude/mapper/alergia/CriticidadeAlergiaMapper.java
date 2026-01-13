package com.upsaude.mapper.alergia;

import org.mapstruct.Mapper;
import com.upsaude.dto.alergia.CriticidadeAlergiaResponse;
import com.upsaude.entity.alergia.CriticidadeAlergia;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface CriticidadeAlergiaMapper {
    CriticidadeAlergiaResponse toResponse(CriticidadeAlergia entity);
}
