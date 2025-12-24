package com.upsaude.api.request.alergia;
import com.upsaude.entity.sistema.multitenancy.Tenant;

import com.upsaude.entity.paciente.Paciente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Dados de alergias paciente simplificado")
public class AlergiasPacienteSimplificadoRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    @NotNull(message = "Tenant é obrigatório")
    private UUID tenant;

    @NotNull(message = "Alergia é obrigatória")
    private UUID alergia;
}
