package com.upsaude.api.request.paciente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.validation.annotation.CelularValido;
import com.upsaude.validation.annotation.EmailValido;
import com.upsaude.validation.annotation.TelefoneValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de contato do paciente")
public class PacienteContatoRequest {

    private UUID paciente;

    @NotNull(message = "Tipo de contato é obrigatório")
    private TipoContatoEnum tipo;

    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @EmailValido
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @CelularValido
    @Size(max = 20, message = "Celular deve ter no máximo 20 caracteres")
    private String celular;

    @TelefoneValido
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;
}
