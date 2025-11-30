package com.upsaude.api.request;

import com.upsaude.enums.TipoProcedimentoEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogoProcedimentosRequest {
    @NotNull(message = "Tipo de procedimento é obrigatório")
    private TipoProcedimentoEnum tipoProcedimento;

    @NotNull(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    private String codigo;

    private String descricao;
    private Integer duracaoMinutos;
    private BigDecimal custoSugerido;
    private Boolean requerProfissionalEspecifico;
    
    @Size(max = 100, message = "Profissional requerido deve ter no máximo 100 caracteres")
    private String profissionalRequerido;
    
    private Boolean requerPreparacao;
    private String instrucoesPreparacao;
    private String observacoes;
    private UUID estabelecimentoId;
}

