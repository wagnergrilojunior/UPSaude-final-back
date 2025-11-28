package com.upsaude.api.request;

import com.upsaude.entity.embeddable.AnamneseAtendimento;
import com.upsaude.entity.embeddable.ClassificacaoRiscoAtendimento;
import com.upsaude.entity.embeddable.DiagnosticoAtendimento;
import com.upsaude.entity.embeddable.InformacoesAtendimento;
import com.upsaude.entity.embeddable.ProcedimentosRealizadosAtendimento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Classe de requisição para criação e atualização de Atendimento.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoRequest {

    @NotNull(message = "ID do estabelecimento é obrigatório")
    private UUID estabelecimentoId;

    @NotNull(message = "ID do paciente é obrigatório")
    private UUID pacienteId;

    @NotNull(message = "ID do profissional de saúde é obrigatório")
    private UUID profissionalId;

    private UUID especialidadeId;
    private UUID equipeSaudeId;
    private UUID convenioId;
    private UUID cidPrincipalId;

    @Valid
    @NotNull(message = "Informações do atendimento são obrigatórias")
    private InformacoesAtendimento informacoes;

    @Valid
    private AnamneseAtendimento anamnese;

    @Valid
    private DiagnosticoAtendimento diagnostico;

    @Valid
    private ProcedimentosRealizadosAtendimento procedimentosRealizados;

    @Valid
    private ClassificacaoRiscoAtendimento classificacaoRisco;

    private String anotacoes;
    private String observacoesInternas;
}
