package com.upsaude.api.request.embeddable;

import com.upsaude.validation.annotation.EmailValido;
import com.upsaude.validation.annotation.SiteValido;
import com.upsaude.validation.annotation.TelefoneValido;
import jakarta.validation.constraints.Size;
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
@Schema(description = "Dados de contato médico")
public class ContatoMedicoRequest {
    @TelefoneValido
    private String telefone;

    @TelefoneValido
    private String telefoneCelular;

    @TelefoneValido
    private String whatsapp;

    @EmailValido
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @EmailValido
    @Size(max = 255, message = "Email institucional deve ter no máximo 255 caracteres")
    private String emailInstitucional;

    @Size(max = 255, message = "Site deve ter no máximo 255 caracteres")
    @SiteValido
    private String site;
}
