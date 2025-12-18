package com.upsaude.api.request.medicacao;

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
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Dados de medicação paciente")
public class MedicacaoPacienteRequest {
    private UUID paciente;
    private UUID medicacao;
    private String dose;
    @JsonDeserialize(using = FrequenciaMedicacaoEnumDeserializer.class)
    private FrequenciaMedicacaoEnum frequencia;

    @JsonDeserialize(using = ViaAdministracaoEnumDeserializer.class)
    private ViaAdministracaoEnum via;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean medicacaoAtiva;
    private String observacoes;
}
