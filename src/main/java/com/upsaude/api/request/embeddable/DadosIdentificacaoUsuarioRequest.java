package com.upsaude.api.request.embeddable;

import com.upsaude.validation.annotation.CPFValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Dados de identificação do usuário")
public class DadosIdentificacaoUsuarioRequest {

    @NotBlank(message = "Username é obrigatório")
    @Size(max = 100, message = "Username deve ter no máximo 100 caracteres")
    private String username;

    @NotNull(message = "{validation.cpf.obrigatorio}")
    @CPFValido
    private String cpf;
}
