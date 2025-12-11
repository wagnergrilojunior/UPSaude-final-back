package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoResponsavelEnum;
import com.upsaude.util.converter.TipoResponsavelEnumDeserializer;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsavelLegalRequest {
    private UUID paciente;
    private String nome;
    private String cpf;
    private String telefone;
    @JsonDeserialize(using = TipoResponsavelEnumDeserializer.class)
    private TipoResponsavelEnum tipoResponsavel;
    private Boolean autorizacaoUsoDadosLGPD;
    private Boolean autorizacaoResponsavel;
}
