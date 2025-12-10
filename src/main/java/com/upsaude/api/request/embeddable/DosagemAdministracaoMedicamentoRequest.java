package com.upsaude.api.request.embeddable;

import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DosagemAdministracaoMedicamentoRequest {
    @NotBlank(message = "Dosagem é obrigatória")
    @Size(max = 50, message = "Dosagem deve ter no máximo 50 caracteres")
    private String dosagem;
    
    private UnidadeMedidaEnum unidadeMedida;
    
    private ViaAdministracaoEnum viaAdministracao;
    
    @DecimalMin(value = "0.00", message = "Concentração deve ser maior ou igual a 0")
    @DecimalMax(value = "99999.99", message = "Concentração deve ser menor ou igual a 99999.99")
    private BigDecimal concentracao;
    
    @Size(max = 50, message = "Unidade concentração deve ter no máximo 50 caracteres")
    private String unidadeConcentracao;
    
    @Size(max = 100, message = "Posologia padrão deve ter no máximo 100 caracteres")
    private String posologiaPadrao;
    
    @Size(max = 255, message = "Instruções de uso deve ter no máximo 255 caracteres")
    private String instrucoesUso;
}
