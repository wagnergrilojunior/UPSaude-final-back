package com.upsaude.api.response.clinica.atendimento;

import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.saude_publica.planejamento.PreNatalResponse;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaPreNatalResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PreNatalResponse preNatal;
    private ProfissionaisSaudeResponse profissional;
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
