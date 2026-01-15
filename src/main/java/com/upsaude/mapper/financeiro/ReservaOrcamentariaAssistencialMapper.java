package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.ReservaOrcamentariaAssistencialRequest;
import com.upsaude.api.response.financeiro.ReservaOrcamentariaAssistencialResponse;
import com.upsaude.entity.financeiro.ReservaOrcamentariaAssistencial;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = { CompetenciaFinanceiraMapper.class, GuiaAtendimentoAmbulatorialMapper.class })
public interface ReservaOrcamentariaAssistencialMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "documentoFaturamento", ignore = true)
    @Mapping(target = "guiaAmbulatorial", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    ReservaOrcamentariaAssistencial fromRequest(ReservaOrcamentariaAssistencialRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "documentoFaturamento", ignore = true)
    @Mapping(target = "guiaAmbulatorial", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    void updateFromRequest(ReservaOrcamentariaAssistencialRequest request, @MappingTarget ReservaOrcamentariaAssistencial entity);

    @Mapping(target = "documentoFaturamento", source = "documentoFaturamento", qualifiedByName = "mapDocumentoFaturamentoParaGuia")
    ReservaOrcamentariaAssistencialResponse toResponse(ReservaOrcamentariaAssistencial entity);
}

