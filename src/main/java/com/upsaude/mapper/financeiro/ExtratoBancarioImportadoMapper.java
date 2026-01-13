package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.ExtratoBancarioImportadoRequest;
import com.upsaude.api.response.financeiro.ExtratoBancarioImportadoResponse;
import com.upsaude.api.response.financeiro.ExtratoBancarioImportadoSimplificadoResponse;
import com.upsaude.entity.financeiro.ExtratoBancarioImportado;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = { ContaFinanceiraMapper.class })
public interface ExtratoBancarioImportadoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "contaFinanceira", ignore = true)
    ExtratoBancarioImportado fromRequest(ExtratoBancarioImportadoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "contaFinanceira", ignore = true)
    void updateFromRequest(ExtratoBancarioImportadoRequest request, @MappingTarget ExtratoBancarioImportado entity);

    ExtratoBancarioImportadoResponse toResponse(ExtratoBancarioImportado entity);

    @Named("toSimplifiedResponse")
    ExtratoBancarioImportadoSimplificadoResponse toSimplifiedResponse(ExtratoBancarioImportado entity);
}

