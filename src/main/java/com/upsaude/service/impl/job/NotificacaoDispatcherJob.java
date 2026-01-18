package com.upsaude.service.impl.job;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.config.BrevoConfig;
import com.upsaude.entity.sistema.notificacao.Notificacao;
import com.upsaude.entity.sistema.notificacao.TemplateNotificacao;
import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.integration.brevo.BrevoEmailClient;
import com.upsaude.repository.sistema.notificacao.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "brevo.enabled", havingValue = "true", matchIfMissing = true)
public class NotificacaoDispatcherJob {

    private final NotificacaoRepository notificacaoRepository;
    private final BrevoEmailClient brevoEmailClient;
    private final BrevoConfig brevoConfig;
    private final ObjectMapper objectMapper;

    
    @Scheduled(fixedDelayString = "${brevo.dispatcher.interval-seconds:30}000")
    @Transactional
    public void processarNotificacoesPendentes() {
        if (!Boolean.TRUE.equals(brevoConfig.getEnabled())) {
            return;
        }

        OffsetDateTime agora = OffsetDateTime.now();
        List<Notificacao> pendentes = notificacaoRepository.findPendentesParaEnvio(agora);

        if (pendentes.isEmpty()) {
            log.debug("Nenhuma notificação pendente para envio");
            return;
        }

        log.info("Processando {} notificação(ões) pendente(s)", pendentes.size());

        for (Notificacao notificacao : pendentes) {
            try {
                processarNotificacao(notificacao);
            } catch (Exception e) {
                log.error("Erro ao processar notificação ID: {}", notificacao.getId(), e);
                tratarErroEnvio(notificacao, e.getMessage());
            }
        }
    }

    private void processarNotificacao(Notificacao notificacao) {
        if (!CanalNotificacaoEnum.EMAIL.equals(notificacao.getCanal())) {
            log.warn("Canal de notificação não suportado: {}. Notificação ID: {}", 
                    notificacao.getCanal(), notificacao.getId());
            notificacao.setStatusEnvio("ERRO");
            notificacao.setErroEnvio("Canal não suportado: " + notificacao.getCanal());
            notificacaoRepository.save(notificacao);
            return;
        }

        TemplateNotificacao template = notificacao.getTemplate();
        if (template == null || template.getBrevoTemplateId() == null) {
            log.warn("Template sem brevoTemplateId. Notificação ID: {}", notificacao.getId());
            notificacao.setStatusEnvio("ERRO");
            notificacao.setErroEnvio("Template sem brevoTemplateId configurado");
            notificacaoRepository.save(notificacao);
            return;
        }

        Map<String, Object> params = parsearParametros(notificacao.getParametrosJson());

        BrevoEmailClient.SenderType senderType = determinarSenderType(notificacao.getTipoNotificacao());

        try {
            String messageId = brevoEmailClient.sendTemplateEmail(
                    template.getBrevoTemplateId(),
                    notificacao.getDestinatario(),
                    null, 
                    params,
                    senderType
            );

            notificacao.setStatusEnvio("ENVIADO");
            notificacao.setDataEnvio(OffsetDateTime.now());
            notificacao.setIdExterno(messageId);
            notificacao.setTentativasEnvio((notificacao.getTentativasEnvio() != null ? notificacao.getTentativasEnvio() : 0) + 1);
            notificacao.setErroEnvio(null);

            log.info("Notificação enviada com sucesso. ID: {}, MessageId: {}", notificacao.getId(), messageId);

        } catch (Exception e) {
            log.error("Erro ao enviar notificação via Brevo. Notificação ID: {}", notificacao.getId(), e);
            tratarErroEnvio(notificacao, e.getMessage());
        }

        notificacaoRepository.save(notificacao);
    }

    private void tratarErroEnvio(Notificacao notificacao, String erro) {
        int tentativas = (notificacao.getTentativasEnvio() != null ? notificacao.getTentativasEnvio() : 0) + 1;
        int maxTentativas = notificacao.getMaximoTentativas() != null ? notificacao.getMaximoTentativas() : 3;

        notificacao.setTentativasEnvio(tentativas);
        notificacao.setErroEnvio(erro);

        if (tentativas >= maxTentativas) {
            notificacao.setStatusEnvio("FALHA");
            log.warn("Notificação ID: {} atingiu máximo de tentativas ({})", notificacao.getId(), maxTentativas);
        } else {
            
            long minutosBackoff = calcularBackoffMinutos(tentativas);
            OffsetDateTime novaDataEnvio = OffsetDateTime.now().plusMinutes(minutosBackoff);
            notificacao.setDataEnvioPrevista(novaDataEnvio);
            notificacao.setStatusEnvio("PENDENTE");
            log.info("Notificação ID: {} será reenviada em {} minutos (tentativa {}/{})", 
                    notificacao.getId(), minutosBackoff, tentativas, maxTentativas);
        }
    }

    private long calcularBackoffMinutos(int tentativa) {
        switch (tentativa) {
            case 1:
                return 5; 
            case 2:
                return 15; 
            default:
                return 60; 
        }
    }

    private Map<String, Object> parsearParametros(String parametrosJson) {
        if (parametrosJson == null || parametrosJson.trim().isEmpty()) {
            return Map.of();
        }

        try {
            return objectMapper.readValue(parametrosJson, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            log.warn("Erro ao parsear parâmetros JSON da notificação", e);
            return Map.of();
        }
    }

    private BrevoEmailClient.SenderType determinarSenderType(com.upsaude.enums.TipoNotificacaoEnum tipo) {
        switch (tipo) {
            case USUARIO_CRIADO:
            case SENHA_ALTERADA:
            case DADOS_PESSOAIS_ATUALIZADOS:
                return BrevoEmailClient.SenderType.NOREPLY;
            case AGENDAMENTO_CONFIRMADO:
            case AGENDAMENTO_CANCELADO:
            case LEMBRETE_24H:
            case LEMBRETE_1H:
                return BrevoEmailClient.SenderType.NOTIFICACOES;
            default:
                return BrevoEmailClient.SenderType.NOREPLY;
        }
    }
}
