package com.upsaude.api.response.farmacia;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.upsaude.enums.TipoControleMedicamentoEnum;
import com.upsaude.util.converter.TipoControleMedicamentoEnumDeserializer;
import com.upsaude.util.converter.TipoControleMedicamentoEnumSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class ReceitaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID pacienteId;
    private String pacienteNome;
    private UUID consultaId;
    private UUID medicoId;
    private String medicoNome;
    private String numeroReceita;
    private LocalDate dataPrescricao;
    private LocalDate dataValidade;
    
    @JsonSerialize(using = TipoControleMedicamentoEnumSerializer.class)
    @JsonDeserialize(using = TipoControleMedicamentoEnumDeserializer.class)
    private TipoControleMedicamentoEnum tipoReceita;
    
    private String observacoes;
    
    @Builder.Default
    private List<ReceitaItemResponse> itens = new ArrayList<>();
}

