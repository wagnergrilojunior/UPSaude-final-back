package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Informações do convênio do paciente")
public class InformacoesConvenioPacienteRequest {
    @Size(max = 50, message = "Número da carteirinha deve ter no máximo 50 caracteres")
    private String numeroCarteirinha;

    private LocalDate dataValidadeCarteirinha;
}

