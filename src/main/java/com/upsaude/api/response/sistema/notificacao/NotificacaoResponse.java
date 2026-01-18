package com.upsaude.api.response.sistema.notificacao;

import com.upsaude.api.response.sistema.notificacao.NotificacaoReferenciaResponse.AgendamentoRef;
import com.upsaude.api.response.sistema.notificacao.NotificacaoReferenciaResponse.EstabelecimentoRef;
import com.upsaude.api.response.sistema.notificacao.NotificacaoReferenciaResponse.PacienteRef;
import com.upsaude.api.response.sistema.notificacao.NotificacaoReferenciaResponse.ProfissionalRef;
import com.upsaude.api.response.sistema.notificacao.NotificacaoReferenciaResponse.TemplateRef;
import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response simplificado de notificação")
public class NotificacaoResponse {
    
    @Schema(description = "ID da notificação")
    private UUID id;
    
    @Schema(description = "Tipo de notificação")
    private TipoNotificacaoEnum tipoNotificacao;
    
    @Schema(description = "Canal de notificação (EMAIL, SMS, etc)")
    private CanalNotificacaoEnum canal;
    
    @Schema(description = "Email ou telefone do destinatário")
    private String destinatario;
    
    @Schema(description = "Assunto da notificação")
    private String assunto;
    
    @Schema(description = "Mensagem da notificação")
    private String mensagem;
    
    @Schema(description = "Status do envio (PENDENTE, ENVIADO, ERRO, etc)")
    private String statusEnvio;
    
    @Schema(description = "Data/hora prevista para envio")
    private OffsetDateTime dataEnvioPrevista;
    
    @Schema(description = "Data/hora do envio (quando enviado)")
    private OffsetDateTime dataEnvio;
    
    @Schema(description = "Data/hora da leitura (quando lido)")
    private OffsetDateTime dataLeitura;
    
    @Schema(description = "Número de tentativas de envio")
    private Integer tentativasEnvio;
    
    @Schema(description = "Mensagem de erro (se houver)")
    private String erroEnvio;
    
    @Schema(description = "ID externo retornado pelo serviço de envio (ex: Brevo messageId)")
    private String idExterno;
    
    
    @Schema(description = "Referência simplificada do estabelecimento")
    private EstabelecimentoRef estabelecimento;
    
    @Schema(description = "Referência simplificada do paciente")
    private PacienteRef paciente;
    
    @Schema(description = "Referência simplificada do profissional")
    private ProfissionalRef profissional;
    
    @Schema(description = "Referência simplificada do agendamento")
    private AgendamentoRef agendamento;
    
    @Schema(description = "Referência simplificada do template")
    private TemplateRef template;
}
