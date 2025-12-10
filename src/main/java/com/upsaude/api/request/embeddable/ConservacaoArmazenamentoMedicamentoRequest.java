package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class ConservacaoArmazenamentoMedicamentoRequest {
    @DecimalMin(value = "-100.00", message = "Temperatura mínima de conservação deve ser maior ou igual a -100°C")
    @DecimalMax(value = "100.00", message = "Temperatura máxima de conservação deve ser menor ou igual a 100°C")
    private BigDecimal temperaturaConservacaoMin;
    
    @DecimalMin(value = "-100.00", message = "Temperatura mínima de conservação deve ser maior ou igual a -100°C")
    @DecimalMax(value = "100.00", message = "Temperatura máxima de conservação deve ser menor ou igual a 100°C")
    private BigDecimal temperaturaConservacaoMax;
    
    @NotNull(message = "Proteger luz é obrigatório")
    @Builder.Default
    private Boolean protegerLuz = false;
    
    @NotNull(message = "Proteger umidade é obrigatório")
    @Builder.Default
    private Boolean protegerUmidade = false;
    
    @Size(max = 255, message = "Condições armazenamento deve ter no máximo 255 caracteres")
    private String condicoesArmazenamento;
    
    private Integer validadeAposAberturaDias;
    
    private String instrucoesConservacao;
}
