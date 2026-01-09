package com.upsaude.api.response.paciente;

import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.util.converter.TipoContatoEnumDeserializer;
import com.upsaude.util.converter.TipoContatoEnumSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteContatoResponse {

    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    @JsonDeserialize(using = TipoContatoEnumDeserializer.class)
    @JsonSerialize(using = TipoContatoEnumSerializer.class)
    private TipoContatoEnum tipo;

    private String nome;
    private String email;
    private String celular;
    private String telefone;
}
