package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoDetalheLeitoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoLeito;

@Mapper(componentModel = "spring")
public interface SigtapProcedimentoDetalheLeitoMapper {

    @Mapping(target = "codigoLeito", source = "tipoLeito.codigoOficial")
    @Mapping(target = "nomeLeito", source = "tipoLeito.nome")
    SigtapProcedimentoDetalheLeitoResponse toResponse(SigtapProcedimentoLeito entity);
}
