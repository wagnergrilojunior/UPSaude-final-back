package com.upsaude.dto.sistema.notificacao;

import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.agendamento.AgendamentoDTO;
import com.upsaude.dto.estabelecimento.EstabelecimentosDTO;
import com.upsaude.dto.paciente.PacienteDTO;
import com.upsaude.dto.profissional.ProfissionaisSaudeDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosDTO estabelecimento;
    private PacienteDTO paciente;
    private ProfissionaisSaudeDTO profissional;
    private AgendamentoDTO agendamento;
    private TemplateNotificacaoDTO template;
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
