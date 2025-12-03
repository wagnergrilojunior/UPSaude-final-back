package com.upsaude.api.response;

import com.upsaude.enums.TipoCuidadoEnfermagemEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuidadosEnfermagemResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private ProfissionaisSaudeResponse profissional;
    private AtendimentoResponse atendimento;
    private TipoCuidadoEnfermagemEnum tipoCuidado;
    private String descricaoProcedimento;
    private OffsetDateTime dataHora;
    private Integer pressaoSistolica;
    private Integer pressaoDiastolica;
    private Integer frequenciaCardiaca;
    private Integer frequenciaRespiratoria;
    private BigDecimal temperatura;
    private Integer saturacaoOxigenio;
    private Integer glicemiaCapilar;
    private BigDecimal peso;
    private BigDecimal altura;
    private String localizacaoFerida;
    private String tipoFerida;
    private String tamanhoFerida;
    private String aspectoFerida;
    private Boolean secrecaoPresente;
    private String tipoSecrecao;
    private String materialUtilizado;
    private String medicamentoAdministrado;
    private String dose;
    private String viaAdministracao;
    private String localAplicacao;
    private String loteMedicamento;
    private String tipoTesteRapido;
    private String resultadoTeste;
    private String loteTeste;
    private String queixaPaciente;
    private String evolucao;
    private String intercorrencias;
    private Boolean reacaoAdversa;
    private String descricaoReacao;
    private String orientacoes;
    private Boolean necessitaRetorno;
    private OffsetDateTime dataRetorno;
    private String observacoes;
}
