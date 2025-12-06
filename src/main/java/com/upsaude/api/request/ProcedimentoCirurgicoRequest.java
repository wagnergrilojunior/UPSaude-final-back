package com.upsaude.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
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
public class ProcedimentoCirurgicoRequest {
    @NotNull(message = "Cirurgia é obrigatória")
    private UUID cirurgia;
    
    @NotBlank(message = "Descrição do procedimento é obrigatória")
    private String descricao;
    @Size(max = 50, message = "Código procedimento deve ter no máximo 50 caracteres")
    private String codigoProcedimento;
    
    @Size(max = 500, message = "Nome procedimento deve ter no máximo 500 caracteres")
    private String nomeProcedimento;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Builder.Default
    private Integer quantidade = 1;
    
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    
    @Size(max = 20, message = "Lado deve ter no máximo 20 caracteres")
    private String lado;
    private String observacoes;
}
