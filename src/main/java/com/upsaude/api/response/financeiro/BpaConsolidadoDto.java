package com.upsaude.api.response.financeiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO para dados consolidados do BPA.
 * Representa uma linha consolidada do arquivo BPA.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpaConsolidadoDto {
    
    // Identificação do estabelecimento
    private String cnes;
    
    // Competência
    private String competencia; // AAAAMM
    
    // Procedimento
    private String procedimentoCodigo; // SIGTAP
    private String procedimentoNome;
    
    // Diagnóstico
    private String cidPrincipal;
    
    // Quantidade e valores
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    
    // Data do atendimento
    private OffsetDateTime dataAtendimento;
    private LocalDate dataAtendimentoLocalDate;
    
    // Profissional
    private String cboProfissional;
    
    // Caráter do atendimento
    private String caraterAtendimento;
    
    // Paciente
    private Integer idade;
    private String sexo; // M/F/I
    private String municipioPacienteIbge;
    
    // Documento origem
    private String tipoDocumentoOrigem;
    private String numeroDocumentoOrigem;
    
    // Origem dos dados (para rastreabilidade)
    private String origemTipo; // DOCUMENTO_FATURAMENTO | RESERVA_CONSUMIDA | GUIA | AGENDAMENTO | ATENDIMENTO
    private UUID origemId;
    
    // Agendamento/Atendimento relacionados
    private UUID agendamentoId;
    private UUID atendimentoId;
    private UUID pacienteId;
}
