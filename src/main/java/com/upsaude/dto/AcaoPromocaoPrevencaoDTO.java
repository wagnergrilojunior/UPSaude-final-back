package com.upsaude.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * DTO para transferência de dados de Ação de Promoção e Prevenção.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcaoPromocaoPrevencaoDTO {
    private UUID id;
    private UUID estabelecimentoId;
    private UUID profissionalResponsavelId;
    private UUID equipeSaudeId;
    private List<UUID> profissionaisParticipantesIds;
    
    // Tipo de ação
    private Integer tipoAcao;
    
    // Informações da ação
    private String nome;
    private String descricao;
    private String objetivos;
    private String justificativa;
    private String metodologia;
    
    // Período
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean acaoContinua;
    private String periodicidade;
    
    // Local
    private String local;
    private String abrangenciaTerritorial;
    
    // Público-alvo
    private String publicoAlvo;
    private Integer populacaoEstimada;
    private String criteriosInclusao;
    
    // Metas e indicadores
    private Integer metaCobertura;
    private Integer metaAtendimentos;
    private String indicadoresAcompanhamento;
    
    // Resultados
    private Integer numeroAtendimentosRealizados;
    private Integer coberturaAlcancada;
    private String resultadosAlcancados;
    private String dificuldadesEncontradas;
    private String licoesAprendidas;
    
    // Recursos
    private String recursosNecessarios;
    private String recursosUtilizados;
    private String parcerias;
    
    // Status
    private String statusAcao;
    private LocalDate dataInicioExecucao;
    private LocalDate dataConclusao;
    
    private String observacoes;
    private Boolean active;
}

