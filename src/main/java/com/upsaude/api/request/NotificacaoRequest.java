package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import com.upsaude.util.converter.CanalNotificacaoEnumDeserializer;
import com.upsaude.util.converter.TipoNotificacaoEnumDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
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
public class NotificacaoRequest {
    private UUID paciente;
    private UUID profissional;
    private UUID agendamento;
    private UUID template;

    @NotNull(message = "Tipo de notificação é obrigatório")
    @JsonDeserialize(using = TipoNotificacaoEnumDeserializer.class)
    private TipoNotificacaoEnum tipoNotificacao;

    @NotNull(message = "Canal de notificação é obrigatório")
    @JsonDeserialize(using = CanalNotificacaoEnumDeserializer.class)
    private CanalNotificacaoEnum canal;

    @NotBlank(message = "Destinatário é obrigatório")
    @Size(max = 255, message = "Destinatário deve ter no máximo 255 caracteres")
    private String destinatario;
    @Size(max = 500, message = "Assunto deve ter no máximo 500 caracteres")
    private String assunto;

    @NotBlank(message = "Mensagem é obrigatória")
    private String mensagem;
    private String statusEnvio;
    private OffsetDateTime dataEnvioPrevista;
    private OffsetDateTime dataEnvio;
    private OffsetDateTime dataLeitura;
    private Integer tentativasEnvio;
    private Integer maximoTentativas;
    private String erroEnvio;
    private String logEnvio;
    private String idExterno;
    private String parametrosJson;
    private UUID enviadoPor;
}
