package com.upsaude.api.request;

import com.upsaude.enums.TipoEducacaoSaudeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducacaoSaudeRequest {
    @NotNull(message = "Profissional responsável é obrigatório")
    private UUID profissionalResponsavel;
    
    private UUID equipeSaude;
    
    @NotNull(message = "Tipo de atividade é obrigatório")
    private TipoEducacaoSaudeEnum tipoAtividade;
    
    @NotBlank(message = "Título é obrigatório")
    @Size(max = 255, message = "Título deve ter no máximo 255 caracteres")
    private String titulo;
    private String descricao;
    private String tema;
    private String objetivos;
    private String metodologia;
    private String recursosUtilizados;
    
    @NotNull(message = "Data e hora de início são obrigatórias")
    private OffsetDateTime dataHoraInicio;
    
    private OffsetDateTime dataHoraFim;
    
    @Min(value = 0, message = "Duração não pode ser negativa")
    private Integer duracaoMinutos;
    
    @Size(max = 255, message = "Local deve ter no máximo 255 caracteres")
    private String local;
    
    @Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
    private String endereco;
    
    @Size(max = 255, message = "Público-alvo deve ter no máximo 255 caracteres")
    private String publicoAlvo;
    
    private Integer faixaEtariaInicio;
    private Integer faixaEtariaFim;
    
    @Min(value = 0, message = "Número previsto não pode ser negativo")
    private Integer numeroParticipantesPrevisto;
    
    @Min(value = 0, message = "Número de presentes não pode ser negativo")
    private Integer numeroParticipantesPresente;
    private String listaPresencaExterna;
    private String avaliacao;
    private String pontosPositivos;
    private String pontosMelhoria;
    private String encaminhamentosRealizados;
    private String materialDistribuido;
    @Min(value = 0, message = "Quantidade não pode ser negativa")
    private Integer quantidadeMaterialDistribuido;
    
    private String observacoes;
    
    @Builder.Default
    private List<UUID> participantes = new ArrayList<>();
    
    @Builder.Default
    private List<UUID> profissionaisParticipantes = new ArrayList<>();
}
