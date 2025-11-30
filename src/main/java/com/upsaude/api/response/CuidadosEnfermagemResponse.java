package com.upsaude.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Response para dados de Cuidados de Enfermagem.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuidadosEnfermagemResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    
    private UUID estabelecimentoId;
    private UUID pacienteId;
    private UUID profissionalId;
    private UUID atendimentoId;
    
    // Tipo de cuidado
    private Integer tipoCuidado;
    private String tipoCuidadoDescricao;
    private String descricaoProcedimento;
    
    // Data e hora
    private OffsetDateTime dataHora;
    
    // Sinais vitais
    private Integer pressaoSistolica;
    private Integer pressaoDiastolica;
    private Integer frequenciaCardiaca;
    private Integer frequenciaRespiratoria;
    private BigDecimal temperatura;
    private Integer saturacaoOxigenio;
    private Integer glicemiaCapilar;
    private BigDecimal peso;
    private BigDecimal altura;
    
    // Dados do curativo
    private String localizacaoFerida;
    private String tipoFerida;
    private String tamanhoFerida;
    private String aspectoFerida;
    private Boolean secrecaoPresente;
    private String tipoSecrecao;
    private String materialUtilizado;
    
    // Medicação administrada
    private String medicamentoAdministrado;
    private String dose;
    private String viaAdministracao;
    private String localAplicacao;
    private String loteMedicamento;
    
    // Teste rápido
    private String tipoTesteRapido;
    private String resultadoTeste;
    private String loteTeste;
    
    // Evolução e resposta
    private String queixaPaciente;
    private String evolucao;
    private String intercorrencias;
    private Boolean reacaoAdversa;
    private String descricaoReacao;
    
    // Orientações
    private String orientacoes;
    
    // Agendamento retorno
    private Boolean necessitaRetorno;
    private OffsetDateTime dataRetorno;
    
    private String observacoes;
}

