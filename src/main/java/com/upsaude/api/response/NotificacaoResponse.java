package com.upsaude.api.response;

import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
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
public class NotificacaoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosResponse estabelecimento;
    private PacienteResponse paciente;
    private ProfissionaisSaudeResponse profissional;
    private AgendamentoResponse agendamento;
    private TemplateNotificacaoResponse template;
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
