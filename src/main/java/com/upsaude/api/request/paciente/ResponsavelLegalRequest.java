package com.upsaude.api.request.paciente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoResponsavelEnum;
import com.upsaude.util.converter.TipoResponsavelEnumDeserializer;
import com.upsaude.validation.annotation.CPFValido;
import com.upsaude.validation.annotation.TelefoneValido;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Dados de responsavel legal")
public class ResponsavelLegalRequest {
    private UUID paciente;
    private UUID estabelecimento;
    private String nome;
    @CPFValido
    private String cpf;
    @TelefoneValido
    private String telefone;
    private String rg;
    private String cns;
    @JsonDeserialize(using = TipoResponsavelEnumDeserializer.class)
    private TipoResponsavelEnum tipoResponsavel;
    private Boolean autorizacaoResponsavel;
}
