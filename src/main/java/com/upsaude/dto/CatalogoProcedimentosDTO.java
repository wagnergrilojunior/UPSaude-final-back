package com.upsaude.dto;

import com.upsaude.enums.TipoProcedimentoEnum;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogoProcedimentosDTO {
    private UUID id;
    private TipoProcedimentoEnum tipoProcedimento;
    private String nome;
    private String codigo;
    private String descricao;
    private Integer duracaoMinutos;
    private BigDecimal custoSugerido;
    private Boolean requerProfissionalEspecifico;
    private String profissionalRequerido;
    private Boolean requerPreparacao;
    private String instrucoesPreparacao;
    private String observacoes;
    private Boolean active;
}

