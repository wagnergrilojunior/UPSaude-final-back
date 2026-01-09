package com.upsaude.api.request.embeddable;

import com.upsaude.validation.annotation.CelularValido;
import com.upsaude.validation.annotation.EmailValido;
import com.upsaude.validation.annotation.SiteValido;
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
@Schema(description = "Dados de contato do estabelecimento")
public class ContatoEstabelecimentoRequest {

    @TelefoneValido
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @CelularValido
    @Size(max = 20, message = "Celular deve ter no máximo 20 caracteres")
    private String celular;

    @TelefoneValido
    @Size(max = 20, message = "Fax deve ter no máximo 20 caracteres")
    private String fax;

    @EmailValido
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @SiteValido
    @Size(max = 500, message = "Site deve ter no máximo 500 caracteres")
    private String site;
}
