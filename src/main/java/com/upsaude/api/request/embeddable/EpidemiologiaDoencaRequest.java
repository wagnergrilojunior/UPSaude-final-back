package com.upsaude.api.request.embeddable;

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
public class EpidemiologiaDoencaRequest {
    private Integer incidenciaAnual;
    
    private Integer prevalencia;
    
    @Size(max = 100, message = "Faixa etária mais afetada deve ter no máximo 100 caracteres")
    private String faixaEtariaMaisAfetada;
    
    @Size(max = 1, message = "Sexo mais afetado deve ter no máximo 1 caractere")
    private String sexoMaisAfetado;
    
    private String fatoresRisco;
    
    @Size(max = 100, message = "Sazonalidade deve ter no máximo 100 caracteres")
    private String sazonalidade;
    
    private String distribuicaoGeografica;
}
