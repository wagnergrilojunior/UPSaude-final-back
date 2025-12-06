package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ClassificacaoRiscoAtendimentoRequest {
    @Size(max = 50, message = "Classificação de risco deve ter no máximo 50 caracteres")
    private String classificacaoRisco;
    
    @Size(max = 50, message = "Prioridade deve ter no máximo 50 caracteres")
    private String prioridade;
    
    @Size(max = 50, message = "Gravidade deve ter no máximo 50 caracteres")
    private String gravidade;
    
    @NotNull(message = "Necessita observação é obrigatório")
    @Builder.Default
    private Boolean necessitaObservacao = false;
    
    @NotNull(message = "Necessita internação é obrigatório")
    @Builder.Default
    private Boolean necessitaInternacao = false;
}
