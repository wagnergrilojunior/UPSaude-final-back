package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.OrcamentoCompetenciaRequest;
import com.upsaude.api.response.financeiro.OrcamentoCompetenciaResponse;
import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = { CompetenciaFinanceiraMapper.class })
public interface OrcamentoCompetenciaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    OrcamentoCompetencia fromRequest(OrcamentoCompetenciaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    void updateFromRequest(OrcamentoCompetenciaRequest request, @MappingTarget OrcamentoCompetencia entity);

    @Mapping(target = "atualizadoEm", source = "updatedAt")
    @Mapping(target = "saldoDisponivel", expression = "java(entity.calcularSaldoDisponivel())")
    OrcamentoCompetenciaResponse toResponse(OrcamentoCompetencia entity);
}

