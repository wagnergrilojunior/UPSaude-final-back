package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.anomalia.SiaPaAnomaliaResponse;
import com.upsaude.entity.referencia.sia.anomalia.SiaPaAnomalia;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.referencia.sia.anomalia.SiaPaAnomaliaRepository;
import com.upsaude.service.api.sia.SiaPaAnomaliaDetectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiaPaAnomaliaDetectionServiceImpl implements SiaPaAnomaliaDetectionService {

    private final JdbcTemplate jdbcTemplate;
    private final SiaPaAnomaliaRepository repository;

    @Value("${sia.anomaly.detection.threshold.production-variance:2.0}")
    private double thresholdProductionVariance;

    @Value("${sia.anomaly.detection.threshold.value-difference:0.1}")
    private double thresholdValueDifference;

    @Override
    public int detectar(String competencia, String uf) {
        if (!StringUtils.hasText(competencia)) throw new BadRequestException("competencia é obrigatória (AAAAMM)");
        if (!StringUtils.hasText(uf)) throw new BadRequestException("uf é obrigatória (2 letras)");

        String ufEfetiva = uf.trim().toUpperCase();

        int inseridas = 0;

        inseridas += detectarDivergenciaValorSigtap(competencia, ufEfetiva);
        inseridas += detectarQuantidadeAprovadaMaiorQueProduzida(competencia, ufEfetiva);
        inseridas += detectarOutliersEstabelecimento(competencia, ufEfetiva);

        return inseridas;
    }

    @Override
    public Page<SiaPaAnomaliaResponse> listar(String competencia, String uf, Pageable pageable) {
        if (!StringUtils.hasText(competencia)) throw new BadRequestException("competencia é obrigatória (AAAAMM)");
        if (!StringUtils.hasText(uf)) throw new BadRequestException("uf é obrigatória (2 letras)");

        try {
            return repository.findByCompetenciaAndUf(competencia, uf.trim().toUpperCase(), pageable)
                    .map(this::toResponse);
        } catch (org.springframework.dao.InvalidDataAccessResourceUsageException e) {
            
            log.debug("Tabela sia_pa_anomalia não encontrada, retornando lista vazia");
            return new PageImpl<>(List.of(), pageable, 0);
        } catch (org.hibernate.exception.SQLGrammarException e) {
            
            log.debug("Tabela sia_pa_anomalia não encontrada, retornando lista vazia");
            return new PageImpl<>(List.of(), pageable, 0);
        }
    }

    @Override
    public SiaPaAnomaliaResponse obterPorId(UUID id) {
        if (id == null) throw new BadRequestException("id é obrigatório");
        SiaPaAnomalia a = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Anomalia não encontrada: " + id));
        return toResponse(a);
    }

    private int detectarDivergenciaValorSigtap(String competencia, String uf) {
        
        
        String sql = """
                INSERT INTO public.sia_pa_anomalia (
                    competencia, uf, tipo_anomalia, severidade, chave, descricao,
                    valor_atual, valor_referencia, delta, delta_pct, dados
                )
                SELECT
                    ap.competencia,
                    ap.uf,
                    'VALOR_DIVERGENTE_SIGTAP' AS tipo_anomalia,
                    CASE
                        WHEN ABS((ap.valor_aprovado_medio - p.valor_servico_ambulatorial) / NULLIF(p.valor_servico_ambulatorial, 0)) >= 0.50 THEN 'ALTA'
                        WHEN ABS((ap.valor_aprovado_medio - p.valor_servico_ambulatorial) / NULLIF(p.valor_servico_ambulatorial, 0)) >= 0.20 THEN 'MEDIA'
                        ELSE 'BAIXA'
                    END AS severidade,
                    ap.procedimento_codigo AS chave,
                    ('Procedimento com valor aprovado médio divergente do SIGTAP. proc=' || ap.procedimento_codigo) AS descricao,
                    ap.valor_aprovado_medio AS valor_atual,
                    p.valor_servico_ambulatorial AS valor_referencia,
                    (ap.valor_aprovado_medio - p.valor_servico_ambulatorial) AS delta,
                    (ap.valor_aprovado_medio - p.valor_servico_ambulatorial) / NULLIF(p.valor_servico_ambulatorial, 0) AS delta_pct,
                    NULL::jsonb AS dados
                FROM public.sia_pa_agregado_procedimento ap
                JOIN LATERAL (
                    SELECT p.valor_servico_ambulatorial
                    FROM public.sigtap_procedimento p
                    WHERE p.codigo_oficial = ap.procedimento_codigo
                      AND (p.competencia_inicial IS NULL OR p.competencia_inicial <= ap.competencia)
                      AND (p.competencia_final IS NULL OR p.competencia_final >= ap.competencia)
                    ORDER BY p.competencia_inicial DESC NULLS LAST
                    LIMIT 1
                ) p ON true
                WHERE ap.competencia = ? AND ap.uf = ?
                  AND p.valor_servico_ambulatorial IS NOT NULL
                  AND ap.valor_aprovado_medio IS NOT NULL
                  AND ABS((ap.valor_aprovado_medio - p.valor_servico_ambulatorial) / NULLIF(p.valor_servico_ambulatorial, 0)) >= ?
                ON CONFLICT (competencia, uf, tipo_anomalia, chave) DO NOTHING
                """;

        return jdbcTemplate.update(sql, competencia, uf, thresholdValueDifference);
    }

    private int detectarQuantidadeAprovadaMaiorQueProduzida(String competencia, String uf) {
        String sql = """
                INSERT INTO public.sia_pa_anomalia (
                    competencia, uf, tipo_anomalia, severidade, chave, descricao,
                    valor_atual, valor_referencia, delta, delta_pct, dados
                )
                SELECT
                    ap.competencia,
                    ap.uf,
                    'QTD_APROVADA_MAIOR_QTD_PRODUZIDA' AS tipo_anomalia,
                    'MEDIA' AS severidade,
                    ap.procedimento_codigo AS chave,
                    ('Quantidade aprovada maior que produzida para procedimento. proc=' || ap.procedimento_codigo) AS descricao,
                    ap.quantidade_aprovada_total::numeric AS valor_atual,
                    ap.quantidade_produzida_total::numeric AS valor_referencia,
                    (ap.quantidade_aprovada_total - ap.quantidade_produzida_total)::numeric AS delta,
                    (ap.quantidade_aprovada_total - ap.quantidade_produzida_total)::numeric / NULLIF(ap.quantidade_produzida_total::numeric, 0) AS delta_pct,
                    NULL::jsonb AS dados
                FROM public.sia_pa_agregado_procedimento ap
                WHERE ap.competencia = ? AND ap.uf = ?
                  AND ap.quantidade_aprovada_total > ap.quantidade_produzida_total
                ON CONFLICT (competencia, uf, tipo_anomalia, chave) DO NOTHING
                """;
        return jdbcTemplate.update(sql, competencia, uf);
    }

    private int detectarOutliersEstabelecimento(String competencia, String uf) {
        
        Map<String, Object> stats = jdbcTemplate.queryForMap("""
                SELECT
                    AVG(ae.valor_aprovado_total) AS media,
                    STDDEV_POP(ae.valor_aprovado_total) AS desvio
                FROM public.sia_pa_agregado_estabelecimento ae
                WHERE ae.competencia = ? AND ae.uf = ?
                """, competencia, uf);

        BigDecimal media = toBigDecimal(stats.get("media"));
        BigDecimal desvio = toBigDecimal(stats.get("desvio"));
        if (media == null || desvio == null) return 0;

        BigDecimal limite = media.add(desvio.multiply(BigDecimal.valueOf(thresholdProductionVariance)));

        String sql = """
                INSERT INTO public.sia_pa_anomalia (
                    competencia, uf, tipo_anomalia, severidade, chave, descricao,
                    valor_atual, valor_referencia, delta, delta_pct, dados
                )
                SELECT
                    ae.competencia,
                    ae.uf,
                    'OUTLIER_ESTABELECIMENTO_VALOR_APROVADO' AS tipo_anomalia,
                    'ALTA' AS severidade,
                    ae.codigo_cnes AS chave,
                    ('Estabelecimento com valor aprovado muito acima do esperado (outlier). cnes=' || COALESCE(ae.codigo_cnes,'')) AS descricao,
                    ae.valor_aprovado_total AS valor_atual,
                    ?::numeric AS valor_referencia,
                    (ae.valor_aprovado_total - ?::numeric) AS delta,
                    (ae.valor_aprovado_total - ?::numeric) / NULLIF(?::numeric, 0) AS delta_pct,
                    NULL::jsonb AS dados
                FROM public.sia_pa_agregado_estabelecimento ae
                WHERE ae.competencia = ? AND ae.uf = ?
                  AND ae.valor_aprovado_total IS NOT NULL
                  AND ae.valor_aprovado_total > ?::numeric
                ON CONFLICT (competencia, uf, tipo_anomalia, chave) DO NOTHING
                """;

        return jdbcTemplate.update(sql,
                limite, limite, limite, limite,
                competencia, uf, limite);
    }

    private SiaPaAnomaliaResponse toResponse(SiaPaAnomalia a) {
        return SiaPaAnomaliaResponse.builder()
                .id(a.getId())
                .criadoEm(a.getCreatedAt())
                .competencia(a.getCompetencia())
                .uf(a.getUf())
                .tipoAnomalia(a.getTipoAnomalia())
                .severidade(a.getSeveridade())
                .chave(a.getChave())
                .registroId(a.getRegistroId())
                .descricao(a.getDescricao())
                .valorAtual(a.getValorAtual())
                .valorReferencia(a.getValorReferencia())
                .delta(a.getDelta())
                .deltaPct(a.getDeltaPct())
                .dados(a.getDados())
                .build();
    }

    private BigDecimal toBigDecimal(Object v) {
        if (v == null) return null;
        if (v instanceof BigDecimal) return (BigDecimal) v;
        if (v instanceof Number) return BigDecimal.valueOf(((Number) v).doubleValue());
        try {
            return new BigDecimal(String.valueOf(v));
        } catch (Exception e) {
            return null;
        }
    }
}

