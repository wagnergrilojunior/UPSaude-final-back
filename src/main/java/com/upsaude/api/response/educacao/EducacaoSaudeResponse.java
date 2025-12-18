package com.upsaude.api.response.educacao;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.paciente.PacienteResponse;

import com.upsaude.enums.TipoEducacaoSaudeEnum;
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
public class EducacaoSaudeResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private ProfissionaisSaudeResponse profissionalResponsavel;
    private EquipeSaudeResponse equipeSaude;
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

    @Builder.Default
    private List<PacienteResponse> participantes = new ArrayList<>();

    @Builder.Default
    private List<ProfissionaisSaudeResponse> profissionaisParticipantes = new ArrayList<>();

    private String observacoes;
}
