package com.upsaude.api.request.embeddable;

import com.upsaude.enums.TipoReacaoAlergicaEnum;
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
public class ReacoesAlergiaRequest {
    private TipoReacaoAlergicaEnum tipoReacaoPrincipal;
    
    private String reacoesComuns;
    
    private String reacoesGraves;
    
    private String sintomas;
    
    @Size(max = 100, message = "Tempo após exposição deve ter no máximo 100 caracteres")
    private String tempoAposExposicao;
}
