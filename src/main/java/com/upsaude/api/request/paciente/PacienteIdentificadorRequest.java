package com.upsaude.api.request.paciente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.enums.OrigemIdentificadorEnum;
import com.upsaude.enums.TipoIdentificadorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de identificador do paciente")
public class PacienteIdentificadorRequest {

    private UUID paciente;

    @NotNull(message = "Tipo de identificador é obrigatório")
    private TipoIdentificadorEnum tipo;

    @NotBlank(message = "Valor do identificador é obrigatório")
    @Size(max = 255, message = "Valor do identificador deve ter no máximo 255 caracteres")
    private String valor;

    private OrigemIdentificadorEnum origem;

    private Boolean validado;

    private LocalDate dataValidacao;

    private Boolean principal;

    private String observacoes;
}
