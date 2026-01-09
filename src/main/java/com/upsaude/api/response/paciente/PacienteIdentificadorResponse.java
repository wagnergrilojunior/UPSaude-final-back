package com.upsaude.api.response.paciente;

import com.upsaude.enums.OrigemIdentificadorEnum;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.util.converter.OrigemIdentificadorEnumDeserializer;
import com.upsaude.util.converter.OrigemIdentificadorEnumSerializer;
import com.upsaude.util.converter.TipoIdentificadorEnumDeserializer;
import com.upsaude.util.converter.TipoIdentificadorEnumSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
public class PacienteIdentificadorResponse {

    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    @JsonDeserialize(using = TipoIdentificadorEnumDeserializer.class)
    @JsonSerialize(using = TipoIdentificadorEnumSerializer.class)
    private TipoIdentificadorEnum tipo;

    private String valor;

    @JsonDeserialize(using = OrigemIdentificadorEnumDeserializer.class)
    @JsonSerialize(using = OrigemIdentificadorEnumSerializer.class)
    private OrigemIdentificadorEnum origem;

    private Boolean validado;
    private LocalDate dataValidacao;
    private Boolean principal;
    private String observacoes;
}
