package com.upsaude.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PuericulturaDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private ProfissionaisSaudeDTO profissionalResponsavel;
    private EquipeSaudeDTO equipeSaude;
    private LocalDate dataNascimento;
    private BigDecimal pesoNascimento;
    private BigDecimal comprimentoNascimento;
    private BigDecimal perimetroCefalicoNascimento;
    private Integer apgar1Minuto;
    private Integer apgar5Minutos;
    private String tipoParto;
    private Integer idadeGestacionalNascimento;
    private Boolean prematuro;
    private String nomeMae;
    private String tipoSanguineoMae;
    private Integer numeroConsultasPreNatal;
    private String intercorrenciasGestacao;
    private Boolean aleitamentoMaternoExclusivo;
    private LocalDate dataInicioAlimentacaoComplementar;
    private LocalDate dataDesmame;
    private String tipoAleitamentoAtual;
    private Boolean testePezinhoRealizado;
    private LocalDate dataTestePezinho;
    private String resultadoTestePezinho;
    private Boolean testeOrelhinhaRealizado;
    private LocalDate dataTesteOrelhinha;
    private String resultadoTesteOrelhinha;
    private Boolean testeOlhinhoRealizado;
    private LocalDate dataTesteOlhinho;
    private String resultadoTesteOlhinho;
    private Boolean testeCoracaoRealizado;
    private LocalDate dataTesteCoracao;
    private String resultadoTesteCoracao;
    private Boolean testeLinguinhaRealizado;
    private LocalDate dataTesteLinguinha;
    private String resultadoTesteLinguinha;
    private LocalDate dataInicioAcompanhamento;
    private LocalDate dataEncerramento;
    private String motivoEncerramento;
    private String antecedentesFamiliares;
    private String alergiasConhecidas;
    private String doencasCronicas;
    private String observacoes;
}
