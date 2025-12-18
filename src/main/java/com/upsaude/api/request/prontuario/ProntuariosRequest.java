package com.upsaude.api.request.prontuario;

import com.upsaude.entity.paciente.Paciente;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de prontuarios")
public class ProntuariosRequest {
    @NotNull(message = "Paciente é obrigatório")
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    @Size(max = 50, message = "Tipo de registro deve ter no máximo 50 caracteres")
    private String tipoRegistro;

    @Size(max = 10000, message = "Conteúdo deve ter no máximo 10000 caracteres")
    private String conteudo;

    private UUID criadoPor;
}
