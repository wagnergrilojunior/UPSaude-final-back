package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.MovimentacaoContaRequest;
import com.upsaude.api.response.financeiro.BaixaReceberSimplificadaResponse;
import com.upsaude.api.response.financeiro.LancamentoFinanceiroSimplificadoResponse;
import com.upsaude.api.response.financeiro.MovimentacaoContaResponse;
import com.upsaude.api.response.financeiro.MovimentacaoContaSimplificadaResponse;
import com.upsaude.api.response.financeiro.PagamentoPagarSimplificadaResponse;
import com.upsaude.api.response.financeiro.TransferenciaEntreContasSimplificadaResponse;
import com.upsaude.entity.financeiro.MovimentacaoConta;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = { ContaFinanceiraMapper.class, TransferenciaEntreContasMapper.class })
public interface MovimentacaoContaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "contaFinanceira", ignore = true)
    @Mapping(target = "baixaReceber", ignore = true)
    @Mapping(target = "pagamentoPagar", ignore = true)
    @Mapping(target = "transferencia", ignore = true)
    @Mapping(target = "lancamentoFinanceiro", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    MovimentacaoConta fromRequest(MovimentacaoContaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "contaFinanceira", ignore = true)
    @Mapping(target = "baixaReceber", ignore = true)
    @Mapping(target = "pagamentoPagar", ignore = true)
    @Mapping(target = "transferencia", ignore = true)
    @Mapping(target = "lancamentoFinanceiro", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    void updateFromRequest(MovimentacaoContaRequest request, @MappingTarget MovimentacaoConta entity);

    MovimentacaoContaResponse toResponse(MovimentacaoConta entity);

    @Named("toSimplifiedResponse")
    MovimentacaoContaSimplificadaResponse toSimplifiedResponse(MovimentacaoConta entity);

    default BaixaReceberSimplificadaResponse mapBaixaReceber(com.upsaude.entity.financeiro.BaixaReceber baixa) {
        if (baixa == null) {
            return null;
        }
        try {
            return BaixaReceberSimplificadaResponse.builder()
                    .id(baixa.getId())
                    .valorPago(baixa.getValorPago())
                    .dataPagamento(baixa.getDataPagamento())
                    .status(baixa.getStatus())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    default PagamentoPagarSimplificadaResponse mapPagamentoPagar(com.upsaude.entity.financeiro.PagamentoPagar pagamento) {
        if (pagamento == null) {
            return null;
        }
        try {
            return PagamentoPagarSimplificadaResponse.builder()
                    .id(pagamento.getId())
                    .valorPago(pagamento.getValorPago())
                    .dataPagamento(pagamento.getDataPagamento())
                    .status(pagamento.getStatus())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    default TransferenciaEntreContasSimplificadaResponse mapTransferenciaEntreContas(
            com.upsaude.entity.financeiro.TransferenciaEntreContas transferencia) {
        if (transferencia == null) {
            return null;
        }
        try {
            return TransferenciaEntreContasSimplificadaResponse.builder()
                    .id(transferencia.getId())
                    .valor(transferencia.getValor())
                    .data(transferencia.getData())
                    .status(transferencia.getStatus())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    default LancamentoFinanceiroSimplificadoResponse mapLancamentoFinanceiro(
            com.upsaude.entity.financeiro.LancamentoFinanceiro lancamento) {
        if (lancamento == null) {
            return null;
        }
        try {
            return LancamentoFinanceiroSimplificadoResponse.builder()
                    .id(lancamento.getId())
                    .status(lancamento.getStatus())
                    .dataEvento(lancamento.getDataEvento())
                    .descricao(lancamento.getDescricao())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }
}

