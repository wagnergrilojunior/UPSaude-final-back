package com.upsaude.api.request.embeddable;

import com.upsaude.enums.TipoControleMedicamentoEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class RegistroControleMedicamentoRequest {
    @Size(max = 50, message = "Registro ANVISA deve ter no máximo 50 caracteres")
    private String registroAnvisa;
    
    private LocalDate dataRegistroAnvisa;
    
    private LocalDate dataValidadeRegistroAnvisa;
    
    private TipoControleMedicamentoEnum tipoControle;
    
    @NotNull(message = "Receita obrigatória é obrigatório")
    @Builder.Default
    private Boolean receitaObrigatoria = false;
    
    @NotNull(message = "Controlado é obrigatório")
    @Builder.Default
    private Boolean controlado = false;
    
    @NotNull(message = "Uso contínuo é obrigatório")
    @Builder.Default
    private Boolean usoContinuo = false;
    
    @NotNull(message = "Medicamento especial é obrigatório")
    @Builder.Default
    private Boolean medicamentoEspecial = false;
    
    @NotNull(message = "Medicamento excepcional é obrigatório")
    @Builder.Default
    private Boolean medicamentoExcepcional = false;
}
