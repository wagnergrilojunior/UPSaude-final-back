package com.upsaude.api.response.paciente;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.OrigemObitoEnum;
import com.upsaude.util.converter.OrigemObitoEnumDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteObitoResponse {

    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private LocalDate dataObito;
    private String causaObitoCid10;
    private OffsetDateTime dataRegistro;

    @JsonDeserialize(using = OrigemObitoEnumDeserializer.class)
    private OrigemObitoEnum origem;

    private String observacoes;
}
