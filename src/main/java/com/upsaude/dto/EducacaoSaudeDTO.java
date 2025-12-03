package com.upsaude.dto;

import com.upsaude.enums.TipoEducacaoSaudeEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducacaoSaudeDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private ProfissionaisSaudeDTO profissionalResponsavel;
    private EquipeSaudeDTO equipeSaude;
    private TipoEducacaoSaudeEnum tipoAtividade;
    private String titulo;
    private String descricao;
    private String tema;
    private String objetivos;
    private String metodologia;
    private String recursosUtilizados;
    private OffsetDateTime dataHoraInicio;
    private OffsetDateTime dataHoraFim;
    private Integer duracaoMinutos;
    private String local;
    private String endereco;
    private String publicoAlvo;
    private Integer faixaEtariaInicio;
    private Integer faixaEtariaFim;
    private Integer numeroParticipantesPrevisto;
    private Integer numeroParticipantesPresente;
    private String listaPresencaExterna;
    private String avaliacao;
    private String pontosPositivos;
    private String pontosMelhoria;
    private String encaminhamentosRealizados;
    private String materialDistribuido;
    private Integer quantidadeMaterialDistribuido;
    private String observacoes;
}
