package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.kpi.SiaPaKpiResponse;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.profissional.MedicosRepository;
import com.upsaude.service.api.cnes.CnesEstabelecimentoService;
import com.upsaude.service.api.sia.SiaPaKpiService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.relatorios.TenantFilterHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiaPaKpiServiceImpl implements SiaPaKpiService {

    private final JdbcTemplate jdbcTemplate;
    private final TenantService tenantService;
    private final CnesEstabelecimentoService cnesEstabelecimentoService;
    private final TenantFilterHelper tenantFilterHelper;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final MedicosRepository medicosRepository;

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "siaKpiGeral",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public SiaPaKpiResponse kpiGeral(String competencia, String uf) {
        String ufEfetiva = resolverUf(uf);
        validarParametrosObrigatorios(competencia, ufEfetiva);

        UUID tenantId = tenantService.validarTenantAtual();
        List<String> cnesList = tenantFilterHelper.obterCnesDoTenant(tenantId);
        
        if (cnesList.isEmpty()) {
            log.warn("Nenhum CNES encontrado para o tenant {}. Retornando KPIs zerados.", tenantId);
            return montarRespostaVazia(competencia, ufEfetiva);
        }

        try {
        
        String placeholders = String.join(",", cnesList.stream().map(c -> "?").toList());
        
        
        Map<String, Object> row;
        try {
            
            row = jdbcTemplate.queryForMap(String.format("""
                    SELECT
                        SUM(mv.total_registros) AS total_registros,
                        COUNT(DISTINCT mv.procedimentos_unicos) AS procedimentos_unicos,
                        COUNT(DISTINCT mv.codigo_cnes) AS estabelecimentos_unicos,
                        SUM(mv.quantidade_produzida_total) AS quantidade_produzida_total,
                        SUM(mv.quantidade_aprovada_total) AS quantidade_aprovada_total,
                        SUM(mv.valor_produzido_total) AS valor_produzido_total,
                        SUM(mv.valor_aprovado_total) AS valor_aprovado_total,
                        SUM(mv.registros_com_erro) AS registros_com_erro
                    FROM public.mv_sia_pa_producao_mensal mv
                    WHERE mv.competencia = ? AND mv.uf = ? AND mv.codigo_cnes IN (%s)
                    """, placeholders), 
                    prepararParametros(competencia, ufEfetiva, cnesList));
        } catch (Exception e) {
            
            log.debug("View materializada não disponível, usando tabela original: {}", e.getMessage());
            row = jdbcTemplate.queryForMap(String.format("""
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
                    WHERE s.competencia = ? AND s.uf = ? AND s.codigo_cnes IN (%s)
                    """, placeholders), 
                    prepararParametros(competencia, ufEfetiva, cnesList));
        }

        return montarResposta(competencia, ufEfetiva, row, true);
        } catch (DataAccessResourceFailureException e) {
            log.error("Erro de conexão ao executar query KPI geral - competencia: {}, uf: {}", competencia, ufEfetiva, e);
            
            
            Throwable cause = e.getCause();
            if (cause instanceof org.postgresql.util.PSQLException) {
                org.postgresql.util.PSQLException psqlEx = (org.postgresql.util.PSQLException) cause;
                if (psqlEx.getMessage() != null && psqlEx.getMessage().contains("I/O error")) {
                    throw new DataAccessResourceFailureException(
                            String.format("Timeout ou erro de conexão ao consultar dados do SIA-PA. A query pode estar demorando muito para processar. Competência: %s, UF: %s. " +
                                    "Tente novamente em alguns instantes ou verifique se há muitos registros para esta competência.", 
                                    competencia, ufEfetiva), e);
                }
            }
            
            throw new DataAccessResourceFailureException(
                    String.format("Erro ao consultar dados do SIA-PA. Verifique a conexão com o banco de dados. Competência: %s, UF: %s", 
                            competencia, ufEfetiva), e);
        } catch (Exception e) {
            log.error("Erro inesperado ao executar query KPI geral - competencia: {}, uf: {}", competencia, ufEfetiva, e);
            throw new RuntimeException(
                    String.format("Erro inesperado ao consultar dados do SIA-PA. Competência: %s, UF: %s", 
                            competencia, ufEfetiva), e);
        }
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "siaKpiEstabelecimento",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public SiaPaKpiResponse kpiPorEstabelecimento(String competencia, String uf, String codigoCnes) {
        String ufEfetiva = resolverUf(uf);
        validarParametrosObrigatorios(competencia, ufEfetiva);
        if (!StringUtils.hasText(codigoCnes)) {
            throw new BadRequestException("codigoCnes é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        
        
        if (!tenantFilterHelper.validarCnesPertenceAoTenant(codigoCnes, tenantId)) {
            throw new BadRequestException("CNES não pertence ao tenant do usuário autenticado");
        }

        try {
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

        SiaPaKpiResponse response = montarResposta(competencia, ufEfetiva, row, false);
        
        
        try {
            var estabelecimento = cnesEstabelecimentoService.buscarEstabelecimentoNoCnes(codigoCnes);
            response.setEstabelecimento(estabelecimento);
            log.debug("Dados do estabelecimento CNES {} incluídos na resposta KPI", codigoCnes);
        } catch (Exception e) {
            log.warn("Não foi possível buscar dados do estabelecimento CNES {}: {}", codigoCnes, e.getMessage());
            
        }
        
        return response;
        } catch (DataAccessResourceFailureException e) {
            log.error("Erro de conexão ao executar query KPI por estabelecimento - competencia: {}, uf: {}, cnes: {}", 
                    competencia, ufEfetiva, codigoCnes, e);
            
            Throwable cause = e.getCause();
            if (cause instanceof org.postgresql.util.PSQLException) {
                org.postgresql.util.PSQLException psqlEx = (org.postgresql.util.PSQLException) cause;
                if (psqlEx.getMessage() != null && psqlEx.getMessage().contains("I/O error")) {
                    throw new DataAccessResourceFailureException(
                            String.format("Timeout ou erro de conexão ao consultar dados do SIA-PA. Competência: %s, UF: %s, CNES: %s", 
                                    competencia, ufEfetiva, codigoCnes), e);
                }
            }
            
            throw new DataAccessResourceFailureException(
                    String.format("Erro ao consultar dados do SIA-PA. Competência: %s, UF: %s, CNES: %s", 
                            competencia, ufEfetiva, codigoCnes), e);
        } catch (Exception e) {
            log.error("Erro inesperado ao executar query KPI por estabelecimento - competencia: {}, uf: {}, cnes: {}", 
                    competencia, ufEfetiva, codigoCnes, e);
            throw new RuntimeException(
                    String.format("Erro inesperado ao consultar dados do SIA-PA. Competência: %s, UF: %s, CNES: %s", 
                            competencia, ufEfetiva, codigoCnes), e);
        }
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "siaKpiProcedimento",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public SiaPaKpiResponse kpiPorProcedimento(String competencia, String uf, String procedimentoCodigo) {
        String ufEfetiva = resolverUf(uf);
        validarParametrosObrigatorios(competencia, ufEfetiva);
        if (!StringUtils.hasText(procedimentoCodigo)) {
            throw new BadRequestException("procedimentoCodigo é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        List<String> cnesList = tenantFilterHelper.obterCnesDoTenant(tenantId);
        
        if (cnesList.isEmpty()) {
            log.warn("Nenhum CNES encontrado para o tenant {}. Retornando KPIs zerados.", tenantId);
            return montarRespostaVazia(competencia, ufEfetiva);
        }

        try {
        
        String placeholders = String.join(",", java.util.Collections.nCopies(cnesList.size(), "?"));
        
        Map<String, Object> row = jdbcTemplate.queryForMap(String.format("""
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
                WHERE s.competencia = ? AND s.uf = ? AND s.procedimento_codigo = ? AND s.codigo_cnes IN (%s)
                """, placeholders),
                prepararParametros(competencia, ufEfetiva, procedimentoCodigo, cnesList));

        return montarResposta(competencia, ufEfetiva, row, false);
        } catch (DataAccessResourceFailureException e) {
            log.error("Erro de conexão ao executar query KPI por procedimento - competencia: {}, uf: {}, procedimento: {}", 
                    competencia, ufEfetiva, procedimentoCodigo, e);
            
            Throwable cause = e.getCause();
            if (cause instanceof org.postgresql.util.PSQLException) {
                org.postgresql.util.PSQLException psqlEx = (org.postgresql.util.PSQLException) cause;
                if (psqlEx.getMessage() != null && psqlEx.getMessage().contains("I/O error")) {
                    throw new DataAccessResourceFailureException(
                            String.format("Timeout ou erro de conexão ao consultar dados do SIA-PA. Competência: %s, UF: %s, Procedimento: %s", 
                                    competencia, ufEfetiva, procedimentoCodigo), e);
                }
            }
            
            throw new DataAccessResourceFailureException(
                    String.format("Erro ao consultar dados do SIA-PA. Competência: %s, UF: %s, Procedimento: %s", 
                            competencia, ufEfetiva, procedimentoCodigo), e);
        } catch (Exception e) {
            log.error("Erro inesperado ao executar query KPI por procedimento - competencia: {}, uf: {}, procedimento: {}", 
                    competencia, ufEfetiva, procedimentoCodigo, e);
            throw new RuntimeException(
                    String.format("Erro inesperado ao consultar dados do SIA-PA. Competência: %s, UF: %s, Procedimento: %s", 
                            competencia, ufEfetiva, procedimentoCodigo), e);
        }
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
                    
                    String municipioSia6 = municipioIbgeLocal.length() >= 6 ? municipioIbgeLocal.substring(0, 6) : municipioIbgeLocal;

                    try {
                    Map<String, Object> rowMun = jdbcTemplate.queryForMap("""
                            SELECT COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total
                            FROM public.sia_pa s
                            WHERE s.competencia = ? AND s.uf = ? AND (s.municipio_gestao_codigo = ? OR s.municipio_ufmun_codigo = ?)
                            """, competencia, uf, municipioSia6, municipioSia6);

                    long qtdMun = toLong(rowMun.get("quantidade_produzida_total"));
                    producaoPerCapita = new BigDecimal(qtdMun)
                            .divide(new BigDecimal(populacao), 6, RoundingMode.HALF_UP);
                    } catch (DataAccessResourceFailureException e) {
                        log.debug("Erro de conexão ao calcular produção per capita - competencia: {}, uf: {}, municipio: {} - {}", 
                                competencia, uf, municipioSia6, e.getMessage());
                        
                    }
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
                .estabelecimento(null) 
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

    private Object[] prepararParametros(String competencia, String uf, List<String> cnesList) {
        Object[] params = new Object[2 + cnesList.size()];
        params[0] = competencia;
        params[1] = uf;
        for (int i = 0; i < cnesList.size(); i++) {
            params[2 + i] = cnesList.get(i);
        }
        return params;
    }

    private Object[] prepararParametros(String competencia, String uf, String procedimentoCodigo, List<String> cnesList) {
        Object[] params = new Object[3 + cnesList.size()];
        params[0] = competencia;
        params[1] = uf;
        params[2] = procedimentoCodigo;
        for (int i = 0; i < cnesList.size(); i++) {
            params[3 + i] = cnesList.get(i);
        }
        return params;
    }

    private SiaPaKpiResponse montarRespostaVazia(String competencia, String uf) {
        return SiaPaKpiResponse.builder()
                .competencia(competencia)
                .uf(uf)
                .totalRegistros(0L)
                .procedimentosUnicos(0L)
                .estabelecimentosUnicos(0L)
                .quantidadeProduzidaTotal(0L)
                .quantidadeAprovadaTotal(0L)
                .valorProduzidoTotal(BigDecimal.ZERO)
                .valorAprovadoTotal(BigDecimal.ZERO)
                .diferencaValorTotal(BigDecimal.ZERO)
                .registrosComErro(0L)
                .taxaErroRegistros(null)
                .taxaAprovacaoValor(null)
                .producaoPerCapita(null)
                .populacaoEstimada(null)
                .municipioIbge(null)
                .estabelecimento(null)
                .build();
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "siaKpiTenant",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public SiaPaKpiResponse kpiPorTenant(String competencia, String uf) {
        
        return kpiGeral(competencia, uf);
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "siaKpiPorEstabelecimentoId",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public SiaPaKpiResponse kpiPorEstabelecimentoId(UUID estabelecimentoId, String competencia, String uf) {
        String ufEfetiva = resolverUf(uf);
        validarParametrosObrigatorios(competencia, ufEfetiva);
        
        UUID tenantId = tenantService.validarTenantAtual();
        
        
        Estabelecimentos estabelecimento = estabelecimentosRepository.findByIdAndTenant(estabelecimentoId, tenantId)
                .orElseThrow(() -> new BadRequestException("Estabelecimento não encontrado ou não pertence ao tenant"));
        
        String codigoCnes = estabelecimento.getDadosIdentificacao() != null 
                ? estabelecimento.getDadosIdentificacao().getCnes() 
                : null;
        
        if (codigoCnes == null || codigoCnes.trim().isEmpty()) {
            throw new BadRequestException("Estabelecimento não possui CNES cadastrado");
        }
        
        return kpiPorEstabelecimento(competencia, ufEfetiva, codigoCnes);
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "siaKpiPorMedicoId",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public SiaPaKpiResponse kpiPorMedicoId(UUID medicoId, String competencia, String uf) {
        String ufEfetiva = resolverUf(uf);
        validarParametrosObrigatorios(competencia, ufEfetiva);
        
        UUID tenantId = tenantService.validarTenantAtual();
        
        
        Medicos medico = medicosRepository.findByIdAndTenant(medicoId, tenantId)
                .orElseThrow(() -> new BadRequestException("Médico não encontrado ou não pertence ao tenant"));
        
        
        String cns = medico.getCns();
        
        if (cns == null || cns.trim().isEmpty()) {
            log.warn("Médico {} não possui CNS cadastrado. Retornando KPIs zerados.", medicoId);
            return montarRespostaVazia(competencia, ufEfetiva);
        }
        
        
        List<String> cnesList = tenantFilterHelper.obterCnesDoTenant(tenantId);
        
        if (cnesList.isEmpty()) {
            log.warn("Nenhum CNES encontrado para o tenant {}. Retornando KPIs zerados.", tenantId);
            return montarRespostaVazia(competencia, ufEfetiva);
        }

        try {
        String placeholders = String.join(",", java.util.Collections.nCopies(cnesList.size(), "?"));
        
        Map<String, Object> row = jdbcTemplate.queryForMap(String.format("""
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
                WHERE s.competencia = ? AND s.uf = ? AND s.cns_profissional = ? AND s.codigo_cnes IN (%s)
                """, placeholders),
                prepararParametrosMedico(competencia, ufEfetiva, cns, cnesList));

        return montarResposta(competencia, ufEfetiva, row, false);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            log.debug("Nenhum registro SIA encontrado para médico CNS {} na competência {} e UF {}", cns, competencia, ufEfetiva);
            return montarRespostaVazia(competencia, ufEfetiva);
        } catch (Exception e) {
            log.error("Erro ao buscar KPIs por médico: {}", e.getMessage(), e);
            throw new com.upsaude.exception.InternalServerErrorException("Erro ao buscar KPIs do SIA para médico", e);
        }
    }

    private Object[] prepararParametrosMedico(String competencia, String uf, String cns, List<String> cnesList) {
        Object[] params = new Object[3 + cnesList.size()];
        params[0] = competencia;
        params[1] = uf;
        params[2] = cns;
        for (int i = 0; i < cnesList.size(); i++) {
            params[3 + i] = cnesList.get(i);
        }
        return params;
    }
}

