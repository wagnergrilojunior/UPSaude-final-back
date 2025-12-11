package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoAcaoPromocaoSaudeEnum;
import com.upsaude.util.converter.TipoAcaoPromocaoSaudeEnumDeserializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
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
public class AcaoPromocaoPrevencaoRequest {
    @NotNull(message = "Profissional responsável é obrigatório")
    private UUID profissionalResponsavel;

    private UUID equipeSaude;

    @NotNull(message = "Tipo de ação é obrigatório")
    @JsonDeserialize(using = TipoAcaoPromocaoSaudeEnumDeserializer.class)
    private TipoAcaoPromocaoSaudeEnum tipoAcao;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;
    private String descricao;
    private String objetivos;
    private String justificativa;
    private String metodologia;

    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;

    private LocalDate dataFim;
    @Size(max = 100, message = "Periodicidade deve ter no máximo 100 caracteres")
    private String periodicidade;

    @Size(max = 255, message = "Local deve ter no máximo 255 caracteres")
    private String local;

    private String abrangenciaTerritorial;

    @Size(max = 255, message = "Público-alvo deve ter no máximo 255 caracteres")
    private String publicoAlvo;

    @Min(value = 0, message = "População estimada não pode ser negativa")
    private Integer populacaoEstimada;
    private String criteriosInclusao;
    private Integer metaCobertura;
    @Min(value = 0, message = "Meta de atendimentos não pode ser negativa")
    private Integer metaAtendimentos;

    private String indicadoresAcompanhamento;

    @Min(value = 0, message = "Número de atendimentos não pode ser negativo")
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
    private String observacoes;

    @Builder.Default
    private List<UUID> profissionaisParticipantes = new ArrayList<>();
}
