package com.upsaude.api.request.atendimento;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de consulta pre natal")
public class ConsultaPreNatalRequest {
    private UUID preNatal;
    private UUID profissional;
    private OffsetDateTime dataConsulta;
    private Integer numeroConsulta;
    private Integer idadeGestacionalSemanas;
    private Integer idadeGestacionalDias;
    private BigDecimal peso;
    private Integer pressaoArterialSistolica;
    private Integer pressaoArterialDiastolica;
    private Boolean edema;
    private String edemaLocalizacao;
    private BigDecimal alturaUterina;
    private Integer bcf;
    private Boolean movimentosFetais;
    private String apresentacaoFetal;
    private String posicaoFetal;
    private String queixaPrincipal;
    private Boolean nauseasVomitos;
    private Boolean sangramento;
    private Boolean contracaoUterina;
    private Boolean perdaLiquido;
    private Boolean cefaleia;
    private Boolean epigastralgia;
    private Boolean disturbiosVisuais;
    private String examesSolicitados;
    private String resultadosExames;
    private Boolean suplementacaoAcidoFolico;
    private Boolean suplementacaoSulfatoFerroso;
    private String medicamentosPrescritos;
    private String orientacoes;
    private String encaminhamentos;
    private OffsetDateTime dataProximaConsulta;
    private String observacoes;
}
