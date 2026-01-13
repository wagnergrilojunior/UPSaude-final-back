package com.upsaude.service.api.support.financeiro.lancamento;

import com.upsaude.api.request.financeiro.LancamentoFinanceiroItemRequest;
import com.upsaude.api.request.financeiro.LancamentoFinanceiroRequest;
import com.upsaude.entity.financeiro.CentroCusto;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.entity.financeiro.ContaContabil;
import com.upsaude.entity.financeiro.LancamentoFinanceiro;
import com.upsaude.entity.financeiro.LancamentoFinanceiroItem;
import com.upsaude.entity.financeiro.TituloPagar;
import com.upsaude.entity.financeiro.TituloReceber;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.mapper.financeiro.LancamentoFinanceiroItemMapper;
import com.upsaude.repository.faturamento.DocumentoFaturamentoRepository;
import com.upsaude.repository.financeiro.CentroCustoRepository;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import com.upsaude.repository.financeiro.ConciliacaoBancariaRepository;
import com.upsaude.repository.financeiro.ContaContabilRepository;
import com.upsaude.repository.financeiro.TituloPagarRepository;
import com.upsaude.repository.financeiro.TituloReceberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LancamentoFinanceiroRelacionamentosHandler {

    private final CompetenciaFinanceiraRepository competenciaFinanceiraRepository;
    private final DocumentoFaturamentoRepository documentoFaturamentoRepository;
    private final TituloReceberRepository tituloReceberRepository;
    private final TituloPagarRepository tituloPagarRepository;
    private final ConciliacaoBancariaRepository conciliacaoBancariaRepository;

    private final ContaContabilRepository contaContabilRepository;
    private final CentroCustoRepository centroCustoRepository;
    private final LancamentoFinanceiroItemMapper itemMapper;

    public void resolver(LancamentoFinanceiro entity, LancamentoFinanceiroRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        CompetenciaFinanceira competencia = competenciaFinanceiraRepository.findById(request.getCompetencia())
                .orElseThrow(() -> new BadRequestException("Competência financeira não encontrada com ID: " + request.getCompetencia()));
        entity.setCompetencia(competencia);

        if (request.getDocumentoFaturamento() != null) {
            DocumentoFaturamento doc = documentoFaturamentoRepository.findByIdAndTenant(request.getDocumentoFaturamento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Documento de faturamento não encontrado com ID: " + request.getDocumentoFaturamento()));
            entity.setDocumentoFaturamento(doc);
        } else {
            entity.setDocumentoFaturamento(null);
        }

        if (request.getTituloReceber() != null) {
            TituloReceber tr = tituloReceberRepository.findByIdAndTenant(request.getTituloReceber(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Título a receber não encontrado com ID: " + request.getTituloReceber()));
            entity.setTituloReceber(tr);
        } else {
            entity.setTituloReceber(null);
        }

        if (request.getTituloPagar() != null) {
            TituloPagar tp = tituloPagarRepository.findByIdAndTenant(request.getTituloPagar(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Título a pagar não encontrado com ID: " + request.getTituloPagar()));
            entity.setTituloPagar(tp);
        } else {
            entity.setTituloPagar(null);
        }

        if (request.getConciliacao() != null) {
            entity.setConciliacao(conciliacaoBancariaRepository.findByIdAndTenant(request.getConciliacao(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Conciliação não encontrada com ID: " + request.getConciliacao())));
        } else {
            entity.setConciliacao(null);
        }
    }

    public List<LancamentoFinanceiroItem> criarItens(LancamentoFinanceiro lancamento, List<LancamentoFinanceiroItemRequest> itensRequest, UUID tenantId, Tenant tenant) {
        List<LancamentoFinanceiroItem> itens = new ArrayList<>();
        if (itensRequest == null) return itens;

        for (LancamentoFinanceiroItemRequest itemReq : itensRequest) {
            LancamentoFinanceiroItem item = itemMapper.fromRequest(itemReq);
            item.setActive(true);
            item.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));
            item.setLancamento(lancamento);

            ContaContabil conta = contaContabilRepository.findByIdAndTenant(itemReq.getContaContabil(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Conta contábil não encontrada com ID: " + itemReq.getContaContabil()));
            item.setContaContabil(conta);

            if (itemReq.getCentroCusto() != null) {
                CentroCusto cc = centroCustoRepository.findByIdAndTenant(itemReq.getCentroCusto(), tenantId)
                        .orElseThrow(() -> new BadRequestException("Centro de custo não encontrado com ID: " + itemReq.getCentroCusto()));
                item.setCentroCusto(cc);
            } else {
                item.setCentroCusto(null);
            }

            itens.add(item);
        }

        return itens;
    }
}

