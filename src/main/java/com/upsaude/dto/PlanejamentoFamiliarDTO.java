package com.upsaude.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO para transferência de dados de Planejamento Familiar.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanejamentoFamiliarDTO {
    private UUID id;
    private UUID estabelecimentoId;
    private UUID pacienteId;
    private UUID profissionalResponsavelId;
    private UUID equipeSaudeId;
    
    // Método contraceptivo
    private Integer metodoAtual;
    private LocalDate dataInicioMetodo;
    private String nomeComercialMetodo;
    private Integer metodoAnterior;
    private String motivoTrocaMetodo;
    
    // Histórico obstétrico
    private Integer numeroGestacoes;
    private Integer numeroPartos;
    private Integer numeroAbortos;
    private Integer numeroFilhosVivos;
    private LocalDate dataUltimoParto;
    private Boolean ultimaGestacaoPlanejada;
    
    // Desejo reprodutivo
    private Boolean desejaEngravidar;
    private String prazoDesejoGestacao;
    private Boolean desejaMetodoDefinitivo;
    
    // Critérios de elegibilidade
    private Boolean temContraindicacoes;
    private String contraindicacoes;
    private String doencasPreexistentes;
    private String medicamentosUso;
    private String alergias;
    
    // Dados menstruais
    private Boolean cicloMenstrualRegular;
    private Integer duracaoCiclo;
    private LocalDate dataUltimaMenstruacao;
    private Boolean dismenorreia;
    private Boolean sangramentoIrregular;
    
    // Acompanhamento
    private LocalDate dataInicioAcompanhamento;
    private Boolean acompanhamentoAtivo;
    private OffsetDateTime dataProximaConsulta;
    private LocalDate dataProximaDispensacao;
    
    // Para DIU
    private LocalDate dataInsercaoDiu;
    private LocalDate dataValidadeDiu;
    
    // Para métodos cirúrgicos
    private LocalDate dataCirurgia;
    private String localCirurgia;
    private Boolean documentacaoCompleta;
    private Boolean prazoMinimoCumprido;
    
    // Orientações
    private Boolean orientacaoMetodosRealizada;
    private LocalDate dataOrientacao;
    private Boolean consentimentoInformado;
    private LocalDate dataConsentimento;
    
    private String observacoes;
    private Boolean active;
}

