package com.upsaude.api.response.sistema.notificacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response do teste de envio de email via Brevo")
public class TesteEmailBrevoResponse {

    @Schema(description = "Indica se o email foi enviado com sucesso")
    private Boolean sucesso;

    @Schema(description = "MessageId retornado pelo Brevo (se enviado com sucesso)")
    private String messageId;

    @Schema(description = "Mensagem de resultado")
    private String mensagem;

    @Schema(description = "Erro (se houver)")
    private String erro;
}
