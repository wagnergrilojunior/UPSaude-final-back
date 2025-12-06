package com.upsaude.api.request;

import com.upsaude.api.request.embeddable.AnamneseAtendimentoRequest;
import com.upsaude.api.request.embeddable.ClassificacaoRiscoAtendimentoRequest;
import com.upsaude.api.request.embeddable.DiagnosticoAtendimentoRequest;
import com.upsaude.api.request.embeddable.InformacoesAtendimentoRequest;
import com.upsaude.api.request.embeddable.ProcedimentosRealizadosAtendimentoRequest;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;
    
    @NotNull(message = "Profissional de saúde é obrigatório")
    private UUID profissional;
    
    private UUID especialidade;
    private UUID equipeSaude;
    private UUID convenio;
    
    private InformacoesAtendimentoRequest informacoes;
    private AnamneseAtendimentoRequest anamnese;
    private DiagnosticoAtendimentoRequest diagnostico;
    private ProcedimentosRealizadosAtendimentoRequest procedimentosRealizados;
    private ClassificacaoRiscoAtendimentoRequest classificacaoRisco;
    
    private UUID cidPrincipal;
    private String anotacoes;
    private String observacoesInternas;
}
