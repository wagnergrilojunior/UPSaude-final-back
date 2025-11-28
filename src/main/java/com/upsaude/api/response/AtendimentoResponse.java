package com.upsaude.api.response;

import com.upsaude.entity.embeddable.AnamneseAtendimento;
import com.upsaude.entity.embeddable.ClassificacaoRiscoAtendimento;
import com.upsaude.entity.embeddable.DiagnosticoAtendimento;
import com.upsaude.entity.embeddable.InformacoesAtendimento;
import com.upsaude.entity.embeddable.ProcedimentosRealizadosAtendimento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Classe de resposta para Atendimento.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoResponse {
    private UUID id;
    private UUID estabelecimentoId;
    private String estabelecimentoNome;
    private UUID pacienteId;
    private String pacienteNome;
    private UUID profissionalId;
    private String profissionalNome;
    private UUID especialidadeId;
    private String especialidadeNome;
    private UUID equipeSaudeId;
    private String equipeSaudeNome;
    private UUID convenioId;
    private String convenioNome;
    private UUID cidPrincipalId;
    private String cidPrincipalCodigo;
    private String cidPrincipalDescricao;
    
    private InformacoesAtendimento informacoes;
    private AnamneseAtendimento anamnese;
    private DiagnosticoAtendimento diagnostico;
    private ProcedimentosRealizadosAtendimento procedimentosRealizados;
    private ClassificacaoRiscoAtendimento classificacaoRisco;
    
    private String anotacoes;
    private String observacoesInternas;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
}
