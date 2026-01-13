package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.TituloReceberRequest;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import com.upsaude.api.response.financeiro.TituloReceberResponse;
import com.upsaude.api.response.financeiro.TituloReceberSimplificadoResponse;
import com.upsaude.entity.financeiro.TituloReceber;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = {
        ParteFinanceiraMapper.class,
        ContaContabilMapper.class,
        CentroCustoMapper.class
})
public interface TituloReceberMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "documentoFaturamento", ignore = true)
    @Mapping(target = "pagador", ignore = true)
    @Mapping(target = "contaContabilReceita", ignore = true)
    @Mapping(target = "centroCusto", ignore = true)
    @Mapping(target = "baixas", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    TituloReceber fromRequest(TituloReceberRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "documentoFaturamento", ignore = true)
    @Mapping(target = "pagador", ignore = true)
    @Mapping(target = "contaContabilReceita", ignore = true)
    @Mapping(target = "centroCusto", ignore = true)
    @Mapping(target = "baixas", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    void updateFromRequest(TituloReceberRequest request, @MappingTarget TituloReceber entity);

    TituloReceberResponse toResponse(TituloReceber entity);

    @Named("toSimplifiedResponse")
    TituloReceberSimplificadoResponse toSimplifiedResponse(TituloReceber entity);

    default DocumentoFaturamentoSimplificadoResponse mapDocumentoFaturamento(
            com.upsaude.entity.faturamento.DocumentoFaturamento documento) {
        if (documento == null) {
            return null;
        }
        try {
            return DocumentoFaturamentoSimplificadoResponse.builder()
                    .id(documento.getId())
                    .tipo(documento.getTipo())
                    .numero(documento.getNumero())
                    .serie(documento.getSerie())
                    .status(documento.getStatus())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }
}

