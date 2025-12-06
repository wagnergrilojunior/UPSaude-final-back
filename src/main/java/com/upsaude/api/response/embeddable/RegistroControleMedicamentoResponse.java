package com.upsaude.api.response.embeddable;

import com.upsaude.enums.TipoControleMedicamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroControleMedicamentoResponse {
    private String registroAnvisa;
    private LocalDate dataRegistroAnvisa;
    private LocalDate dataValidadeRegistroAnvisa;
    private TipoControleMedicamentoEnum tipoControle;
    private Boolean receitaObrigatoria;
    private Boolean controlado;
    private Boolean usoContinuo;
    private Boolean medicamentoEspecial;
    private Boolean medicamentoExcepcional;
}
