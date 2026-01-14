package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioProducaoResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopCidResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopProcedimentosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.api.sia.SiaPaRelatorioService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiaPaRelatorioServiceImpl implements SiaPaRelatorioService {

    private final JdbcTemplate jdbcTemplate;
    private final TenantService tenantService;

    @Override
    public SiaPaRelatorioProducaoResponse gerarRelatorioProducaoMensal(String uf, String competenciaInicio, String competenciaFim) {
        String ufEfetiva = resolverUf(uf);
        if (!StringUtils.hasText(ufEfetiva)) {
            throw new BadRequestException("uf é obrigatória (2 letras) ou deve ser inferível pelo tenant");
        }
        if (!StringUtils.hasText(competenciaInicio) || !StringUtils.hasText(competenciaFim)) {
            throw new BadRequestException("competenciaInicio e competenciaFim são obrigatórias (AAAAMM)");
        }

        // Usa a materialized view sia_pa_agregado_temporal
        List<SiaPaRelatorioProducaoResponse.ItemProducaoMensal> itens = jdbcTemplate.query("""
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
            (rs, rowNum) -> SiaPaRelatorioProducaoResponse.ItemProducaoMensal.builder()
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

        return SiaPaRelatorioProducaoResponse.builder()
                .uf(ufEfetiva)
                .competenciaInicio(competenciaInicio)
                .competenciaFim(competenciaFim)
                .itens(itens)
                .build();
    }

    @Override
    public SiaPaRelatorioTopProcedimentosResponse gerarTopProcedimentos(String uf, String competencia, Integer limit) {
        String ufEfetiva = resolverUf(uf);
        if (!StringUtils.hasText(ufEfetiva)) {
            throw new BadRequestException("uf é obrigatória (2 letras) ou deve ser inferível pelo tenant");
        }
        if (!StringUtils.hasText(competencia)) {
            throw new BadRequestException("competencia é obrigatória (AAAAMM)");
        }
        int lim = limit != null && limit > 0 ? Math.min(limit, 100) : 10;

        // Usa a materialized view sia_pa_agregado_procedimento
        // Busca o nome do procedimento: primeiro tenta encontrar um válido para a competência,
        // se não encontrar, busca o mais recente disponível
        List<SiaPaRelatorioTopProcedimentosResponse.ItemTopProcedimento> itens = jdbcTemplate.query("""
                    SELECT
                        ap.procedimento_codigo,
                        ap.quantidade_produzida_total,
                        ap.valor_aprovado_total,
                        ap.estabelecimentos_unicos,
                        ap.municipios_unicos,
                        COALESCE(
                            (SELECT p.nome
                             FROM public.sigtap_procedimento p
                             WHERE p.codigo_oficial = ap.procedimento_codigo
                               AND (p.competencia_inicial IS NULL OR p.competencia_inicial <= ap.competencia)
                               AND (p.competencia_final IS NULL OR p.competencia_final >= ap.competencia)
                             ORDER BY p.competencia_inicial DESC NULLS LAST
                             LIMIT 1),
                            (SELECT p.nome
                             FROM public.sigtap_procedimento p
                             WHERE p.codigo_oficial = ap.procedimento_codigo
                             ORDER BY p.competencia_inicial DESC NULLS LAST
                             LIMIT 1)
                        ) AS procedimento_nome
                    FROM public.sia_pa_agregado_procedimento ap
                    WHERE ap.uf = ? AND ap.competencia = ?
                    ORDER BY ap.quantidade_produzida_total DESC NULLS LAST
                    LIMIT ?
                    """,
            (rs, rowNum) -> SiaPaRelatorioTopProcedimentosResponse.ItemTopProcedimento.builder()
                    .procedimentoCodigo(rs.getString("procedimento_codigo"))
                    .procedimentoNome(rs.getString("procedimento_nome"))
                    .quantidadeProduzidaTotal(getLong(rs.getObject("quantidade_produzida_total")))
                    .valorAprovadoTotal((BigDecimal) rs.getObject("valor_aprovado_total"))
                    .estabelecimentosUnicos(getLong(rs.getObject("estabelecimentos_unicos")))
                    .municipiosUnicos(getLong(rs.getObject("municipios_unicos")))
                    .build(),
            ufEfetiva, competencia, lim
        );

        return SiaPaRelatorioTopProcedimentosResponse.builder()
                .uf(ufEfetiva)
                .competencia(competencia)
                .limit(lim)
                .itens(itens)
                .build();
    }

    @Override
    public SiaPaRelatorioTopCidResponse gerarTopCid(String uf, String competencia, Integer limit) {
        String ufEfetiva = resolverUf(uf);
        if (!StringUtils.hasText(ufEfetiva)) {
            throw new BadRequestException("uf é obrigatória (2 letras) ou deve ser inferível pelo tenant");
        }
        if (!StringUtils.hasText(competencia)) {
            throw new BadRequestException("competencia é obrigatória (AAAAMM)");
        }
        int lim = limit != null && limit > 0 ? Math.min(limit, 100) : 10;

        // Usa a materialized view sia_pa_agregado_cid e busca descrição do CID de múltiplas tabelas
        // Os topos serão calculados em uma segunda passada apenas para os itens que precisarem
        List<SiaPaRelatorioTopCidResponse.ItemTopCid> itens = jdbcTemplate.query("""
                    SELECT
                        ac.cid_principal_codigo,
                        ac.quantidade_produzida_total,
                        ac.valor_aprovado_total,
                        ac.top_procedimento_codigo,
                        ac.top_procedimento_total,
                        ac.top_municipio_ufmun_codigo,
                        ac.top_municipio_total,
                        COALESCE(
                            cid_sub.descricao, 
                            cid_cat.descricao,
                            CASE WHEN ac.cid_principal_codigo = '0000' THEN 'Sem diagnóstico principal' ELSE NULL END
                        ) AS cid_descricao
                    FROM public.sia_pa_agregado_cid ac
                    LEFT JOIN public.cid10_subcategorias cid_sub ON cid_sub.subcat = ac.cid_principal_codigo
                    LEFT JOIN public.cid10_categorias cid_cat ON cid_cat.cat = ac.cid_principal_codigo
                    WHERE ac.uf = ? AND ac.competencia = ?
                    ORDER BY ac.quantidade_produzida_total DESC NULLS LAST
                    LIMIT ?
                    """,
            (rs, rowNum) -> SiaPaRelatorioTopCidResponse.ItemTopCid.builder()
                    .cidPrincipalCodigo(rs.getString("cid_principal_codigo"))
                    .cidDescricao(rs.getString("cid_descricao"))
                    .quantidadeProduzidaTotal(getLong(rs.getObject("quantidade_produzida_total")))
                    .valorAprovadoTotal((BigDecimal) rs.getObject("valor_aprovado_total"))
                    .topProcedimentoCodigo(rs.getString("top_procedimento_codigo"))
                    .topProcedimentoTotal(getLong(rs.getObject("top_procedimento_total")))
                    .topMunicipioUfmunCodigo(rs.getString("top_municipio_ufmun_codigo"))
                    .topMunicipioTotal(getLong(rs.getObject("top_municipio_total")))
                    .build(),
            ufEfetiva, competencia, lim
        );

        // Preenche os topos que estão null calculando diretamente da tabela sia_pa
        for (SiaPaRelatorioTopCidResponse.ItemTopCid item : itens) {
            if (item.getTopProcedimentoCodigo() == null) {
                String topProc = jdbcTemplate.queryForObject("""
                        SELECT procedimento_codigo
                        FROM public.sia_pa
                        WHERE uf = ? AND competencia = ? AND cid_principal_codigo = ?
                        GROUP BY procedimento_codigo
                        ORDER BY COUNT(*) DESC
                        LIMIT 1
                        """, String.class, ufEfetiva, competencia, item.getCidPrincipalCodigo());
                if (topProc != null) {
                    Long topProcTotal = jdbcTemplate.queryForObject("""
                            SELECT COUNT(*)
                            FROM public.sia_pa
                            WHERE uf = ? AND competencia = ? AND cid_principal_codigo = ? AND procedimento_codigo = ?
                            """, Long.class, ufEfetiva, competencia, item.getCidPrincipalCodigo(), topProc);
                    item.setTopProcedimentoCodigo(topProc);
                    item.setTopProcedimentoTotal(topProcTotal);
                }
            }
            
            if (item.getTopMunicipioUfmunCodigo() == null) {
                String topMun = jdbcTemplate.queryForObject("""
                        SELECT municipio_ufmun_codigo
                        FROM public.sia_pa
                        WHERE uf = ? AND competencia = ? AND cid_principal_codigo = ? AND municipio_ufmun_codigo IS NOT NULL
                        GROUP BY municipio_ufmun_codigo
                        ORDER BY COUNT(*) DESC
                        LIMIT 1
                        """, String.class, ufEfetiva, competencia, item.getCidPrincipalCodigo());
                if (topMun != null) {
                    Long topMunTotal = jdbcTemplate.queryForObject("""
                            SELECT COUNT(*)
                            FROM public.sia_pa
                            WHERE uf = ? AND competencia = ? AND cid_principal_codigo = ? AND municipio_ufmun_codigo = ?
                            """, Long.class, ufEfetiva, competencia, item.getCidPrincipalCodigo(), topMun);
                    item.setTopMunicipioUfmunCodigo(topMun);
                    item.setTopMunicipioTotal(topMunTotal);
                }
            }
        }

        return SiaPaRelatorioTopCidResponse.builder()
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
}

