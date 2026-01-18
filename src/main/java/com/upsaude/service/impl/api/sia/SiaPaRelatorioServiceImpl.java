package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioProducaoResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopCidResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopProcedimentosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.api.sia.SiaPaRelatorioService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.relatorios.TenantFilterHelper;
import com.upsaude.service.api.support.sia.SiaDataEnricher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiaPaRelatorioServiceImpl implements SiaPaRelatorioService {

    private final JdbcTemplate jdbcTemplate;
    private final TenantService tenantService;
    private final TenantFilterHelper tenantFilterHelper;
    private final SiaDataEnricher siaDataEnricher;

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "siaRelatorioProducaoMensal",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public SiaPaRelatorioProducaoResponse gerarRelatorioProducaoMensal(String uf, String competenciaInicio, String competenciaFim) {
        String ufEfetiva = resolverUf(uf);
        if (!StringUtils.hasText(ufEfetiva)) {
            throw new BadRequestException("uf é obrigatória (2 letras) ou deve ser inferível pelo tenant");
        }
        if (!StringUtils.hasText(competenciaInicio) || !StringUtils.hasText(competenciaFim)) {
            throw new BadRequestException("competenciaInicio e competenciaFim são obrigatórias (AAAAMM)");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        List<String> cnesList = tenantFilterHelper.obterCnesDoTenant(tenantId);
        
        if (cnesList.isEmpty()) {
            log.warn("Nenhum CNES encontrado para o tenant {}. Retornando relatório vazio.", tenantId);
            return SiaPaRelatorioProducaoResponse.builder()
                    .uf(ufEfetiva)
                    .competenciaInicio(competenciaInicio)
                    .competenciaFim(competenciaFim)
                    .itens(List.of())
                    .build();
        }

        
        String placeholders = String.join(",", java.util.Collections.nCopies(cnesList.size(), "?"));
        Object[] params = new Object[3 + cnesList.size()];
        params[0] = Objects.requireNonNull(ufEfetiva, "UF não pode ser null");
        params[1] = Objects.requireNonNull(competenciaInicio, "Competência início não pode ser null");
        params[2] = Objects.requireNonNull(competenciaFim, "Competência fim não pode ser null");
        for (int i = 0; i < cnesList.size(); i++) {
            params[3 + i] = Objects.requireNonNull(cnesList.get(i), "CNES não pode ser null");
        }

        
        List<SiaPaRelatorioProducaoResponse.ItemProducaoMensal> itens;
        try {
            String querySql = String.format("""
                        SELECT
                            mv.competencia,
                            SUM(mv.quantidade_produzida_total) AS quantidade_produzida_total,
                            SUM(mv.valor_produzido_total) AS valor_produzido_total,
                            SUM(mv.valor_aprovado_total) AS valor_aprovado_total,
                            NULL AS valor_aprovado_prev,
                            NULL AS delta_valor_aprovado,
                            NULL AS crescimento_valor_aprovado_pct
                        FROM public.mv_sia_pa_producao_mensal mv
                        WHERE mv.uf = ?
                          AND mv.competencia >= ?
                          AND mv.competencia <= ?
                          AND mv.codigo_cnes IN (%s)
                        GROUP BY mv.competencia
                        ORDER BY mv.competencia ASC
                        """, placeholders);
            itens = jdbcTemplate.query(
                Objects.requireNonNull(querySql, "Query SQL não pode ser null"),
                (rs, rowNum) -> SiaPaRelatorioProducaoResponse.ItemProducaoMensal.builder()
                        .competencia(rs.getString("competencia"))
                        .quantidadeProduzidaTotal(getLong(rs.getObject("quantidade_produzida_total")))
                        .valorProduzidoTotal((BigDecimal) rs.getObject("valor_produzido_total"))
                        .valorAprovadoTotal((BigDecimal) rs.getObject("valor_aprovado_total"))
                        .valorAprovadoPrev((BigDecimal) rs.getObject("valor_aprovado_prev"))
                        .deltaValorAprovado((BigDecimal) rs.getObject("delta_valor_aprovado"))
                        .crescimentoValorAprovadoPct((BigDecimal) rs.getObject("crescimento_valor_aprovado_pct"))
                        .build(),
                params
            );
        } catch (Exception e) {
            
            log.debug("View materializada não disponível, usando tabela original: {}", e.getMessage());
            String querySql = String.format("""
                        SELECT
                            s.competencia,
                            COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
                            COALESCE(SUM(COALESCE(s.valor_produzido, 0)), 0) AS valor_produzido_total,
                            COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total,
                            NULL AS valor_aprovado_prev,
                            NULL AS delta_valor_aprovado,
                            NULL AS crescimento_valor_aprovado_pct
                        FROM public.sia_pa s
                        WHERE s.uf = ?
                          AND s.competencia >= ?
                          AND s.competencia <= ?
                          AND s.codigo_cnes IN (%s)
                        GROUP BY s.competencia
                        ORDER BY s.competencia ASC
                        """, placeholders);
            itens = jdbcTemplate.query(
                Objects.requireNonNull(querySql, "Query SQL não pode ser null"),
                (rs, rowNum) -> SiaPaRelatorioProducaoResponse.ItemProducaoMensal.builder()
                        .competencia(rs.getString("competencia"))
                        .quantidadeProduzidaTotal(getLong(rs.getObject("quantidade_produzida_total")))
                        .valorProduzidoTotal((BigDecimal) rs.getObject("valor_produzido_total"))
                        .valorAprovadoTotal((BigDecimal) rs.getObject("valor_aprovado_total"))
                        .valorAprovadoPrev((BigDecimal) rs.getObject("valor_aprovado_prev"))
                        .deltaValorAprovado((BigDecimal) rs.getObject("delta_valor_aprovado"))
                        .crescimentoValorAprovadoPct((BigDecimal) rs.getObject("crescimento_valor_aprovado_pct"))
                        .build(),
                params
            );
        }

        return SiaPaRelatorioProducaoResponse.builder()
                .uf(ufEfetiva)
                .competenciaInicio(competenciaInicio)
                .competenciaFim(competenciaFim)
                .itens(itens)
                .build();
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "siaRelatorioTopProcedimentos",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public SiaPaRelatorioTopProcedimentosResponse gerarTopProcedimentos(String uf, String competencia, Integer limit) {
        String ufEfetiva = resolverUf(uf);
        if (!StringUtils.hasText(ufEfetiva)) {
            throw new BadRequestException("uf é obrigatória (2 letras) ou deve ser inferível pelo tenant");
        }
        if (!StringUtils.hasText(competencia)) {
            throw new BadRequestException("competencia é obrigatória (AAAAMM)");
        }
        int lim = limit != null && limit > 0 ? Math.min(limit, 100) : 10;

        UUID tenantId = tenantService.validarTenantAtual();
        List<String> cnesList = tenantFilterHelper.obterCnesDoTenant(tenantId);
        
        if (cnesList.isEmpty()) {
            log.warn("Nenhum CNES encontrado para o tenant {}. Retornando relatório vazio.", tenantId);
            return SiaPaRelatorioTopProcedimentosResponse.builder()
                    .uf(ufEfetiva)
                    .competencia(competencia)
                    .limit(lim)
                    .itens(List.of())
                    .build();
        }

        
        String placeholders = String.join(",", java.util.Collections.nCopies(cnesList.size(), "?"));
        Object[] params = new Object[3 + cnesList.size()];
        params[0] = Objects.requireNonNull(ufEfetiva, "UF não pode ser null");
        params[1] = Objects.requireNonNull(competencia, "Competência não pode ser null");
        params[2] = lim;
        for (int i = 0; i < cnesList.size(); i++) {
            params[3 + i] = Objects.requireNonNull(cnesList.get(i), "CNES não pode ser null");
        }

        
        String querySql;
        try {
            
            Integer viewExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM pg_matviews WHERE matviewname = 'mv_sia_pa_top_procedimentos'",
                Integer.class
            );
            if (viewExists != null && viewExists > 0) {
                
                querySql = String.format("""
                        SELECT
                            mv.procedimento_codigo,
                            SUM(mv.quantidade_produzida_total) AS quantidade_produzida_total,
                            SUM(mv.valor_aprovado_total) AS valor_aprovado_total,
                            COUNT(DISTINCT mv.estabelecimentos_unicos) AS estabelecimentos_unicos,
                            COUNT(DISTINCT s.municipio_ufmun_codigo) AS municipios_unicos
                        FROM public.mv_sia_pa_top_procedimentos mv
                        INNER JOIN public.sia_pa s ON s.procedimento_codigo = mv.procedimento_codigo 
                            AND s.competencia = mv.competencia AND s.uf = mv.uf
                        WHERE mv.uf = ? AND mv.competencia = ? AND s.codigo_cnes IN (%s)
                        GROUP BY mv.procedimento_codigo, mv.competencia
                        ORDER BY quantidade_produzida_total DESC NULLS LAST
                        LIMIT ?
                        """, placeholders);
            } else {
                throw new Exception("View materializada não existe");
            }
        } catch (Exception e) {
            
            log.debug("View materializada não disponível, usando tabela original: {}", e.getMessage());
            querySql = String.format("""
                    SELECT
                        s.procedimento_codigo,
                        COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
                        COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total,
                        COUNT(DISTINCT s.codigo_cnes) AS estabelecimentos_unicos,
                        COUNT(DISTINCT s.municipio_ufmun_codigo) AS municipios_unicos
                    FROM public.sia_pa s
                    WHERE s.uf = ? AND s.competencia = ? AND s.codigo_cnes IN (%s)
                    GROUP BY s.procedimento_codigo, s.competencia
                    ORDER BY quantidade_produzida_total DESC NULLS LAST
                    LIMIT ?
                    """, placeholders);
        }
        
        List<SiaPaRelatorioTopProcedimentosResponse.ItemTopProcedimento> itens = jdbcTemplate.query(
            Objects.requireNonNull(querySql, "Query SQL não pode ser null"),
            (rs, rowNum) -> {
                String procedimentoCodigo = rs.getString("procedimento_codigo");
                
                String procedimentoNome = siaDataEnricher.enriquecerComProcedimento(procedimentoCodigo);
                
                return SiaPaRelatorioTopProcedimentosResponse.ItemTopProcedimento.builder()
                        .procedimentoCodigo(procedimentoCodigo)
                        .procedimentoNome(procedimentoNome)
                        .quantidadeProduzidaTotal(getLong(rs.getObject("quantidade_produzida_total")))
                        .valorAprovadoTotal((BigDecimal) rs.getObject("valor_aprovado_total"))
                        .estabelecimentosUnicos(getLong(rs.getObject("estabelecimentos_unicos")))
                        .municipiosUnicos(getLong(rs.getObject("municipios_unicos")))
                        .build();
            },
            params
        );

        return SiaPaRelatorioTopProcedimentosResponse.builder()
                .uf(ufEfetiva)
                .competencia(competencia)
                .limit(lim)
                .itens(itens)
                .build();
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "siaRelatorioTopCid",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public SiaPaRelatorioTopCidResponse gerarTopCid(String uf, String competencia, Integer limit) {
        String ufEfetiva = resolverUf(uf);
        if (!StringUtils.hasText(ufEfetiva)) {
            throw new BadRequestException("uf é obrigatória (2 letras) ou deve ser inferível pelo tenant");
        }
        if (!StringUtils.hasText(competencia)) {
            throw new BadRequestException("competencia é obrigatória (AAAAMM)");
        }
        int lim = limit != null && limit > 0 ? Math.min(limit, 100) : 10;

        UUID tenantId = tenantService.validarTenantAtual();
        List<String> cnesList = tenantFilterHelper.obterCnesDoTenant(tenantId);
        
        if (cnesList.isEmpty()) {
            log.warn("Nenhum CNES encontrado para o tenant {}. Retornando relatório vazio.", tenantId);
            return SiaPaRelatorioTopCidResponse.builder()
                    .uf(ufEfetiva)
                    .competencia(competencia)
                    .limit(lim)
                    .itens(List.of())
                    .build();
        }

        
        String placeholders = String.join(",", java.util.Collections.nCopies(cnesList.size(), "?"));
        Object[] params = new Object[3 + cnesList.size()];
        params[0] = Objects.requireNonNull(ufEfetiva, "UF não pode ser null");
        params[1] = Objects.requireNonNull(competencia, "Competência não pode ser null");
        params[2] = lim;
        for (int i = 0; i < cnesList.size(); i++) {
            params[3 + i] = Objects.requireNonNull(cnesList.get(i), "CNES não pode ser null");
        }

        
        List<SiaPaRelatorioTopCidResponse.ItemTopCid> itens;
        try {
            String querySql = String.format("""
                        SELECT
                            mv.cid_principal_codigo,
                            SUM(mv.quantidade_produzida_total) AS quantidade_produzida_total,
                            SUM(mv.valor_aprovado_total) AS valor_aprovado_total,
                            COALESCE(
                                cid_sub.descricao, 
                                cid_cat.descricao,
                                CASE WHEN mv.cid_principal_codigo = '0000' THEN 'Sem diagnóstico principal' ELSE NULL END
                            ) AS cid_descricao
                        FROM public.mv_sia_pa_top_cid mv
                        LEFT JOIN public.cid10_subcategorias cid_sub ON cid_sub.subcat = mv.cid_principal_codigo
                        LEFT JOIN public.cid10_categorias cid_cat ON cid_cat.cat = mv.cid_principal_codigo
                        WHERE mv.uf = ? AND mv.competencia = ? AND mv.cid_principal_codigo IN (
                            SELECT DISTINCT cid_principal_codigo 
                            FROM public.sia_pa 
                            WHERE uf = ? AND competencia = ? AND codigo_cnes IN (%s) AND cid_principal_codigo IS NOT NULL
                        )
                        GROUP BY mv.cid_principal_codigo, cid_sub.descricao, cid_cat.descricao
                        ORDER BY quantidade_produzida_total DESC NULLS LAST
                        LIMIT ?
                        """, placeholders);
            Object[] paramsView = new Object[4 + cnesList.size()];
            paramsView[0] = Objects.requireNonNull(ufEfetiva, "UF não pode ser null");
            paramsView[1] = Objects.requireNonNull(competencia, "Competência não pode ser null");
            paramsView[2] = Objects.requireNonNull(ufEfetiva, "UF não pode ser null");
            paramsView[3] = Objects.requireNonNull(competencia, "Competência não pode ser null");
            for (int i = 0; i < cnesList.size(); i++) {
                paramsView[4 + i] = Objects.requireNonNull(cnesList.get(i), "CNES não pode ser null");
            }
            paramsView[4 + cnesList.size()] = lim;
            
            itens = jdbcTemplate.query(
                Objects.requireNonNull(querySql, "Query SQL não pode ser null"),
                (rs, rowNum) -> {
                    String cidCodigo = rs.getString("cid_principal_codigo");
                    String cidDescricaoFromDb = rs.getString("cid_descricao");
                    
                    String cidDescricao = cidDescricaoFromDb != null && !cidDescricaoFromDb.isEmpty() 
                            ? cidDescricaoFromDb 
                            : siaDataEnricher.enriquecerComCid(cidCodigo);
                    
                    return SiaPaRelatorioTopCidResponse.ItemTopCid.builder()
                            .cidPrincipalCodigo(cidCodigo)
                            .cidDescricao(cidDescricao != null ? cidDescricao : cidCodigo)
                            .quantidadeProduzidaTotal(getLong(rs.getObject("quantidade_produzida_total")))
                            .valorAprovadoTotal((BigDecimal) rs.getObject("valor_aprovado_total"))
                            .topProcedimentoCodigo(null)
                            .topProcedimentoTotal(null)
                            .topMunicipioUfmunCodigo(null)
                            .topMunicipioTotal(null)
                            .build();
                },
                paramsView
            );
        } catch (Exception e) {
            
            log.debug("View materializada não disponível, usando tabela original: {}", e.getMessage());
            String querySql = String.format("""
                        SELECT
                            s.cid_principal_codigo,
                            COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
                            COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total,
                            COALESCE(
                                cid_sub.descricao, 
                                cid_cat.descricao,
                                CASE WHEN s.cid_principal_codigo = '0000' THEN 'Sem diagnóstico principal' ELSE NULL END
                            ) AS cid_descricao
                        FROM public.sia_pa s
                        LEFT JOIN public.cid10_subcategorias cid_sub ON cid_sub.subcat = s.cid_principal_codigo
                        LEFT JOIN public.cid10_categorias cid_cat ON cid_cat.cat = s.cid_principal_codigo
                        WHERE s.uf = ? AND s.competencia = ? AND s.codigo_cnes IN (%s)
                          AND s.cid_principal_codigo IS NOT NULL
                        GROUP BY s.cid_principal_codigo, cid_sub.descricao, cid_cat.descricao
                        ORDER BY quantidade_produzida_total DESC NULLS LAST
                        LIMIT ?
                        """, placeholders);
            itens = jdbcTemplate.query(
                Objects.requireNonNull(querySql, "Query SQL não pode ser null"),
                (rs, rowNum) -> {
                    String cidCodigo = rs.getString("cid_principal_codigo");
                    String cidDescricaoFromDb = rs.getString("cid_descricao");
                    
                    String cidDescricao = cidDescricaoFromDb != null && !cidDescricaoFromDb.isEmpty() 
                            ? cidDescricaoFromDb 
                            : siaDataEnricher.enriquecerComCid(cidCodigo);
                    
                    return SiaPaRelatorioTopCidResponse.ItemTopCid.builder()
                            .cidPrincipalCodigo(cidCodigo)
                            .cidDescricao(cidDescricao != null ? cidDescricao : cidCodigo)
                            .quantidadeProduzidaTotal(getLong(rs.getObject("quantidade_produzida_total")))
                            .valorAprovadoTotal((BigDecimal) rs.getObject("valor_aprovado_total"))
                            .topProcedimentoCodigo(null)
                            .topProcedimentoTotal(null)
                            .topMunicipioUfmunCodigo(null)
                            .topMunicipioTotal(null)
                            .build();
                },
                params
            );
        }

        
        String placeholdersTop = String.join(",", java.util.Collections.nCopies(cnesList.size(), "?"));
        for (SiaPaRelatorioTopCidResponse.ItemTopCid item : itens) {
            Object[] paramsTopProc = new Object[3 + cnesList.size()];
            paramsTopProc[0] = ufEfetiva;
            paramsTopProc[1] = competencia;
            paramsTopProc[2] = item.getCidPrincipalCodigo();
            for (int i = 0; i < cnesList.size(); i++) {
                paramsTopProc[3 + i] = cnesList.get(i);
            }
            
            try {
                String queryTopProcSql = String.format("""
                        SELECT procedimento_codigo
                        FROM public.sia_pa
                        WHERE uf = ? AND competencia = ? AND cid_principal_codigo = ? AND codigo_cnes IN (%s)
                        GROUP BY procedimento_codigo
                        ORDER BY COUNT(*) DESC
                        LIMIT 1
                        """, placeholdersTop);
                String topProc = jdbcTemplate.queryForObject(
                    Objects.requireNonNull(queryTopProcSql, "Query SQL não pode ser null"), 
                    String.class, 
                    Objects.requireNonNull(paramsTopProc, "Parâmetros não podem ser null"));
                if (topProc != null) {
                    Object[] paramsTopProcTotal = new Object[4 + cnesList.size()];
                    paramsTopProcTotal[0] = Objects.requireNonNull(ufEfetiva, "UF não pode ser null");
                    paramsTopProcTotal[1] = Objects.requireNonNull(competencia, "Competência não pode ser null");
                    paramsTopProcTotal[2] = Objects.requireNonNull(item.getCidPrincipalCodigo(), "CID não pode ser null");
                    paramsTopProcTotal[3] = Objects.requireNonNull(topProc, "Código procedimento não pode ser null");
                    for (int i = 0; i < cnesList.size(); i++) {
                        paramsTopProcTotal[4 + i] = Objects.requireNonNull(cnesList.get(i), "CNES não pode ser null");
                    }
                    String queryTopProcTotal = String.format("""
                            SELECT COUNT(*)
                            FROM public.sia_pa
                            WHERE uf = ? AND competencia = ? AND cid_principal_codigo = ? AND procedimento_codigo = ? AND codigo_cnes IN (%s)
                            """, placeholdersTop);
                    Long topProcTotal = jdbcTemplate.queryForObject(
                        Objects.requireNonNull(queryTopProcTotal, "Query SQL não pode ser null"), 
                        Long.class, paramsTopProcTotal);
                    item.setTopProcedimentoCodigo(topProc);
                    item.setTopProcedimentoTotal(topProcTotal);
                }
            } catch (Exception e) {
                log.debug("Erro ao calcular top procedimento para CID {}: {}", item.getCidPrincipalCodigo(), e.getMessage());
            }
            
            try {
                Object[] paramsTopMun = new Object[3 + cnesList.size()];
                paramsTopMun[0] = Objects.requireNonNull(ufEfetiva, "UF não pode ser null");
                paramsTopMun[1] = Objects.requireNonNull(competencia, "Competência não pode ser null");
                paramsTopMun[2] = Objects.requireNonNull(item.getCidPrincipalCodigo(), "CID não pode ser null");
                for (int i = 0; i < cnesList.size(); i++) {
                    paramsTopMun[3 + i] = Objects.requireNonNull(cnesList.get(i), "CNES não pode ser null");
                }
                String queryTopMun = String.format("""
                        SELECT municipio_ufmun_codigo
                        FROM public.sia_pa
                        WHERE uf = ? AND competencia = ? AND cid_principal_codigo = ? AND municipio_ufmun_codigo IS NOT NULL AND codigo_cnes IN (%s)
                        GROUP BY municipio_ufmun_codigo
                        ORDER BY COUNT(*) DESC
                        LIMIT 1
                        """, placeholdersTop);
                String topMun = jdbcTemplate.queryForObject(
                    Objects.requireNonNull(queryTopMun, "Query SQL não pode ser null"), 
                    String.class, paramsTopMun);
                if (topMun != null) {
                    Object[] paramsTopMunTotal = new Object[4 + cnesList.size()];
                    paramsTopMunTotal[0] = Objects.requireNonNull(ufEfetiva, "UF não pode ser null");
                    paramsTopMunTotal[1] = Objects.requireNonNull(competencia, "Competência não pode ser null");
                    paramsTopMunTotal[2] = Objects.requireNonNull(item.getCidPrincipalCodigo(), "CID não pode ser null");
                    paramsTopMunTotal[3] = Objects.requireNonNull(topMun, "Código município não pode ser null");
                    for (int i = 0; i < cnesList.size(); i++) {
                        paramsTopMunTotal[4 + i] = Objects.requireNonNull(cnesList.get(i), "CNES não pode ser null");
                    }
                    String queryTopMunTotal = String.format("""
                            SELECT COUNT(*)
                            FROM public.sia_pa
                            WHERE uf = ? AND competencia = ? AND cid_principal_codigo = ? AND municipio_ufmun_codigo = ? AND codigo_cnes IN (%s)
                            """, placeholdersTop);
                    Long topMunTotal = jdbcTemplate.queryForObject(
                        Objects.requireNonNull(queryTopMunTotal, "Query SQL não pode ser null"), 
                        Long.class, paramsTopMunTotal);
                    item.setTopMunicipioUfmunCodigo(topMun);
                    item.setTopMunicipioTotal(topMunTotal);
                }
            } catch (Exception e) {
                log.debug("Erro ao calcular top município para CID {}: {}", item.getCidPrincipalCodigo(), e.getMessage());
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

