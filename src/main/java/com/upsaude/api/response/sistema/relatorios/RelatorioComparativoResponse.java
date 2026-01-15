package com.upsaude.api.response.sistema.relatorios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Response DTO para relatórios comparativos.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioComparativoResponse {

    private TipoComparacao tipoComparacao;
    private LocalDate periodoAtualInicio;
    private LocalDate periodoAtualFim;
    private LocalDate periodoAnteriorInicio;
    private LocalDate periodoAnteriorFim;

    // Dados do período atual
    private DadosPeriodo periodoAtual;

    // Dados do período anterior
    private DadosPeriodo periodoAnterior;

    // Comparações calculadas
    private Comparacoes comparacoes;

    // Filtros aplicados (opcional)
    private UUID estabelecimentoId;
    private UUID medicoId;
    private UUID especialidadeId;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DadosPeriodo {
        private Long totalAtendimentos;
        private Long totalConsultas;
        private Long totalAgendamentos;
        private Long totalPacientes;
        private Long totalProcedimentos;
        private BigDecimal valorTotal;
        private Map<String, Long> atendimentosPorTipo;
        private Map<String, Long> atendimentosPorEspecialidade;
        private Map<String, BigDecimal> indicadores;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Comparacoes {
        // Variações percentuais
        private BigDecimal variacaoAtendimentos;
        private BigDecimal variacaoConsultas;
        private BigDecimal variacaoAgendamentos;
        private BigDecimal variacaoPacientes;
        private BigDecimal variacaoProcedimentos;
        private BigDecimal variacaoValor;

        // Diferenças absolutas
        private Long diferencaAtendimentos;
        private Long diferencaConsultas;
        private Long diferencaAgendamentos;
        private Long diferencaPacientes;
        private Long diferencaProcedimentos;
        private BigDecimal diferencaValor;

        // Tendências (crescimento, estabilidade, declínio)
        private Tendencia tendenciaAtendimentos;
        private Tendencia tendenciaConsultas;
        private Tendencia tendenciaAgendamentos;
        private Tendencia tendenciaValor;

        // Comparações por tipo/especialidade
        private List<ComparacaoItem> comparacaoPorTipo;
        private List<ComparacaoItem> comparacaoPorEspecialidade;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComparacaoItem {
        private String nome;
        private Long valorAtual;
        private Long valorAnterior;
        private BigDecimal variacaoPercentual;
        private Tendencia tendencia;
    }

    public enum TipoComparacao {
        PERIODO_TEMPORAL,
        ESTABELECIMENTO,
        MEDICO,
        ESPECIALIDADE
    }

    public enum Tendencia {
        CRESCIMENTO,
        ESTABILIDADE,
        DECLINIO
    }
}
