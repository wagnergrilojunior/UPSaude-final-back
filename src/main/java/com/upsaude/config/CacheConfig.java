package com.upsaude.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;
import java.util.List;

/**
 * Configuração de cache para relatórios, dashboards e KPIs.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Lista de caches para relatórios e KPIs.
     */
    public static final List<String> RELATORIOS_CACHES = Arrays.asList(
            "siaKpiGeral",
            "siaKpiEstabelecimento",
            "siaKpiProcedimento",
            "siaKpiTenant",
            "siaKpiPorEstabelecimentoId",
            "siaKpiPorMedicoId",
            "siaRelatorioProducaoMensal",
            "siaRelatorioTopProcedimentos",
            "siaRelatorioTopCid",
            "dashboardTenant",
            "dashboardEstabelecimento",
            "dashboardMedico",
            "relatorioEstatisticas",
            "relatorioComparativo",
            "sigtapProcedimento",
            "cid10Subcategoria",
            "estabelecimentoPorCnes",
            "medicoPorCns",
            // Caches de entidades principais
            "tenants",
            "medicos",
            "pacientes",
            "profissionaisSaude",
            "estabelecimentos",
            "consultas",
            "atendimentos",
            "atendimento",
            "prontuarios",
            "agendamentos",
            "cidades",
            "estados",
            // Caches adicionais
            "vinculoprofissionalequipe",
            "endereco",
            "profissionalestabelecimento",
            "deficienciaspaciente",
            "alergias",
            "equipesSaude",
            "vacinas",
            "exames",
            "convenio",
            "dadosClinicosBasicos",
            "dadosSociodemograficos",
            "ciddoencas",
            "cirurgias",
            "fabricantesequipamento",
            "fabricantesmedicamento",
            "deficiencias",
            "departamentos",
            "faltas",
            "filaEspera",
            "perfisusuarios",
            "permissoes",
            "plantoes",
            "procedimentosCirurgicos",
            "procedimentosOdontologicos",
            "responsavellegal",
            "servicosEstabelecimento",
            "templatesNotificacao",
            "notificacoes",
            "configuracoesEstabelecimento",
            "conselhosprofissionais",
            "controleponto",
            "cuidadosenfermagem",
            "dispensacoesmedicamentos",
            "equipamentos",
            "equipamentosestabelecimento",
            "escalatrabalho",
            "checkinatendimento",
            "historicoclinico",
            "infraestruturaestabelecimento",
            "integracaogov",
            "lgpdconsentimento",
            "medicaoclinica",
            "logsauditoria",
            "receitasmedicas"
    );

    /**
     * Cache manager padrão para relatórios e KPIs.
     * Usa ConcurrentMapCacheManager como fallback quando Redis não está disponível.
     */
    @Bean
    @Primary
    public CacheManager relatoriosCacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setCacheNames(RELATORIOS_CACHES);
        return cacheManager;
    }
}
