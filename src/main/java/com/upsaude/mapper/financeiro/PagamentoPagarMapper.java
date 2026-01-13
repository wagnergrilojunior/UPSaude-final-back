package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.PagamentoPagarRequest;
import com.upsaude.api.response.financeiro.PagamentoPagarResponse;
import com.upsaude.api.response.financeiro.PagamentoPagarSimplificadaResponse;
import com.upsaude.api.response.financeiro.TituloPagarSimplificadoResponse;
import com.upsaude.entity.financeiro.PagamentoPagar;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = { ContaFinanceiraMapper.class, MovimentacaoContaMapper.class })
public interface PagamentoPagarMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "tituloPagar", ignore = true)
    @Mapping(target = "contaFinanceira", ignore = true)
    @Mapping(target = "movimentacaoConta", ignore = true)
    @Mapping(target = "lancamentoFinanceiro", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    PagamentoPagar fromRequest(PagamentoPagarRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "tituloPagar", ignore = true)
    @Mapping(target = "contaFinanceira", ignore = true)
    @Mapping(target = "movimentacaoConta", ignore = true)
    @Mapping(target = "lancamentoFinanceiro", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    void updateFromRequest(PagamentoPagarRequest request, @MappingTarget PagamentoPagar entity);

    PagamentoPagarResponse toResponse(PagamentoPagar entity);

    @Named("toSimplifiedResponse")
    PagamentoPagarSimplificadaResponse toSimplifiedResponse(PagamentoPagar entity);

    default TituloPagarSimplificadoResponse mapTituloPagar(com.upsaude.entity.financeiro.TituloPagar titulo) {
        if (titulo == null) {
            return null;
        }
        try {
            return TituloPagarSimplificadoResponse.builder()
                    .id(titulo.getId())
                    .numeroDocumento(titulo.getNumeroDocumento())
                    .valorAberto(titulo.getValorAberto())
                    .dataVencimento(titulo.getDataVencimento())
                    .status(titulo.getStatus())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }
}

