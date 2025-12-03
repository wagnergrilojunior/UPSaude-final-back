package com.upsaude.api.request;

import com.upsaude.enums.TipoProcedimentoEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogoProcedimentosRequest {
    private TipoProcedimentoEnum tipoProcedimento;
    private String nome;
    private String codigo;
    private String descricao;
    private Integer duracaoMinutos;
    private BigDecimal custoSugerido;
    private String profissionalRequerido;
    private String instrucoesPreparacao;
    private String observacoes;
}
