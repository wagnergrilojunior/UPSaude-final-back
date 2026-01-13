package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.analytics.SiaPaComparacaoResponse;
import com.upsaude.api.response.sia.analytics.SiaPaRankingResponse;
import com.upsaude.api.response.sia.analytics.SiaPaSazonalidadeResponse;
import com.upsaude.api.response.sia.analytics.SiaPaTendenciaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.api.sia.SiaPaAnalyticsService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiaPaAnalyticsServiceImpl implements SiaPaAnalyticsService {

    private final JdbcTemplate jdbcTemplate;
    private final TenantService tenantService;

    @Override
    public SiaPaTendenciaResponse calcularTendenciaTemporal(String uf, String competenciaInicio, String competenciaFim) {
        String ufEfetiva = resolverUf(uf);
        if (!StringUtils.hasText(ufEfetiva)) throw new BadRequestException("uf é obrigatória (ou inferível pelo tenant)");
        if (!StringUtils.hasText(competenciaInicio) || !StringUtils.hasText(competenciaFim)) {
            throw new BadRequestException("competenciaInicio e competenciaFim são obrigatórias");
        }

        List<SiaPaTendenciaResponse.PontoTendencia> pontos = jdbcTemplate.query("""
                        SELECT
                            t.competencia,
                            t.quantidade_produzida_total,
                            t.valor_produzido_total,
                            t.valor_aprovado_total,
                            t.valor_aprovado_prev,
                            t.delta_valor_aprovado,
                            t.crescimento_valor_aprovado_pct
                        FROM public.sia_pa_agregado_temporal t
                        WHERE t.uf = ?
                          AND t.competencia >= ?
                          AND t.competencia <= ?
                        ORDER BY t.competencia ASC
                        """,
                (rs, rowNum) -> SiaPaTendenciaResponse.PontoTendencia.builder()
                        .competencia(rs.getString("competencia"))
                        .quantidadeProduzidaTotal(getLong(rs.getObject("quantidade_produzida_total")))
                        .valorProduzidoTotal((BigDecimal) rs.getObject("valor_produzido_total"))
                        .valorAprovadoTotal((BigDecimal) rs.getObject("valor_aprovado_total"))
                        .valorAprovadoPrev((BigDecimal) rs.getObject("valor_aprovado_prev"))
                        .deltaValorAprovado((BigDecimal) rs.getObject("delta_valor_aprovado"))
                        .crescimentoValorAprovadoPct((BigDecimal) rs.getObject("crescimento_valor_aprovado_pct"))
                        .build(),
                ufEfetiva, competenciaInicio, competenciaFim
        );

        return SiaPaTendenciaResponse.builder()
                .uf(ufEfetiva)
                .competenciaInicio(competenciaInicio)
                .competenciaFim(competenciaFim)
                .pontos(pontos)
                .build();
    }

    @Override
    public SiaPaSazonalidadeResponse calcularSazonalidade(String uf, String competenciaInicio, String competenciaFim) {
        String ufEfetiva = resolverUf(uf);
        if (!StringUtils.hasText(ufEfetiva)) throw new BadRequestException("uf é obrigatória (ou inferível pelo tenant)");
        if (!StringUtils.hasText(competenciaInicio) || !StringUtils.hasText(competenciaFim)) {
            throw new BadRequestException("competenciaInicio e competenciaFim são obrigatórias");
        }

        List<SiaPaSazonalidadeResponse.ItemMes> meses = jdbcTemplate.query("""
                        WITH base AS (
                            SELECT
                                SUBSTRING(t.competencia, 5, 2) AS mes,
                                t.valor_aprovado_total,
                                t.quantidade_produzida_total
                            FROM public.sia_pa_agregado_temporal t
                            WHERE t.uf = ?
                              AND t.competencia >= ?
                              AND t.competencia <= ?
                        )
                        SELECT
                            mes,
                            AVG(valor_aprovado_total) AS valor_aprovado_medio,
                            AVG(quantidade_produzida_total) AS quantidade_produzida_media
                        FROM base
                        GROUP BY mes
                        ORDER BY mes ASC
                        """,
                (rs, rowNum) -> SiaPaSazonalidadeResponse.ItemMes.builder()
                        .mes(rs.getString("mes"))
                        .valorAprovadoMedio((BigDecimal) rs.getObject("valor_aprovado_medio"))
                        .quantidadeProduzidaMedia((BigDecimal) rs.getObject("quantidade_produzida_media"))
                        .build(),
                ufEfetiva, competenciaInicio, competenciaFim
        );

        return SiaPaSazonalidadeResponse.builder()
                .uf(ufEfetiva)
                .competenciaInicio(competenciaInicio)
                .competenciaFim(competenciaFim)
                .meses(meses)
                .build();
    }

    @Override
    public SiaPaComparacaoResponse compararPeriodos(String uf, String competenciaBase, String competenciaComparacao) {
        String ufEfetiva = resolverUf(uf);
        if (!StringUtils.hasText(ufEfetiva)) throw new BadRequestException("uf é obrigatória (ou inferível pelo tenant)");
        if (!StringUtils.hasText(competenciaBase) || !StringUtils.hasText(competenciaComparacao)) {
            throw new BadRequestException("competenciaBase e competenciaComparacao são obrigatórias");
        }

        Map<String, Object> base = jdbcTemplate.queryForMap("""
                SELECT
                    COALESCE(SUM(valor_aprovado_total), 0) AS valor_aprovado_total,
                    COALESCE(SUM(quantidade_produzida_total), 0) AS quantidade_produzida_total
                FROM public.sia_pa_agregado_temporal
                WHERE uf = ? AND competencia = ?
                """, ufEfetiva, competenciaBase);

        Map<String, Object> comp = jdbcTemplate.queryForMap("""
                SELECT
                    COALESCE(SUM(valor_aprovado_total), 0) AS valor_aprovado_total,
                    COALESCE(SUM(quantidade_produzida_total), 0) AS quantidade_produzida_total
                FROM public.sia_pa_agregado_temporal
                WHERE uf = ? AND competencia = ?
                """, ufEfetiva, competenciaComparacao);

        BigDecimal valorBase = toBigDecimal(base.get("valor_aprovado_total"));
        BigDecimal valorComp = toBigDecimal(comp.get("valor_aprovado_total"));
        BigDecimal deltaValor = valorComp.subtract(valorBase);
        BigDecimal deltaValorPct = null;
        if (valorBase.compareTo(BigDecimal.ZERO) > 0) {
            deltaValorPct = valorComp.divide(valorBase, 6, RoundingMode.HALF_UP).subtract(BigDecimal.ONE);
        }

        long qtdBase = toLong(base.get("quantidade_produzida_total"));
        long qtdComp = toLong(comp.get("quantidade_produzida_total"));
        long deltaQtd = qtdComp - qtdBase;
        BigDecimal deltaQtdPct = null;
        if (qtdBase > 0) {
            deltaQtdPct = new BigDecimal(qtdComp)
                    .divide(new BigDecimal(qtdBase), 6, RoundingMode.HALF_UP)
                    .subtract(BigDecimal.ONE);
        }

        return SiaPaComparacaoResponse.builder()
                .uf(ufEfetiva)
                .competenciaBase(competenciaBase)
                .competenciaComparacao(competenciaComparacao)
                .valorAprovadoBase(valorBase)
                .valorAprovadoComparacao(valorComp)
                .deltaValorAprovado(deltaValor)
                .deltaValorAprovadoPct(deltaValorPct)
                .quantidadeProduzidaBase(qtdBase)
                .quantidadeProduzidaComparacao(qtdComp)
                .deltaQuantidadeProduzida(deltaQtd)
                .deltaQuantidadeProduzidaPct(deltaQtdPct)
                .build();
    }

    @Override
    public SiaPaRankingResponse rankingEstabelecimentos(String uf, String competencia, Integer limit) {
        String ufEfetiva = resolverUf(uf);
        if (!StringUtils.hasText(ufEfetiva)) throw new BadRequestException("uf é obrigatória (ou inferível pelo tenant)");
        if (!StringUtils.hasText(competencia)) throw new BadRequestException("competencia é obrigatória");
        int lim = limit != null && limit > 0 ? Math.min(limit, 200) : 20;

        List<SiaPaRankingResponse.ItemRanking> itens = jdbcTemplate.query("""
                        SELECT
                            ae.codigo_cnes AS chave,
                            est.nome AS descricao,
                            ae.valor_aprovado_total,
                            ae.quantidade_produzida_total
                        FROM public.sia_pa_agregado_estabelecimento ae
                        LEFT JOIN LATERAL (
                            SELECT e.nome
                            FROM public.estabelecimentos e
                            WHERE e.cnes = ae.codigo_cnes
                            ORDER BY e.data_ultima_sincronizacao_cnes DESC NULLS LAST
                            LIMIT 1
                        ) est ON true
                        WHERE ae.uf = ? AND ae.competencia = ?
                        ORDER BY ae.valor_aprovado_total DESC NULLS LAST
                        LIMIT ?
                        """,
                (rs, rowNum) -> SiaPaRankingResponse.ItemRanking.builder()
                        .posicao(rowNum + 1)
                        .chave(rs.getString("chave"))
                        .descricao(rs.getString("descricao"))
                        .valorAprovadoTotal((BigDecimal) rs.getObject("valor_aprovado_total"))
                        .quantidadeProduzidaTotal(getLong(rs.getObject("quantidade_produzida_total")))
                        .build(),
                ufEfetiva, competencia, lim
        );

        return SiaPaRankingResponse.builder()
                .uf(ufEfetiva)
                .competencia(competencia)
                .limit(lim)
                .itens(itens)
                .build();
    }

    private String resolverUf(String uf) {
        if (StringUtils.hasText(uf)) return uf.trim().toUpperCase();
        try {
            var tenant = tenantService.obterTenantDoUsuarioAutenticado();
            var endereco = tenant != null ? tenant.getEndereco() : null;
            if (endereco != null && endereco.getEstado() != null) {
                String ufEstado = StringUtils.hasText(endereco.getEstado().getSiglaIbge())
                        ? endereco.getEstado().getSiglaIbge()
                        : endereco.getEstado().getSigla();
                if (StringUtils.hasText(ufEstado)) return ufEstado.trim().toUpperCase();
            }
        } catch (Exception e) {
            log.debug("Não foi possível resolver UF via tenant: {}", e.getMessage());
        }
        return null;
    }

    private Long getLong(Object v) {
        if (v == null) return null;
        if (v instanceof Number) return ((Number) v).longValue();
        try {
            return Long.parseLong(String.valueOf(v));
        } catch (Exception e) {
            return null;
        }
    }

    private long toLong(Object v) {
        if (v == null) return 0L;
        if (v instanceof Number) return ((Number) v).longValue();
        try {
            return Long.parseLong(String.valueOf(v));
        } catch (Exception e) {
            return 0L;
        }
    }

    private BigDecimal toBigDecimal(Object v) {
        if (v == null) return BigDecimal.ZERO;
        if (v instanceof BigDecimal) return (BigDecimal) v;
        if (v instanceof Number) return BigDecimal.valueOf(((Number) v).doubleValue());
        try {
            return new BigDecimal(String.valueOf(v));
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}

