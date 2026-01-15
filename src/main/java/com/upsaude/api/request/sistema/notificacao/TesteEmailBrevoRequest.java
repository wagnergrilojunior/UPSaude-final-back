package com.upsaude.api.request.sistema.notificacao;

import com.upsaude.validation.annotation.EmailValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para teste de envio de email via Brevo")
public class TesteEmailBrevoRequest {

    @EmailValido
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    @Schema(description = "Email do destinatário", example = "teste@exemplo.com")
    private String email;

    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Schema(description = "Nome do destinatário (opcional)", example = "João Silva")
    private String nome;

    @Schema(description = "Parâmetros para o template do Brevo (opcional)", example = "{\"nome\": \"João\", \"data\": \"2026-01-15\"}")
    private Map<String, Object> parametros;
}
