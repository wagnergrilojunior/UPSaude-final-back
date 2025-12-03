package com.upsaude.api.request;

import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoRequest {
    private UUID paciente;
    private UUID profissional;
    private UUID agendamento;
    private UUID template;
    private TipoNotificacaoEnum tipoNotificacao;
    private CanalNotificacaoEnum canal;
    private String destinatario;
    private String assunto;
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
