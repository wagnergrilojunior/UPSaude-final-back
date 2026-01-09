package com.upsaude.api.request.embeddable;

import com.upsaude.validation.annotation.CelularValido;
import com.upsaude.validation.annotation.EmailValido;
import com.upsaude.validation.annotation.TelefoneValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
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
@Schema(description = "Dados de contato do paciente")
public class ContatoPacienteRequest {
    @TelefoneValido
    private String telefone;

    @CelularValido
    private String celular;

    @EmailValido
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;
}

