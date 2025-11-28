package com.upsaude.dto;

import com.upsaude.entity.embeddable.AnamneseAtendimento;
import com.upsaude.entity.embeddable.ClassificacaoRiscoAtendimento;
import com.upsaude.entity.embeddable.DiagnosticoAtendimento;
import com.upsaude.entity.embeddable.InformacoesAtendimento;
import com.upsaude.entity.embeddable.ProcedimentosRealizadosAtendimento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO (Data Transfer Object) para Atendimento.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoDTO {
    private UUID id;
    private UUID estabelecimentoId;
    private UUID pacienteId;
    private UUID profissionalId;
    private UUID especialidadeId;
    private UUID equipeSaudeId;
    private UUID convenioId;
    private UUID cidPrincipalId;
    
    private InformacoesAtendimento informacoes;
    private AnamneseAtendimento anamnese;
    private DiagnosticoAtendimento diagnostico;
    private ProcedimentosRealizadosAtendimento procedimentosRealizados;
    private ClassificacaoRiscoAtendimento classificacaoRisco;
    
    private String anotacoes;
    private String observacoesInternas;
    private Boolean active;
}
