package com.upsaude.api.request.paciente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.enums.OrigemObitoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados de óbito do paciente")
public class PacienteObitoRequest {

    private UUID paciente;

    @NotNull(message = "Data de óbito é obrigatória")
    private LocalDate dataObito;

    @Size(max = 10, message = "Causa do óbito CID-10 deve ter no máximo 10 caracteres")
    private String causaObitoCid10;

    private OrigemObitoEnum origem;

    private String observacoes;
}

