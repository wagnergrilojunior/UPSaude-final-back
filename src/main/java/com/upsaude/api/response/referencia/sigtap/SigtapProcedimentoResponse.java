package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "Resposta com informações de um procedimento SIGTAP")
public class SigtapProcedimentoResponse {
    @Schema(description = "Identificador único do procedimento", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Código oficial do procedimento SIGTAP (10 dígitos). Estrutura: 2 dígitos grupo + 2 subgrupo + 2 forma org + 4 procedimento", example = "0401010015")
    private String codigoOficial;

    @Schema(description = "Nome completo do procedimento", example = "ARITENOIDECTOMIA COM LARINGOFISSURA")
    private String nome;

    @Schema(description = "Competência inicial de vigência do procedimento (formato AAAAMM)", example = "202512")
    private String competenciaInicial;

    @Schema(description = "Competência final de vigência do procedimento (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;

    @Schema(description = "Código do grupo (2 dígitos) - primeiro nível da hierarquia SIGTAP", example = "04")
    private String grupoCodigo;

    @Schema(description = "Nome do grupo", example = "Procedimentos cirúrgicos")
    private String grupoNome;

    @Schema(description = "Código do subgrupo (2 dígitos) - segundo nível da hierarquia SIGTAP", example = "01")
    private String subgrupoCodigo;

    @Schema(description = "Nome do subgrupo", example = "Pequenas cirurgias e cirurgias de pele, tecido subcutâneo e mucosa")
    private String subgrupoNome;

    @Schema(description = "Código da forma de organização (2 dígitos) - terceiro nível da hierarquia SIGTAP", example = "01")
    private String formaOrganizacaoCodigo;

    @Schema(description = "Nome da forma de organização", example = "Pequenas cirurgias")
    private String formaOrganizacaoNome;

    @Schema(description = "Tipo de complexidade do procedimento. Valores: 1 (Baixa), 2 (Média), 3 (Alta)", example = "2")
    private String tipoComplexidade;

    @Schema(description = "Sexo permitido para o procedimento. Valores: M (Masculino), F (Feminino), I (Indiferente/Ambos)", example = "I")
    private String sexoPermitido;

    @Schema(description = "Idade mínima permitida em meses. 0 = sem restrição", example = "0")
    private Integer idadeMinima;

    @Schema(description = "Idade máxima permitida em meses. Valores altos (ex: 1571) indicam sem restrição prática", example = "1571")
    private Integer idadeMaxima;

    @Schema(description = "Média de dias de internação para o procedimento", example = "3")
    private Integer mediaDiasInternacao;

    @Schema(description = "Quantidade máxima de dias permitidos", example = "9999")
    private Integer quantidadeMaximaDias;

    @Schema(description = "Limite máximo de execuções do procedimento", example = "1")
    private Integer limiteMaximo;

    @Schema(description = "Quantidade de pontos do procedimento", example = "15")
    private Integer pontos;

    @Schema(description = "Informações do financiamento do procedimento")
    private SigtapFinanciamentoResponse financiamento;

    @Schema(description = "Informações da rubrica (sub-tipo de financiamento) do procedimento")
    private SigtapRubricaResponse rubrica;

    @Schema(description = "Valor do serviço hospitalar em reais", example = "664.72")
    private BigDecimal valorServicoHospitalar;

    @Schema(description = "Valor do serviço ambulatorial em reais", example = "0.00")
    private BigDecimal valorServicoAmbulatorial;

    @Schema(description = "Valor do serviço profissional em reais", example = "326.65")
    private BigDecimal valorServicoProfissional;

    @Schema(description = "Total ambulatorial (Serviço Ambulatorial + Serviço Profissional) em reais", example = "32.40")
    private BigDecimal totalAmbulatorial;

    @Schema(description = "Total hospitalar (Serviço Hospitalar + Serviço Profissional) em reais", example = "32.40")
    private BigDecimal totalHospitalar;

    @Schema(description = "Atributos complementares do procedimento (campos adicionais ou especiais)", example = "{}")
    private java.util.Map<String, Object> atributosComplementares;
}
