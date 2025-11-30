package com.upsaude.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Request para criação/atualização de Pré-Natal.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreNatalRequest {
    private UUID estabelecimentoId;
    private UUID pacienteId;
    private UUID profissionalResponsavelId;
    private UUID equipeSaudeId;
    
    // Dados obstétricos
    private LocalDate dataUltimaMenstruacao;
    private LocalDate dataProvavelParto;
    private Integer idadeGestacionalCadastro;
    private Integer gestacoesAnteriores;
    private Integer partos;
    private Integer abortos;
    private Integer filhosVivos;
    private Integer partosVaginais;
    private Integer cesareas;
    private Integer partosPrematuros;
    private Integer natimortos;
    
    // Classificação de risco (código do enum)
    private Integer classificacaoRisco;
    private String motivosAltoRisco;
    
    // Status (código do enum)
    private Integer statusPreNatal;
    private LocalDate dataInicioAcompanhamento;
    private LocalDate dataEncerramento;
    
    // Dados do parto
    private OffsetDateTime dataParto;
    private Integer idadeGestacionalParto;
    private String tipoParto;
    private String localParto;
    
    // Dados do recém-nascido
    private BigDecimal pesoNascimento;
    private BigDecimal comprimentoNascimento;
    private Integer apgar1Minuto;
    private Integer apgar5Minutos;
    private BigDecimal perimetroCefalico;
    
    // Informações de saúde
    private String tipoSanguineo;
    private String fatorRh;
    private BigDecimal pesoPreGestacional;
    private BigDecimal altura;
    private BigDecimal imcPreGestacional;
    
    // Exames
    private Boolean exameSifilisRealizado;
    private Boolean exameHivRealizado;
    private Boolean exameHepatiteBRealizado;
    private Boolean exameToxoplasmoseRealizado;
    private Boolean ultrassonografiaRealizada;
    private LocalDate dataPrimeiraUltrassonografia;
    
    // Vacinas
    private Boolean vacinaDtpaRealizada;
    private Boolean vacinaHepatiteBRealizada;
    private Boolean vacinaGripeRealizada;
    
    // Consultas
    private Integer numeroConsultasPreNatal;
    
    // Observações
    private String antecedentesFamiliares;
    private String antecedentesPessoais;
    private String observacoes;
    private String observacoesInternas;
}

