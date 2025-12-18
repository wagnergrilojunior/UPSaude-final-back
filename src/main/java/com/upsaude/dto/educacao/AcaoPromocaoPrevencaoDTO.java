package com.upsaude.dto.educacao;

import com.upsaude.enums.TipoAcaoPromocaoSaudeEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcaoPromocaoPrevencaoDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private ProfissionaisSaudeDTO profissionalResponsavel;
    private EquipeSaudeDTO equipeSaude;
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
    private String observacoes;
}
