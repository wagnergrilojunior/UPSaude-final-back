package com.upsaude.api.response.sistema.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardTenantResponse {

    private UUID tenantId;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    
    private Long totalAtendimentos;
    private Long totalConsultas;
    private Long totalAgendamentos;
    private Long totalPacientes;
    private Long totalEstabelecimentos;

    
    private List<EvolucaoTemporal> evolucaoTemporal;

    
    private List<ItemTopProcedimento> topProcedimentos;

    
    private List<ItemTopCid> topCids;

    
    private List<ItemTopMedico> topMedicos;

    
    private ComparacaoPeriodo comparacaoPeriodoAnterior;

    
    private Map<String, BigDecimal> indicadores;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvolucaoTemporal {
        private LocalDate data;
        private Long atendimentos;
        private Long consultas;
        private Long agendamentos;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemTopProcedimento {
        private String procedimentoCodigo;
        private String procedimentoNome;
        private Long quantidade;
        private BigDecimal valorTotal;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemTopCid {
        private String cidCodigo;
        private String cidDescricao;
        private Long quantidade;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemTopMedico {
        private UUID medicoId;
        private String medicoNome;
        private Long totalAtendimentos;
        private Long totalAgendamentos;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComparacaoPeriodo {
        private BigDecimal variacaoAtendimentos;
        private BigDecimal variacaoConsultas;
        private BigDecimal variacaoAgendamentos;
        private BigDecimal variacaoPacientes;
    }
}
