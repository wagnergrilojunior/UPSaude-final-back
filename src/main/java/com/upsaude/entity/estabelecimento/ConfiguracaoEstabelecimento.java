package com.upsaude.entity.estabelecimento;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.estabelecimento.Estabelecimentos;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false, unique = true)
    @NotNull(message = "Estabelecimento é obrigatório")
    private Estabelecimentos estabelecimento;

    @Column(name = "duracao_padrao_atendimento_minutos")
    private Integer duracaoPadraoAtendimentoMinutos;

    @Column(name = "intervalo_minimo_entre_atendimentos_minutos")
    private Integer intervaloMinimoEntreAtendimentosMinutos;

    @Column(name = "permite_agendamento_online")
    private Boolean permiteAgendamentoOnline;

    @Column(name = "antecendencia_maxima_agendamento_dias")
    private Integer antecendenciaMaximaAgendamentoDias;

    @Column(name = "antecendencia_minima_agendamento_horas")
    private Integer antecendenciaMinimaAgendamentoHoras;

    @Column(name = "permite_reagendamento")
    private Boolean permiteReagendamento;

    @Column(name = "prazo_reagendamento_horas")
    private Integer prazoReagendamentoHoras;

    @Column(name = "permite_cancelamento_online")
    private Boolean permiteCancelamentoOnline;

    @Column(name = "prazo_cancelamento_horas")
    private Integer prazoCancelamentoHoras;

    @Column(name = "permite_encaixe")
    private Boolean permiteEncaixe;

    @Column(name = "maximo_encaixes_por_dia")
    private Integer maximoEncaixesPorDia;

    @Column(name = "permite_fila_espera")
    private Boolean permiteFilaEspera;

    @Column(name = "tamanho_maximo_fila_espera")
    private Integer tamanhoMaximoFilaEspera;

    @Column(name = "permite_priorizacao")
    private Boolean permitePriorizacao;

    @Column(name = "verifica_conflito_horario")
    private Boolean verificaConflitoHorario;

    @Column(name = "permite_sobreposicao_atendimentos")
    private Boolean permiteSobreposicaoAtendimentos;

    @Column(name = "horario_funcionamento_inicio")
    private LocalTime horarioFuncionamentoInicio;

    @Column(name = "horario_funcionamento_fim")
    private LocalTime horarioFuncionamentoFim;

    @Column(name = "horario_almoco_inicio")
    private LocalTime horarioAlmocoInicio;

    @Column(name = "horario_almoco_fim")
    private LocalTime horarioAlmocoFim;

    @Column(name = "funciona_sabado")
    private Boolean funcionaSabado;

    @Column(name = "horario_sabado_inicio")
    private LocalTime horarioSabadoInicio;

    @Column(name = "horario_sabado_fim")
    private LocalTime horarioSabadoFim;

    @Column(name = "funciona_domingo")
    private Boolean funcionaDomingo;

    @Column(name = "horario_domingo_inicio")
    private LocalTime horarioDomingoInicio;

    @Column(name = "horario_domingo_fim")
    private LocalTime horarioDomingoFim;

    @Column(name = "funciona_feriados")
    private Boolean funcionaFeriados;

    @Column(name = "envia_notificacao_email")
    private Boolean enviaNotificacaoEmail;

    @Column(name = "envia_notificacao_sms")
    private Boolean enviaNotificacaoSms;

    @Column(name = "envia_notificacao_whatsapp")
    private Boolean enviaNotificacaoWhatsapp;

    @Column(name = "envia_lembrete_24h")
    private Boolean enviaLembrete24h;

    @Column(name = "envia_lembrete_1h")
    private Boolean enviaLembrete1h;

    @Column(name = "envia_confirmacao_agendamento")
    private Boolean enviaConfirmacaoAgendamento;

    @Column(name = "envia_notificacao_cancelamento")
    private Boolean enviaNotificacaoCancelamento;

    @Column(name = "envia_notificacao_reagendamento")
    private Boolean enviaNotificacaoReagendamento;

    @Column(name = "envia_notificacao_resultado_exame")
    private Boolean enviaNotificacaoResultadoExame;

    @Column(name = "envia_aviso_falta")
    private Boolean enviaAvisoFalta;

    @Column(name = "tempo_espera_aviso_falta_minutos")
    private Integer tempoEsperaAvisoFaltaMinutos;

    @Column(name = "envia_notificacao_fila_espera")
    private Boolean enviaNotificacaoFilaEspera;

    @Column(name = "servidor_smtp", length = 255)
    private String servidorSmtp;

    @Column(name = "porta_smtp")
    private Integer portaSmtp;

    @Column(name = "email_remetente", length = 255)
    private String emailRemetente;

    @Column(name = "nome_remetente", length = 255)
    private String nomeRemetente;

    @Column(name = "usuario_smtp", length = 255)
    private String usuarioSmtp;

    @Column(name = "senha_smtp", length = 500)
    private String senhaSmtp;

    @Column(name = "ssl_smtp")
    private Boolean sslSmtp;

    @Column(name = "tls_smtp")
    private Boolean tlsSmtp;

    @Column(name = "provedor_sms", length = 100)
    private String provedorSms;

    @Column(name = "api_key_sms", length = 500)
    private String apiKeySms;

    @Column(name = "api_secret_sms", length = 500)
    private String apiSecretSms;

    @Column(name = "telefone_remetente_sms", length = 20)
    private String telefoneRemetenteSms;

    @Column(name = "provedor_whatsapp", length = 100)
    private String provedorWhatsapp;

    @Column(name = "api_key_whatsapp", length = 500)
    private String apiKeyWhatsapp;

    @Column(name = "api_secret_whatsapp", length = 500)
    private String apiSecretWhatsapp;

    @Column(name = "telefone_remetente_whatsapp", length = 20)
    private String telefoneRemetenteWhatsapp;

    @Column(name = "webhook_url_whatsapp", length = 500)
    private String webhookUrlWhatsapp;

    @Column(name = "permite_checkin_online")
    private Boolean permiteCheckinOnline;

    @Column(name = "tempo_antecipacao_checkin_minutos")
    private Integer tempoAntecipacaoCheckinMinutos;

    @Column(name = "registra_presenca_automatica")
    private Boolean registraPresencaAutomatica;

    @Column(name = "tempo_tolerancia_atraso_minutos")
    private Integer tempoToleranciaAtrasoMinutos;

    @Column(name = "cancela_automatico_apos_atraso")
    private Boolean cancelaAutomaticoAposAtraso;

    @Column(name = "tempo_cancelamento_automatico_minutos")
    private Integer tempoCancelamentoAutomaticoMinutos;

    @Column(name = "registra_auditoria_completa")
    private Boolean registraAuditoriaCompleta;

    @Column(name = "retencao_dados_meses")
    private Integer retencaoDadosMeses;

    @Column(name = "exige_justificativa_cancelamento")
    private Boolean exigeJustificativaCancelamento;

    @Column(name = "exige_justificativa_reagendamento")
    private Boolean exigeJustificativaReagendamento;

    @Column(name = "permite_acesso_paciente_portal")
    private Boolean permiteAcessoPacientePortal;

    @Column(name = "permite_agendamento_profissional")
    private Boolean permiteAgendamentoProfissional;

    @Column(name = "permite_cancelamento_profissional")
    private Boolean permiteCancelamentoProfissional;

    @Column(name = "gera_relatorio_produtividade")
    private Boolean geraRelatorioProdutividade;

    @Column(name = "gera_relatorio_nao_comparecimento")
    private Boolean geraRelatorioNaoComparecimento;

    @Column(name = "gera_relatorio_estatisticas")
    private Boolean geraRelatorioEstatisticas;

    @Column(name = "mantem_historico_completo")
    private Boolean mantemHistoricoCompleto;

    @Column(name = "compartilha_historico_entre_estabelecimentos")
    private Boolean compartilhaHistoricoEntreEstabelecimentos;

    @Column(name = "permite_visualizacao_historico_paciente")
    private Boolean permiteVisualizacaoHistoricoPaciente;

    @Column(name = "isolamento_dados_estabelecimento")
    private Boolean isolamentoDadosEstabelecimento;

    @Column(name = "compartilha_recursos_tenant")
    private Boolean compartilhaRecursosTenant;

    @Column(name = "timezone", length = 50)
    private String timezone;

    @Column(name = "idioma", length = 10)
    private String idioma;

    @Column(name = "formato_data", length = 20)
    private String formatoData;

    @Column(name = "formato_hora", length = 20)
    private String formatoHora;

    @Column(name = "configuracao_json", columnDefinition = "TEXT")
    private String configuracaoJson;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
