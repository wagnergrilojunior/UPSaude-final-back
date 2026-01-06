package com.upsaude.api.request.embeddable;

import com.upsaude.validation.annotation.CPFValido;
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
@Schema(description = "Dados do responsável legal do paciente")
public class ResponsavelLegalPacienteRequest {
    @Size(max = 255, message = "Nome do responsável deve ter no máximo 255 caracteres")
    private String nome;

    @CPFValido
    private String cpf;

    @TelefoneValido
    private String telefone;
}

