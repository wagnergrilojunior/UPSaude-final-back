package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;

/**
 * Entidade que representa as configurações de um estabelecimento de saúde.
 * Armazena todas as configurações necessárias para funcionamento do sistema de agendamento e atendimento.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "configuracoes_estabelecimento", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_config_estabelecimento", columnNames = {"estabelecimento_id"})
       },
       indexes = {
           @Index(name = "idx_config_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class ConfiguracaoEstabelecimento extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false, unique = true)
    @NotNull(message = "Estabelecimento é obrigatório")
    private Estabelecimentos estabelecimento;

    // ========== CONFIGURAÇÕES DE AGENDAMENTO ==========

    @Column(name = "duracao_padrao_atendimento_minutos")
    private Integer duracaoPadraoAtendimentoMinutos; // Duração padrão de um atendimento em minutos

    @Column(name = "intervalo_minimo_entre_atendimentos_minutos")
    private Integer intervaloMinimoEntreAtendimentosMinutos; // Intervalo mínimo entre atendimentos

    @Column(name = "permite_agendamento_online")
    private Boolean permiteAgendamentoOnline; // Se permite agendamento online

    @Column(name = "antecendencia_maxima_agendamento_dias")
    private Integer antecendenciaMaximaAgendamentoDias; // Antecedência máxima para agendamento em dias

    @Column(name = "antecendencia_minima_agendamento_horas")
    private Integer antecendenciaMinimaAgendamentoHoras; // Antecedência mínima para agendamento em horas

    @Column(name = "permite_reagendamento")
    private Boolean permiteReagendamento; // Se permite reagendamento

    @Column(name = "prazo_reagendamento_horas")
    private Integer prazoReagendamentoHoras; // Prazo mínimo/máximo para reagendamento

    @Column(name = "permite_cancelamento_online")
    private Boolean permiteCancelamentoOnline; // Se permite cancelamento online

    @Column(name = "prazo_cancelamento_horas")
    private Integer prazoCancelamentoHoras; // Prazo mínimo para cancelamento em horas

    @Column(name = "permite_encaixe")
    private Boolean permiteEncaixe; // Se permite encaixe

    @Column(name = "maximo_encaixes_por_dia")
    private Integer maximoEncaixesPorDia; // Máximo de encaixes por dia

    @Column(name = "permite_fila_espera")
    private Boolean permiteFilaEspera; // Se permite fila de espera

    @Column(name = "tamanho_maximo_fila_espera")
    private Integer tamanhoMaximoFilaEspera; // Tamanho máximo da fila de espera

    @Column(name = "permite_priorizacao")
    private Boolean permitePriorizacao; // Se permite priorização de atendimentos

    @Column(name = "verifica_conflito_horario")
    private Boolean verificaConflitoHorario; // Se verifica conflitos de horário

    @Column(name = "permite_sobreposicao_atendimentos")
    private Boolean permiteSobreposicaoAtendimentos; // Se permite sobreposição de atendimentos

    // ========== CONFIGURAÇÕES DE HORÁRIOS ==========

    @Column(name = "horario_funcionamento_inicio")
    private LocalTime horarioFuncionamentoInicio; // Horário de início de funcionamento

    @Column(name = "horario_funcionamento_fim")
    private LocalTime horarioFuncionamentoFim; // Horário de fim de funcionamento

    @Column(name = "horario_almoco_inicio")
    private LocalTime horarioAlmocoInicio; // Horário de início do almoço

    @Column(name = "horario_almoco_fim")
    private LocalTime horarioAlmocoFim; // Horário de fim do almoço

    @Column(name = "funciona_sabado")
    private Boolean funcionaSabado; // Se funciona aos sábados

    @Column(name = "horario_sabado_inicio")
    private LocalTime horarioSabadoInicio;

    @Column(name = "horario_sabado_fim")
    private LocalTime horarioSabadoFim;

    @Column(name = "funciona_domingo")
    private Boolean funcionaDomingo; // Se funciona aos domingos

    @Column(name = "horario_domingo_inicio")
    private LocalTime horarioDomingoInicio;

    @Column(name = "horario_domingo_fim")
    private LocalTime horarioDomingoFim;

    @Column(name = "funciona_feriados")
    private Boolean funcionaFeriados; // Se funciona em feriados

    // ========== CONFIGURAÇÕES DE NOTIFICAÇÕES ==========

    @Column(name = "envia_notificacao_email")
    private Boolean enviaNotificacaoEmail; // Se envia notificações por e-mail

    @Column(name = "envia_notificacao_sms")
    private Boolean enviaNotificacaoSms; // Se envia notificações por SMS

    @Column(name = "envia_notificacao_whatsapp")
    private Boolean enviaNotificacaoWhatsapp; // Se envia notificações por WhatsApp

    @Column(name = "envia_lembrete_24h")
    private Boolean enviaLembrete24h; // Se envia lembrete 24 horas antes

    @Column(name = "envia_lembrete_1h")
    private Boolean enviaLembrete1h; // Se envia lembrete 1 hora antes

    @Column(name = "envia_confirmacao_agendamento")
    private Boolean enviaConfirmacaoAgendamento; // Se envia confirmação ao criar agendamento

    @Column(name = "envia_notificacao_cancelamento")
    private Boolean enviaNotificacaoCancelamento; // Se envia notificação de cancelamento

    @Column(name = "envia_notificacao_reagendamento")
    private Boolean enviaNotificacaoReagendamento; // Se envia notificação de reagendamento

    @Column(name = "envia_notificacao_resultado_exame")
    private Boolean enviaNotificacaoResultadoExame; // Se envia notificação quando resultado de exame estiver disponível

    @Column(name = "envia_aviso_falta")
    private Boolean enviaAvisoFalta; // Se envia aviso quando paciente falta

    @Column(name = "tempo_espera_aviso_falta_minutos")
    private Integer tempoEsperaAvisoFaltaMinutos; // Tempo de espera antes de enviar aviso de falta

    @Column(name = "envia_notificacao_fila_espera")
    private Boolean enviaNotificacaoFilaEspera; // Se envia notificação quando há vaga na fila de espera

    // ========== CONFIGURAÇÕES DE INTEGRAÇÕES ==========

    @Column(name = "servidor_smtp", length = 255)
    private String servidorSmtp; // Servidor SMTP para envio de e-mails

    @Column(name = "porta_smtp")
    private Integer portaSmtp; // Porta do servidor SMTP

    @Column(name = "email_remetente", length = 255)
    private String emailRemetente; // E-mail remetente padrão

    @Column(name = "nome_remetente", length = 255)
    private String nomeRemetente; // Nome do remetente

    @Column(name = "usuario_smtp", length = 255)
    private String usuarioSmtp; // Usuário para autenticação SMTP

    @Column(name = "senha_smtp", length = 500)
    private String senhaSmtp; // Senha para autenticação SMTP (criptografada)

    @Column(name = "ssl_smtp")
    private Boolean sslSmtp; // Se usa SSL para SMTP

    @Column(name = "tls_smtp")
    private Boolean tlsSmtp; // Se usa TLS para SMTP

    @Column(name = "provedor_sms", length = 100)
    private String provedorSms; // Provedor de SMS (ex: Twilio, AWS SNS)

    @Column(name = "api_key_sms", length = 500)
    private String apiKeySms; // API Key do provedor de SMS

    @Column(name = "api_secret_sms", length = 500)
    private String apiSecretSms; // API Secret do provedor de SMS

    @Column(name = "telefone_remetente_sms", length = 20)
    private String telefoneRemetenteSms; // Telefone remetente para SMS

    @Column(name = "provedor_whatsapp", length = 100)
    private String provedorWhatsapp; // Provedor de WhatsApp (ex: Twilio, Evolution API)

    @Column(name = "api_key_whatsapp", length = 500)
    private String apiKeyWhatsapp; // API Key do provedor de WhatsApp

    @Column(name = "api_secret_whatsapp", length = 500)
    private String apiSecretWhatsapp; // API Secret do provedor de WhatsApp

    @Column(name = "telefone_remetente_whatsapp", length = 20)
    private String telefoneRemetenteWhatsapp; // Telefone remetente para WhatsApp

    @Column(name = "webhook_url_whatsapp", length = 500)
    private String webhookUrlWhatsapp; // URL do webhook para WhatsApp

    // ========== CONFIGURAÇÕES DE CHECK-IN/CHECK-OUT ==========

    @Column(name = "permite_checkin_online")
    private Boolean permiteCheckinOnline; // Se permite check-in online

    @Column(name = "tempo_antecipacao_checkin_minutos")
    private Integer tempoAntecipacaoCheckinMinutos; // Tempo de antecedência para check-in

    @Column(name = "registra_presenca_automatica")
    private Boolean registraPresencaAutomatica; // Se registra presença automaticamente no check-in

    @Column(name = "tempo_tolerancia_atraso_minutos")
    private Integer tempoToleranciaAtrasoMinutos; // Tempo de tolerância para atraso

    @Column(name = "cancela_automatico_apos_atraso")
    private Boolean cancelaAutomaticoAposAtraso; // Se cancela automaticamente após atraso

    @Column(name = "tempo_cancelamento_automatico_minutos")
    private Integer tempoCancelamentoAutomaticoMinutos; // Tempo para cancelamento automático

    // ========== CONFIGURAÇÕES DE AUDITORIA E CONTROLE ==========

    @Column(name = "registra_auditoria_completa")
    private Boolean registraAuditoriaCompleta; // Se registra auditoria completa

    @Column(name = "retencao_dados_meses")
    private Integer retencaoDadosMeses; // Retenção de dados em meses

    @Column(name = "exige_justificativa_cancelamento")
    private Boolean exigeJustificativaCancelamento; // Se exige justificativa para cancelamento

    @Column(name = "exige_justificativa_reagendamento")
    private Boolean exigeJustificativaReagendamento; // Se exige justificativa para reagendamento

    @Column(name = "permite_acesso_paciente_portal")
    private Boolean permiteAcessoPacientePortal; // Se permite acesso do paciente ao portal

    @Column(name = "permite_agendamento_profissional")
    private Boolean permiteAgendamentoProfissional; // Se profissionais podem agendar

    @Column(name = "permite_cancelamento_profissional")
    private Boolean permiteCancelamentoProfissional; // Se profissionais podem cancelar

    // ========== CONFIGURAÇÕES DE RELATÓRIOS ==========

    @Column(name = "gera_relatorio_produtividade")
    private Boolean geraRelatorioProdutividade; // Se gera relatório de produtividade

    @Column(name = "gera_relatorio_nao_comparecimento")
    private Boolean geraRelatorioNaoComparecimento; // Se gera relatório de não comparecimento

    @Column(name = "gera_relatorio_estatisticas")
    private Boolean geraRelatorioEstatisticas; // Se gera relatório de estatísticas

    // ========== CONFIGURAÇÕES DE HISTÓRICO CLÍNICO ==========

    @Column(name = "mantem_historico_completo")
    private Boolean mantemHistoricoCompleto; // Se mantém histórico clínico completo

    @Column(name = "compartilha_historico_entre_estabelecimentos")
    private Boolean compartilhaHistoricoEntreEstabelecimentos; // Se compartilha histórico entre estabelecimentos

    @Column(name = "permite_visualizacao_historico_paciente")
    private Boolean permiteVisualizacaoHistoricoPaciente; // Se paciente pode visualizar histórico

    // ========== CONFIGURAÇÕES DE MULTITENANCY ==========

    @Column(name = "isolamento_dados_estabelecimento")
    private Boolean isolamentoDadosEstabelecimento; // Se isola dados por estabelecimento

    @Column(name = "compartilha_recursos_tenant")
    private Boolean compartilhaRecursosTenant; // Se compartilha recursos entre estabelecimentos do tenant

    // ========== CONFIGURAÇÕES GERAIS ==========

    @Column(name = "timezone", length = 50)
    private String timezone; // Timezone do estabelecimento (ex: America/Sao_Paulo)

    @Column(name = "idioma", length = 10)
    private String idioma; // Idioma padrão (ex: pt-BR)

    @Column(name = "formato_data", length = 20)
    private String formatoData; // Formato de data (ex: dd/MM/yyyy)

    @Column(name = "formato_hora", length = 20)
    private String formatoHora; // Formato de hora (ex: HH:mm)

    @Column(name = "configuracao_json", columnDefinition = "TEXT")
    private String configuracaoJson; // Configurações adicionais em JSON para extensibilidade

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes; // Observações gerais sobre as configurações
}

