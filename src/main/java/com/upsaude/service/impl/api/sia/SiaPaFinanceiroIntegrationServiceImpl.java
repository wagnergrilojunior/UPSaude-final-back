package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.financeiro.SiaPaFinanceiroIntegracaoResponse;
import com.upsaude.api.response.sia.financeiro.SiaPaFinanceiroReceitaPorCompetenciaResponse;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.service.api.sia.SiaPaFinanceiroIntegrationService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Array;
import java.sql.Connection;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiaPaFinanceiroIntegrationServiceImpl implements SiaPaFinanceiroIntegrationService {

    private final JdbcTemplate jdbcTemplate;
    private final TenantService tenantService;
    private final EstabelecimentosRepository estabelecimentosRepository;

    @Override
    public SiaPaFinanceiroIntegracaoResponse conciliacao(String competencia, String uf, Integer limitNaoFaturados) {
        if (!StringUtils.hasText(competencia)) throw new BadRequestException("competencia é obrigatória (AAAAMM)");
        if (!StringUtils.hasText(uf)) throw new BadRequestException("uf é obrigatória (2 letras)");

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null) throw new BadRequestException("Tenant autenticado não encontrado");

        String ufEfetiva = uf.trim().toUpperCase();
        int limNaoFat = limitNaoFaturados != null && limitNaoFaturados > 0 ? Math.min(limitNaoFaturados, 200) : 50;

        List<String> cnesList = estabelecimentosRepository.findByTenant(tenant).stream()
                .map(e -> e.getDadosIdentificacao() != null ? e.getDadosIdentificacao().getCnes() : null)
                .filter(StringUtils::hasText)
                .distinct()
                .toList();

        Map<String, ProcedimentoAgg> siaByProc = buscarSiaPorProcedimento(competencia, ufEfetiva, cnesList);
        Map<String, ProcedimentoAgg> fatByProc = buscarFaturamentoPorProcedimento(competencia, tenant.getId());

        Set<String> allProc = new HashSet<>();
        allProc.addAll(siaByProc.keySet());
        allProc.addAll(fatByProc.keySet());

        List<SiaPaFinanceiroIntegracaoResponse.ItemConciliacaoProcedimento> itens = allProc.stream()
                .map(proc -> montarItem(proc, siaByProc.get(proc), fatByProc.get(proc), competencia))
                .sorted(Comparator.comparing((SiaPaFinanceiroIntegracaoResponse.ItemConciliacaoProcedimento i) ->
                        i.getDeltaValor() != null ? i.getDeltaValor().abs() : BigDecimal.ZERO).reversed())
                .toList();

        List<SiaPaFinanceiroIntegracaoResponse.ItemConciliacaoProcedimento> naoFaturados = itens.stream()
                .filter(i -> (i.getSiaQuantidade() != null && i.getSiaQuantidade() > 0)
                        && (i.getFaturamentoQuantidade() == null || i.getFaturamentoQuantidade() == 0))
                .limit(limNaoFat)
                .toList();

        BigDecimal siaValorTotal = itens.stream()
                .map(i -> i.getSiaValorAprovado() != null ? i.getSiaValorAprovado() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal fatValorTotal = itens.stream()
                .map(i -> i.getFaturamentoValor() != null ? i.getFaturamentoValor() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long siaQtdTotal = itens.stream().mapToLong(i -> i.getSiaQuantidade() != null ? i.getSiaQuantidade() : 0L).sum();
        long fatQtdTotal = itens.stream().mapToLong(i -> i.getFaturamentoQuantidade() != null ? i.getFaturamentoQuantidade() : 0L).sum();

        BigDecimal deltaValor = fatValorTotal.subtract(siaValorTotal);
        BigDecimal deltaPct = null;
        if (siaValorTotal.compareTo(BigDecimal.ZERO) > 0) {
            deltaPct = fatValorTotal.divide(siaValorTotal, 6, RoundingMode.HALF_UP).subtract(BigDecimal.ONE);
        }

        return SiaPaFinanceiroIntegracaoResponse.builder()
                .tenantId(tenant.getId())
                .uf(ufEfetiva)
                .competencia(competencia)
                .siaValorAprovadoTotal(siaValorTotal)
                .faturamentoValorTotal(fatValorTotal)
                .deltaValor(deltaValor)
                .deltaValorPct(deltaPct)
                .siaQuantidadeTotal(siaQtdTotal)
                .faturamentoQuantidadeTotal(fatQtdTotal)
                .deltaQuantidade(fatQtdTotal - siaQtdTotal)
                .itens(itens)
                .procedimentosNaoFaturados(naoFaturados)
                .build();
    }

    @Override
    public SiaPaFinanceiroReceitaPorCompetenciaResponse receitaPorCompetencia(String uf, String competenciaInicio, String competenciaFim) {
        if (!StringUtils.hasText(uf)) throw new BadRequestException("uf é obrigatória (2 letras)");
        if (!StringUtils.hasText(competenciaInicio) || !StringUtils.hasText(competenciaFim)) {
            throw new BadRequestException("competenciaInicio e competenciaFim são obrigatórias (AAAAMM)");
        }

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null) throw new BadRequestException("Tenant autenticado não encontrado");

        String ufEfetiva = uf.trim().toUpperCase();

        List<String> competencias = jdbcTemplate.queryForList("""
                SELECT DISTINCT s.competencia
                FROM public.sia_pa s
                WHERE s.uf = ?
                  AND s.competencia >= ?
                  AND s.competencia <= ?
                ORDER BY s.competencia ASC
                """, String.class, ufEfetiva, competenciaInicio, competenciaFim);

        List<SiaPaFinanceiroReceitaPorCompetenciaResponse.Item> itens = new ArrayList<>();
        for (String comp : competencias) {
            SiaPaFinanceiroIntegracaoResponse conc = conciliacao(comp, ufEfetiva, 0);
            BigDecimal fat = conc.getFaturamentoValorTotal() != null ? conc.getFaturamentoValorTotal() : BigDecimal.ZERO;
            BigDecimal sia = conc.getSiaValorAprovadoTotal() != null ? conc.getSiaValorAprovadoTotal() : BigDecimal.ZERO;
            itens.add(SiaPaFinanceiroReceitaPorCompetenciaResponse.Item.builder()
                    .competencia(comp)
                    .faturamentoValorTotal(fat)
                    .siaValorAprovadoTotal(sia)
                    .deltaValor(fat.subtract(sia))
                    .build());
        }

        return SiaPaFinanceiroReceitaPorCompetenciaResponse.builder()
                .tenantId(tenant.getId())
                .uf(ufEfetiva)
                .competenciaInicio(competenciaInicio)
                .competenciaFim(competenciaFim)
                .itens(itens)
                .build();
    }

    private Map<String, ProcedimentoAgg> buscarSiaPorProcedimento(String competencia, String uf, List<String> cnesList) {
        if (cnesList == null || cnesList.isEmpty()) {
            return Collections.emptyMap();
        }

        return jdbcTemplate.execute((Connection con) -> {
            Array cnesArray = con.createArrayOf("text", cnesList.toArray());
            var ps = con.prepareStatement("""
                    SELECT
                        s.procedimento_codigo,
                        COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS qtd,
                        COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor
                    FROM public.sia_pa s
                    WHERE s.competencia = ?
                      AND s.uf = ?
                      AND s.codigo_cnes = ANY(?)
                    GROUP BY s.procedimento_codigo
                    """);
            ps.setString(1, competencia);
            ps.setString(2, uf);
            ps.setArray(3, cnesArray);
            return ps;
        }, ps -> {
            Map<String, ProcedimentoAgg> map = new HashMap<>();
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    String proc = rs.getString("procedimento_codigo");
                    long qtd = rs.getLong("qtd");
                    BigDecimal valor = (BigDecimal) rs.getObject("valor");
                    map.put(proc, new ProcedimentoAgg(qtd, valor));
                }
            }
            return map;
        });
    }

    private Map<String, ProcedimentoAgg> buscarFaturamentoPorProcedimento(String competenciaCodigo, UUID tenantId) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("""
                SELECT
                    sp.codigo_oficial AS procedimento_codigo,
                    COALESCE(SUM(COALESCE(i.quantidade, 0)), 0) AS qtd,
                    COALESCE(SUM(COALESCE(i.valor_total, 0)), 0) AS valor
                FROM public.documento_faturamento d
                JOIN public.competencia_financeira cf ON cf.id = d.competencia_id
                JOIN public.documento_faturamento_item i ON i.documento_id = d.id
                JOIN public.sigtap_procedimento sp ON sp.id = i.sigtap_procedimento_id
                WHERE d.tenant_id = ?
                  AND cf.codigo = ?
                  AND (d.status IS NULL OR d.status <> 'CANCELADO')
                GROUP BY sp.codigo_oficial
                """, tenantId, competenciaCodigo);

        Map<String, ProcedimentoAgg> map = new HashMap<>();
        for (Map<String, Object> r : rows) {
            String proc = r.get("procedimento_codigo") != null ? String.valueOf(r.get("procedimento_codigo")) : null;
            if (!StringUtils.hasText(proc)) continue;
            long qtd = r.get("qtd") instanceof Number ? ((Number) r.get("qtd")).longValue() : 0L;
            BigDecimal valor = r.get("valor") instanceof BigDecimal ? (BigDecimal) r.get("valor") : BigDecimal.ZERO;
            map.put(proc, new ProcedimentoAgg(qtd, valor));
        }
        return map;
    }

    private SiaPaFinanceiroIntegracaoResponse.ItemConciliacaoProcedimento montarItem(String proc,
                                                                                    ProcedimentoAgg sia,
                                                                                    ProcedimentoAgg fat,
                                                                                    String competencia) {
        long siaQtd = sia != null ? sia.qtd : 0L;
        BigDecimal siaValor = sia != null && sia.valor != null ? sia.valor : BigDecimal.ZERO;
        long fatQtd = fat != null ? fat.qtd : 0L;
        BigDecimal fatValor = fat != null && fat.valor != null ? fat.valor : BigDecimal.ZERO;

        BigDecimal delta = fatValor.subtract(siaValor);
        BigDecimal deltaPct = null;
        if (siaValor.compareTo(BigDecimal.ZERO) > 0) {
            deltaPct = fatValor.divide(siaValor, 6, RoundingMode.HALF_UP).subtract(BigDecimal.ONE);
        }

        String nome = buscarNomeProcedimento(proc, competencia);

        return SiaPaFinanceiroIntegracaoResponse.ItemConciliacaoProcedimento.builder()
                .procedimentoCodigo(proc)
                .procedimentoNome(nome)
                .siaQuantidade(siaQtd)
                .siaValorAprovado(siaValor)
                .faturamentoQuantidade(fatQtd)
                .faturamentoValor(fatValor)
                .deltaValor(delta)
                .deltaValorPct(deltaPct)
                .build();
    }

    private String buscarNomeProcedimento(String procedimentoCodigo, String competencia) {
        if (!StringUtils.hasText(procedimentoCodigo)) return null;
        try {
            return jdbcTemplate.queryForObject("""
                    SELECT p.nome
                    FROM public.sigtap_procedimento p
                    WHERE p.codigo_oficial = ?
                      AND (p.competencia_inicial IS NULL OR p.competencia_inicial <= ?)
                      AND (p.competencia_final IS NULL OR p.competencia_final >= ?)
                    ORDER BY p.competencia_inicial DESC NULLS LAST
                    LIMIT 1
                    """, String.class, procedimentoCodigo, competencia, competencia);
        } catch (Exception e) {
            return null;
        }
    }

    private record ProcedimentoAgg(long qtd, BigDecimal valor) {}
}

