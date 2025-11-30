package com.upsaude.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Response para dados de Educação em Saúde.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducacaoSaudeResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    
    private UUID estabelecimentoId;
    private UUID profissionalResponsavelId;
    private UUID equipeSaudeId;
    private List<UUID> participantesIds;
    private List<UUID> profissionaisParticipantesIds;
    
    // Tipo de atividade
    private Integer tipoAtividade;
    private String tipoAtividadeDescricao;
    
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
}

