package com.upsaude.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO para transferência de dados de Puericultura.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PuericulturaDTO {
    private UUID id;
    private UUID estabelecimentoId;
    private UUID pacienteId;
    private UUID profissionalResponsavelId;
    private UUID equipeSaudeId;
    
    // Dados do nascimento
    private LocalDate dataNascimento;
    private BigDecimal pesoNascimento;
    private BigDecimal comprimentoNascimento;
    private BigDecimal perimetroCefalicoNascimento;
    private Integer apgar1Minuto;
    private Integer apgar5Minutos;
    private String tipoParto;
    private Integer idadeGestacionalNascimento;
    private Boolean prematuro;
    
    // Dados da mãe
    private String nomeMae;
    private String tipoSanguineoMae;
    private Integer numeroConsultasPreNatal;
    private String intercorrenciasGestacao;
    
    // Aleitamento
    private Boolean aleitamentoMaternoExclusivo;
    private LocalDate dataInicioAlimentacaoComplementar;
    private LocalDate dataDesmame;
    private String tipoAleitamentoAtual;
    
    // Triagem neonatal
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
    
    // Status
    private LocalDate dataInicioAcompanhamento;
    private Boolean acompanhamentoAtivo;
    private LocalDate dataEncerramento;
    private String motivoEncerramento;
    
    // Informações adicionais
    private String antecedentesFamiliares;
    private String alergiasConhecidas;
    private String doencasCronicas;
    private String observacoes;
    
    private Boolean active;
}

