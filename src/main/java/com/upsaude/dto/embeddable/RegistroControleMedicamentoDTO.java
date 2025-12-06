package com.upsaude.dto.embeddable;

import com.upsaude.enums.TipoControleMedicamentoEnum;
import java.time.LocalDate;
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
public class RegistroControleMedicamentoDTO {
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
