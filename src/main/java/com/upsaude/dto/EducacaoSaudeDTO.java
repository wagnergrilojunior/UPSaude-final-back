package com.upsaude.dto;

import lombok.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO para transferência de dados de Educação em Saúde.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducacaoSaudeDTO {
    private UUID id;
    private UUID estabelecimentoId;
    private UUID profissionalResponsavelId;
    private UUID equipeSaudeId;
    private List<UUID> participantesIds;
    private List<UUID> profissionaisParticipantesIds;
    
    // Tipo de atividade
    private Integer tipoAtividade;
    
    // Informações da atividade
    private String titulo;
    private String descricao;
    private String tema;
    private String objetivos;
    private String metodologia;
    private String recursosUtilizados;
    
    // Local e horário
    private OffsetDateTime dataHoraInicio;
    private OffsetDateTime dataHoraFim;
    private Integer duracaoMinutos;
    private String local;
    private String endereco;
    
    // Público-alvo
    private String publicoAlvo;
    private Integer faixaEtariaInicio;
    private Integer faixaEtariaFim;
    
    // Participação
    private Integer numeroParticipantesPrevisto;
    private Integer numeroParticipantesPresente;
    private String listaPresencaExterna;
    
    // Avaliação
    private Boolean atividadeRealizada;
    private String avaliacao;
    private String pontosPositivos;
    private String pontosMelhoria;
    private String encaminhamentosRealizados;
    
    // Material de apoio
    private String materialDistribuido;
    private Integer quantidadeMaterialDistribuido;
    
    private String observacoes;
    private Boolean active;
}

