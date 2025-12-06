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
public class CalendarioVacinalRequest {
    @NotNull(message = "Calendário básico é obrigatório")
    @Builder.Default
    private Boolean calendarioBasico = false;
    
    @NotNull(message = "Calendário campanha é obrigatório")
    @Builder.Default
    private Boolean calendarioCampanha = false;
    
    @Size(max = 100, message = "Faixa etária calendário deve ter no máximo 100 caracteres")
    private String faixaEtariaCalendario;
    
    @Size(max = 50, message = "Situação epidemiológica deve ter no máximo 50 caracteres")
    private String situacaoEpidemiologica;
    
    @NotNull(message = "Obrigatória é obrigatório")
    @Builder.Default
    private Boolean obrigatoria = false;
}
