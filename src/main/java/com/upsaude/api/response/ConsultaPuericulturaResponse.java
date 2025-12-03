package com.upsaude.api.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaPuericulturaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PuericulturaResponse puericultura;
    private ProfissionaisSaudeResponse profissional;
    private OffsetDateTime dataConsulta;
    private Integer numeroConsulta;
    private Integer idadeMeses;
    private Integer idadeDias;
    private BigDecimal peso;
    private BigDecimal comprimentoEstatura;
    private BigDecimal perimetroCefalico;
    private BigDecimal perimetroToracico;
    private BigDecimal imc;
    private String pesoIdade;
    private String estaturaIdade;
    private String pesoEstatura;
    private String imcIdade;
    private String perimetroCefalicoIdade;
    private Boolean desenvolvimentoAdequado;
    private String marcosDesenvolvimento;
    private String alteracoesDesenvolvimento;
    private String tipoAleitamento;
    private String alimentacaoComplementar;
    private String dificuldadesAlimentacao;
    private Boolean vacinacaoEmDia;
    private String vacinasAtrasadas;
    private String vacinasAplicadasConsulta;
    private String queixaPrincipal;
    private String exameFisico;
    private String reflexos;
    private Boolean suplementacaoVitaminaA;
    private Boolean suplementacaoFerro;
    private Boolean suplementacaoVitaminaD;
    private String orientacoes;
    private String medicamentosPrescritos;
    private String encaminhamentos;
    private OffsetDateTime dataProximaConsulta;
    private String observacoes;
}
