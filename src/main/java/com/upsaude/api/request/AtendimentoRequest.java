package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.api.request.embeddable.AnamneseAtendimentoRequest;
import com.upsaude.api.request.embeddable.ClassificacaoRiscoAtendimentoRequest;
import com.upsaude.api.request.embeddable.DiagnosticoAtendimentoRequest;
import com.upsaude.api.request.embeddable.InformacoesAtendimentoRequest;
import com.upsaude.api.request.embeddable.ProcedimentosRealizadosAtendimentoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AtendimentoRequest {
    // ========== CAMPOS OBRIGATÓRIOS ==========
    
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;
    
    @NotNull(message = "Profissional de saúde é obrigatório")
    private UUID profissional;
    
    // ========== IDs DE RELACIONAMENTO ==========
    
    private UUID especialidade;
    private UUID equipeSaude;
    private UUID convenio;
    private UUID cidPrincipal;
    
    // ========== OBJETOS EMBEDDABLE ==========
    
    @Valid
    private InformacoesAtendimentoRequest informacoes;
    
    @Valid
    private AnamneseAtendimentoRequest anamnese;
    
    @Valid
    private DiagnosticoAtendimentoRequest diagnostico;
    
    @Valid
    private ProcedimentosRealizadosAtendimentoRequest procedimentosRealizados;
    
    @Valid
    private ClassificacaoRiscoAtendimentoRequest classificacaoRisco;
    
    // ========== CAMPOS TEXTUAIS LONGOS ==========
    
    @Size(max = 1000, message = "Anotações deve ter no máximo 1000 caracteres")
    private String anotacoes;
    
    @Size(max = 1000, message = "Observações internas deve ter no máximo 1000 caracteres")
    private String observacoesInternas;
}
