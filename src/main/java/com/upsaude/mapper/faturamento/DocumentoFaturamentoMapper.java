package com.upsaude.mapper.faturamento;

import com.upsaude.api.request.faturamento.DocumentoFaturamentoRequest;
import com.upsaude.api.response.convenio.ConvenioSimplificadoResponse;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoResponse;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.financeiro.CompetenciaFinanceiraMapper;
import com.upsaude.mapper.financeiro.GuiaAtendimentoAmbulatorialMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = { CompetenciaFinanceiraMapper.class, GuiaAtendimentoAmbulatorialMapper.class, DocumentoFaturamentoItemMapper.class })
public interface DocumentoFaturamentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "guiaAmbulatorial", ignore = true)
    @Mapping(target = "emitidoEm", ignore = true)
    @Mapping(target = "canceladoEm", ignore = true)
    @Mapping(target = "canceladoPor", ignore = true)
    @Mapping(target = "itens", ignore = true)
    DocumentoFaturamento fromRequest(DocumentoFaturamentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "guiaAmbulatorial", ignore = true)
    @Mapping(target = "emitidoEm", ignore = true)
    @Mapping(target = "canceladoEm", ignore = true)
    @Mapping(target = "canceladoPor", ignore = true)
    @Mapping(target = "itens", ignore = true)
    void updateFromRequest(DocumentoFaturamentoRequest request, @MappingTarget DocumentoFaturamento entity);

    @Named("toSimplifiedResponse")
    DocumentoFaturamentoSimplificadoResponse toSimplifiedResponse(DocumentoFaturamento entity);

    DocumentoFaturamentoResponse toResponse(DocumentoFaturamento entity);

    default ConvenioSimplificadoResponse mapConvenio(com.upsaude.entity.convenio.Convenio convenio) {
        if (convenio == null) return null;
        try {
            return ConvenioSimplificadoResponse.builder()
                    .id(convenio.getId())
                    .nome(convenio.getNome())
                    .estabelecimentoId(convenio.getEstabelecimento() != null ? convenio.getEstabelecimento().getId() : null)
                    .tenantId(convenio.getTenant() != null ? convenio.getTenant().getId() : null)
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }
}

