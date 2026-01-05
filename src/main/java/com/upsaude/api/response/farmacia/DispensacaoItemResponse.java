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
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class DispensacaoItemResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID dispensacaoId;
    private UUID receitaItemId;
    private UUID sigtapProcedimentoId;
    private String procedimentoCodigo;
    private String procedimentoNome;
    private BigDecimal quantidadeDispensada;
    
    @JsonSerialize(using = UnidadeMedidaEnumSerializer.class)
    @JsonDeserialize(using = UnidadeMedidaEnumDeserializer.class)
    private UnidadeMedidaEnum unidadeMedida;
    
    private String lote;
    private LocalDate validadeLote;
    private String observacoes;
}

