package com.upsaude.entity;

import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import com.upsaude.util.converter.CanalNotificacaoEnumConverter;
import com.upsaude.util.converter.TipoNotificacaoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

/**
 * Entidade que representa uma notificação enviada pelo sistema.
 * Armazena informações sobre notificações enviadas por e-mail, SMS, WhatsApp ou mensagens internas.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "notificacoes", schema = "public",
       indexes = {
           @Index(name = "idx_notificacao_paciente", columnList = "paciente_id"),
           @Index(name = "idx_notificacao_profissional", columnList = "profissional_id"),
           @Index(name = "idx_notificacao_agendamento", columnList = "agendamento_id"),
           @Index(name = "idx_notificacao_tipo", columnList = "tipo_notificacao"),
           @Index(name = "idx_notificacao_canal", columnList = "canal"),
           @Index(name = "idx_notificacao_status", columnList = "status_envio"),
           @Index(name = "idx_notificacao_data_envio", columnList = "data_envio"),
           @Index(name = "idx_notificacao_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Notificacao extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente; // Opcional: notificação para paciente

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private ProfissionaisSaude profissional; // Opcional: notificação para profissional

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento; // Opcional: relacionado a agendamento

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private TemplateNotificacao template; // Opcional: template usado

    // ========== DADOS DA NOTIFICAÇÃO ==========

    @Convert(converter = TipoNotificacaoEnumConverter.class)
    @Column(name = "tipo_notificacao", nullable = false)
    @NotNull(message = "Tipo de notificação é obrigatório")
    private TipoNotificacaoEnum tipoNotificacao;

    @Convert(converter = CanalNotificacaoEnumConverter.class)
    @Column(name = "canal", nullable = false)
    @NotNull(message = "Canal de notificação é obrigatório")
    private CanalNotificacaoEnum canal;

    @Column(name = "destinatario", nullable = false, length = 255)
    @NotNull(message = "Destinatário é obrigatório")
    private String destinatario; // E-mail, telefone ou ID de usuário

    @Column(name = "assunto", length = 500)
    private String assunto; // Assunto da notificação

    @Column(name = "mensagem", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Mensagem é obrigatória")
    private String mensagem; // Conteúdo da mensagem

    // ========== STATUS E CONTROLE ==========

    @Column(name = "status_envio", nullable = false, length = 50)
    @NotNull(message = "Status de envio é obrigatório")
    private String statusEnvio; // PENDENTE, ENVIADO, FALHA, CANCELADO

    @Column(name = "data_envio_prevista")
    private OffsetDateTime dataEnvioPrevista; // Quando deveria ser enviada

    @Column(name = "data_envio")
    private OffsetDateTime dataEnvio; // Quando foi enviada

    @Column(name = "data_leitura")
    private OffsetDateTime dataLeitura; // Quando foi lida (se aplicável)

    @Column(name = "tentativas_envio")
    private Integer tentativasEnvio; // Quantidade de tentativas de envio

    @Column(name = "maximo_tentativas")
    private Integer maximoTentativas; // Máximo de tentativas permitidas

    // ========== ERROS E LOGS ==========

    @Column(name = "erro_envio", columnDefinition = "TEXT")
    private String erroEnvio; // Mensagem de erro se falhou

    @Column(name = "log_envio", columnDefinition = "TEXT")
    private String logEnvio; // Log detalhado do envio

    @Column(name = "id_externo", length = 255)
    private String idExterno; // ID retornado pelo provedor externo (SMS, WhatsApp, etc.)

    // ========== METADADOS ==========

    @Column(name = "parametros_json", columnDefinition = "TEXT")
    private String parametrosJson; // Parâmetros adicionais em JSON

    @Column(name = "enviado_por")
    private java.util.UUID enviadoPor; // ID do usuário/sistema que enviou
}

