package com.upsaude.api.response;

import com.upsaude.enums.TipoProcedimentoEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogoProcedimentosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
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
