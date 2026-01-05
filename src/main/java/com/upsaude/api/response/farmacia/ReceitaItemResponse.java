package com.upsaude.api.response.farmacia;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.util.converter.UnidadeMedidaEnumDeserializer;
import com.upsaude.util.converter.UnidadeMedidaEnumSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class ReceitaItemResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID receitaId;
    private UUID sigtapProcedimentoId;
    private String procedimentoCodigo;
    private String procedimentoNome;
    private Integer posicao;
    private BigDecimal quantidadePrescrita;
    private BigDecimal quantidadeJaDispensada;
    
    @JsonSerialize(using = UnidadeMedidaEnumSerializer.class)
    @JsonDeserialize(using = UnidadeMedidaEnumDeserializer.class)
    private UnidadeMedidaEnum unidadeMedida;
    
    private String posologia;
    private Integer duracaoTratamento;
    private String observacoes;
}

