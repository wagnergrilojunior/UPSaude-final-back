package com.upsaude.mapper.alergia;

import org.mapstruct.Mapper;
import com.upsaude.dto.alergia.ReacaoAdversaResponse;
import com.upsaude.entity.alergia.ReacaoAdversaCatalogo;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface ReacaoAdversaCatalogoMapper {
    ReacaoAdversaResponse toResponse(ReacaoAdversaCatalogo entity);
}
