package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.FrequenciaMedicacaoEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import com.upsaude.util.converter.FrequenciaMedicacaoEnumDeserializer;
import com.upsaude.util.converter.ViaAdministracaoEnumDeserializer;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicacaoPacienteRequest {
    private UUID paciente;
    private UUID medicacao;
    private String dose;
    @JsonDeserialize(using = FrequenciaMedicacaoEnumDeserializer.class)
    private FrequenciaMedicacaoEnum frequencia;
    
    @JsonDeserialize(using = ViaAdministracaoEnumDeserializer.class)
    private ViaAdministracaoEnum via;
    private UUID cidRelacionado;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean medicacaoAtiva;
    private String observacoes;
}
