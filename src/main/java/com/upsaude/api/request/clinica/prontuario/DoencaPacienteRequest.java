package com.upsaude.api.request.clinica.prontuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de doença do paciente")
public class DoencaPacienteRequest {

    @NotNull(message = "Prontuário é obrigatório")
    private UUID prontuario;

    @NotNull(message = "Diagnóstico CID-10 é obrigatório")
    private UUID diagnostico;

    @NotNull(message = "Data de diagnóstico é obrigatória")
    private LocalDate dataDiagnostico;

    @NotNull(message = "Status ativa é obrigatório")
    private Boolean ativa;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}

