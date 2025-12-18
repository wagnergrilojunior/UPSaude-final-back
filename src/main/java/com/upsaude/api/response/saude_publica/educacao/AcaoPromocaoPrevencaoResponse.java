package com.upsaude.api.response.saude_publica.educacao;

import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.profissional.equipe.EquipeSaudeResponse;


import com.upsaude.enums.TipoAcaoPromocaoSaudeEnum;
import java.time.LocalDate;
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
public class AcaoPromocaoPrevencaoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private ProfissionaisSaudeResponse profissionalResponsavel;
    private EquipeSaudeResponse equipeSaude;
    private TipoAcaoPromocaoSaudeEnum tipoAcao;
    private String nome;
    private String descricao;
    private String objetivos;
    private String justificativa;
    private String metodologia;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String periodicidade;
    private String local;
    private String abrangenciaTerritorial;
    private String publicoAlvo;
    private Integer populacaoEstimada;
    private String criteriosInclusao;
    private Integer metaCobertura;
    private Integer metaAtendimentos;
    private String indicadoresAcompanhamento;
    private Integer numeroAtendimentosRealizados;
    private Integer coberturaAlcancada;
    private String resultadosAlcancados;
    private String dificuldadesEncontradas;
    private String licoesAprendidas;
    private String recursosNecessarios;
    private String recursosUtilizados;
    private String parcerias;
    private LocalDate dataInicioExecucao;
    private LocalDate dataConclusao;

    @Builder.Default
    private List<ProfissionaisSaudeResponse> profissionaisParticipantes = new ArrayList<>();

    private String observacoes;
}
