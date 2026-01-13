package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.kpi.SiaPaKpiResponse;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.api.sia.SiaPaKpiService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiaPaKpiServiceImpl implements SiaPaKpiService {

    private final JdbcTemplate jdbcTemplate;
    private final TenantService tenantService;

    @Override
    public SiaPaKpiResponse kpiGeral(String competencia, String uf) {
        String ufEfetiva = resolverUf(uf);
        validarParametrosObrigatorios(competencia, ufEfetiva);

        Map<String, Object> row = jdbcTemplate.queryForMap("""
                SELECT
                    COUNT(*) AS total_registros,
                    COUNT(DISTINCT s.procedimento_codigo) AS procedimentos_unicos,
                    COUNT(DISTINCT s.codigo_cnes) AS estabelecimentos_unicos,
                    COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
                    COALESCE(SUM(COALESCE(s.quantidade_aprovada, 0)), 0) AS quantidade_aprovada_total,
                    COALESCE(SUM(COALESCE(s.valor_produzido, 0)), 0) AS valor_produzido_total,
                    COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total,
                    COALESCE(SUM(CASE WHEN s.flag_erro IS NOT NULL AND s.flag_erro <> '0' THEN 1 ELSE 0 END), 0) AS registros_com_erro
                FROM public.sia_pa s
                WHERE s.competencia = ? AND s.uf = ?
                """, competencia, ufEfetiva);

        return montarResposta(competencia, ufEfetiva, row, true);
    }

    @Override
    public SiaPaKpiResponse kpiPorEstabelecimento(String competencia, String uf, String codigoCnes) {
        String ufEfetiva = resolverUf(uf);
        validarParametrosObrigatorios(competencia, ufEfetiva);
        if (!StringUtils.hasText(codigoCnes)) {
            throw new BadRequestException("codigoCnes é obrigatório");
        }

        Map<String, Object> row = jdbcTemplate.queryForMap("""
                SELECT
                    COUNT(*) AS total_registros,
                    COUNT(DISTINCT s.procedimento_codigo) AS procedimentos_unicos,
                    COUNT(DISTINCT s.codigo_cnes) AS estabelecimentos_unicos,
                    COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
                    COALESCE(SUM(COALESCE(s.quantidade_aprovada, 0)), 0) AS quantidade_aprovada_total,
                    COALESCE(SUM(COALESCE(s.valor_produzido, 0)), 0) AS valor_produzido_total,
                    COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total,
                    COALESCE(SUM(CASE WHEN s.flag_erro IS NOT NULL AND s.flag_erro <> '0' THEN 1 ELSE 0 END), 0) AS registros_com_erro
                FROM public.sia_pa s
                WHERE s.competencia = ? AND s.uf = ? AND s.codigo_cnes = ?
                """, competencia, ufEfetiva, codigoCnes);

        return montarResposta(competencia, ufEfetiva, row, false);
    }

    @Override
    public SiaPaKpiResponse kpiPorProcedimento(String competencia, String uf, String procedimentoCodigo) {
        String ufEfetiva = resolverUf(uf);
        validarParametrosObrigatorios(competencia, ufEfetiva);
        if (!StringUtils.hasText(procedimentoCodigo)) {
            throw new BadRequestException("procedimentoCodigo é obrigatório");
        }

        Map<String, Object> row = jdbcTemplate.queryForMap("""
                SELECT
                    COUNT(*) AS total_registros,
                    COUNT(DISTINCT s.procedimento_codigo) AS procedimentos_unicos,
                    COUNT(DISTINCT s.codigo_cnes) AS estabelecimentos_unicos,
                    COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
                    COALESCE(SUM(COALESCE(s.quantidade_aprovada, 0)), 0) AS quantidade_aprovada_total,
                    COALESCE(SUM(COALESCE(s.valor_produzido, 0)), 0) AS valor_produzido_total,
                    COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total,
                    COALESCE(SUM(CASE WHEN s.flag_erro IS NOT NULL AND s.flag_erro <> '0' THEN 1 ELSE 0 END), 0) AS registros_com_erro
                FROM public.sia_pa s
                WHERE s.competencia = ? AND s.uf = ? AND s.procedimento_codigo = ?
                """, competencia, ufEfetiva, procedimentoCodigo);

        return montarResposta(competencia, ufEfetiva, row, false);
    }

    private void validarParametrosObrigatorios(String competencia, String uf) {
        if (!StringUtils.hasText(competencia)) {
            throw new BadRequestException("competencia é obrigatória (AAAAMM)");
        }
        if (!StringUtils.hasText(uf)) {
            throw new BadRequestException("uf é obrigatória (2 letras)");
        }
    }

    private String resolverUf(String uf) {
        if (StringUtils.hasText(uf)) {
            return uf.trim().toUpperCase();
        }

        try {
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            Endereco endereco = tenant != null ? tenant.getEndereco() : null;
            if (endereco != null && endereco.getEstado() != null) {
                String ufEstado = StringUtils.hasText(endereco.getEstado().getSiglaIbge())
                        ? endereco.getEstado().getSiglaIbge()
                        : endereco.getEstado().getSigla();
                if (StringUtils.hasText(ufEstado)) {
                    return ufEstado.trim().toUpperCase();
                }
            }
        } catch (Exception e) {
            log.debug("Não foi possível resolver UF via tenant: {}", e.getMessage());
        }
        return null;
    }

    private SiaPaKpiResponse montarResposta(String competencia, String uf, Map<String, Object> row, boolean tentarPerCapita) {
        long totalRegistros = toLong(row.get("total_registros"));
        long procedimentosUnicos = toLong(row.get("procedimentos_unicos"));
        long estabelecimentosUnicos = toLong(row.get("estabelecimentos_unicos"));
        long qtdProd = toLong(row.get("quantidade_produzida_total"));
        long qtdAprov = toLong(row.get("quantidade_aprovada_total"));
        BigDecimal valorProd = toBigDecimal(row.get("valor_produzido_total"));
        BigDecimal valorAprov = toBigDecimal(row.get("valor_aprovado_total"));
        long registrosComErro = toLong(row.get("registros_com_erro"));

        BigDecimal taxaErro = null;
        if (totalRegistros > 0) {
            taxaErro = new BigDecimal(registrosComErro)
                    .divide(new BigDecimal(totalRegistros), 6, RoundingMode.HALF_UP);
        }

        BigDecimal taxaAprovacaoValor = null;
        if (valorProd != null && valorProd.compareTo(BigDecimal.ZERO) > 0) {
            taxaAprovacaoValor = valorAprov.divide(valorProd, 6, RoundingMode.HALF_UP);
        }

        BigDecimal diferenca = null;
        if (valorProd != null && valorAprov != null) {
            diferenca = valorProd.subtract(valorAprov);
        }

        BigDecimal producaoPerCapita = null;
        Integer populacao = null;
        String municipioIbge = null;

        if (tentarPerCapita) {
            try {
                Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
                Endereco endereco = tenant != null ? tenant.getEndereco() : null;
                if (endereco != null) {
                    municipioIbge = endereco.getCodigoIbgeMunicipio();
                }
                if (endereco != null && endereco.getCidade() != null && endereco.getCidade().getPopulacaoEstimada() != null) {
                    populacao = endereco.getCidade().getPopulacaoEstimada();
                }

                if (populacao != null && populacao > 0 && StringUtils.hasText(municipioIbge)) {
                    String municipioIbgeLocal = municipioIbge;
                    if (municipioIbgeLocal == null) {
                        // Segurança defensiva para análise estática (não deve ocorrer por conta do hasText acima)
                        return SiaPaKpiResponse.builder()
                                .competencia(competencia)
                                .uf(uf)
                                .totalRegistros(totalRegistros)
                                .procedimentosUnicos(procedimentosUnicos)
                                .estabelecimentosUnicos(estabelecimentosUnicos)
                                .quantidadeProduzidaTotal(qtdProd)
                                .quantidadeAprovadaTotal(qtdAprov)
                                .valorProduzidoTotal(valorProd)
                                .valorAprovadoTotal(valorAprov)
                                .diferencaValorTotal(diferenca)
                                .registrosComErro(registrosComErro)
                                .taxaErroRegistros(taxaErro)
                                .taxaAprovacaoValor(taxaAprovacaoValor)
                                .producaoPerCapita(null)
                                .populacaoEstimada(populacao)
                                .municipioIbge(municipioIbge)
                                .build();
                    }
                    // SIA usa frequentemente código de município com 6 dígitos; derivamos a versão 6.
                    String municipioSia6 = municipioIbgeLocal.length() >= 6 ? municipioIbgeLocal.substring(0, 6) : municipioIbgeLocal;

                    Map<String, Object> rowMun = jdbcTemplate.queryForMap("""
                            SELECT COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total
                            FROM public.sia_pa s
                            WHERE s.competencia = ? AND s.uf = ? AND (s.municipio_gestao_codigo = ? OR s.municipio_ufmun_codigo = ?)
                            """, competencia, uf, municipioSia6, municipioSia6);

                    long qtdMun = toLong(rowMun.get("quantidade_produzida_total"));
                    producaoPerCapita = new BigDecimal(qtdMun)
                            .divide(new BigDecimal(populacao), 6, RoundingMode.HALF_UP);
                }
            } catch (Exception e) {
                log.debug("Não foi possível calcular produção per capita: {}", e.getMessage());
            }
        }

        return SiaPaKpiResponse.builder()
                .competencia(competencia)
                .uf(uf)
                .totalRegistros(totalRegistros)
                .procedimentosUnicos(procedimentosUnicos)
                .estabelecimentosUnicos(estabelecimentosUnicos)
                .quantidadeProduzidaTotal(qtdProd)
                .quantidadeAprovadaTotal(qtdAprov)
                .valorProduzidoTotal(valorProd)
                .valorAprovadoTotal(valorAprov)
                .diferencaValorTotal(diferenca)
                .registrosComErro(registrosComErro)
                .taxaErroRegistros(taxaErro)
                .taxaAprovacaoValor(taxaAprovacaoValor)
                .producaoPerCapita(producaoPerCapita)
                .populacaoEstimada(populacao)
                .municipioIbge(municipioIbge)
                .build();
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

