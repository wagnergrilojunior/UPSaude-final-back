package com.upsaude.integration.brevo;

import com.upsaude.config.BrevoConfig;
import com.upsaude.integration.brevo.dto.BrevoEmailRequest;
import com.upsaude.integration.brevo.dto.BrevoEmailResponse;
import com.upsaude.integration.brevo.exception.BrevoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class BrevoEmailClient {

    private final BrevoConfig brevoConfig;
    @Qualifier("brevoRestTemplate")
    private final RestTemplate brevoRestTemplate;

    /**
     * Envia e-mail usando template do Brevo
     *
     * @param templateId ID do template no Brevo
     * @param toEmail Email do destinatário
     * @param toName Nome do destinatário (opcional)
     * @param params Parâmetros para substituir no template
     * @param senderType Tipo de remetente (noreply, notificacoes, suporte)
     * @return MessageId do e-mail enviado
     */
    public String sendTemplateEmail(
            Integer templateId,
            String toEmail,
            String toName,
            Map<String, Object> params,
            SenderType senderType) {
        
        if (!Boolean.TRUE.equals(brevoConfig.getEnabled())) {
            log.warn("Brevo está desabilitado. E-mail não será enviado para: {}", toEmail);
            return null;
        }

        if (brevoConfig.getApiKey() == null || brevoConfig.getApiKey().trim().isEmpty()) {
            log.error("Chave API do Brevo não configurada!");
            throw new BrevoException("Chave API do Brevo não configurada");
        }

        BrevoConfig.Sender senderConfig = brevoConfig.getSender();
        BrevoEmailRequest.Sender sender;
        
        switch (senderType) {
            case NOREPLY:
                sender = BrevoEmailRequest.Sender.builder()
                        .email(senderConfig.getNoreply().getEmail())
                        .name(senderConfig.getNoreply().getName())
                        .build();
                break;
            case NOTIFICACOES:
                sender = BrevoEmailRequest.Sender.builder()
                        .email(senderConfig.getNotificacoes().getEmail())
                        .name(senderConfig.getNotificacoes().getName())
                        .build();
                break;
            case SUPORTE:
                sender = BrevoEmailRequest.Sender.builder()
                        .email(senderConfig.getSuporte().getEmail())
                        .name(senderConfig.getSuporte().getName())
                        .build();
                break;
            default:
                sender = BrevoEmailRequest.Sender.builder()
                        .email(senderConfig.getNoreply().getEmail())
                        .name(senderConfig.getNoreply().getName())
                        .build();
        }

        BrevoEmailRequest.Recipient recipient = BrevoEmailRequest.Recipient.builder()
                .email(toEmail)
                .name(toName != null ? toName : "")
                .build();

        BrevoEmailRequest request = BrevoEmailRequest.builder()
                .sender(sender)
                .to(Collections.singletonList(recipient))
                .templateId(templateId)
                .params(params != null ? params : Collections.emptyMap())
                .build();

        return sendEmail(request);
    }

    /**
     * Envia e-mail com conteúdo HTML/texto direto (sem template)
     */
    public String sendEmail(
            String toEmail,
            String toName,
            String subject,
            String htmlContent,
            String textContent,
            SenderType senderType) {
        
        if (!Boolean.TRUE.equals(brevoConfig.getEnabled())) {
            log.warn("Brevo está desabilitado. E-mail não será enviado para: {}", toEmail);
            return null;
        }

        if (brevoConfig.getApiKey() == null || brevoConfig.getApiKey().trim().isEmpty()) {
            log.error("Chave API do Brevo não configurada!");
            throw new BrevoException("Chave API do Brevo não configurada");
        }

        BrevoConfig.Sender senderConfig = brevoConfig.getSender();
        BrevoEmailRequest.Sender sender;
        
        switch (senderType) {
            case NOREPLY:
                sender = BrevoEmailRequest.Sender.builder()
                        .email(senderConfig.getNoreply().getEmail())
                        .name(senderConfig.getNoreply().getName())
                        .build();
                break;
            case NOTIFICACOES:
                sender = BrevoEmailRequest.Sender.builder()
                        .email(senderConfig.getNotificacoes().getEmail())
                        .name(senderConfig.getNotificacoes().getName())
                        .build();
                break;
            case SUPORTE:
                sender = BrevoEmailRequest.Sender.builder()
                        .email(senderConfig.getSuporte().getEmail())
                        .name(senderConfig.getSuporte().getName())
                        .build();
                break;
            default:
                sender = BrevoEmailRequest.Sender.builder()
                        .email(senderConfig.getNoreply().getEmail())
                        .name(senderConfig.getNoreply().getName())
                        .build();
        }

        BrevoEmailRequest.Recipient recipient = BrevoEmailRequest.Recipient.builder()
                .email(toEmail)
                .name(toName != null ? toName : "")
                .build();

        BrevoEmailRequest request = BrevoEmailRequest.builder()
                .sender(sender)
                .to(Collections.singletonList(recipient))
                .subject(subject)
                .htmlContent(htmlContent)
                .textContent(textContent)
                .build();

        return sendEmail(request);
    }

    private String sendEmail(BrevoEmailRequest request) {
        String url = brevoConfig.getBaseUrl() + "/smtp/email";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Limpar espaços em branco da chave (pode vir do arquivo de propriedades)
        String apiKey = brevoConfig.getApiKey();
        if (apiKey != null) {
            apiKey = apiKey.trim();
        }
        
        // Validar formato da chave
        if (apiKey == null || apiKey.isEmpty()) {
            log.error("Chave API do Brevo está vazia ou nula!");
            throw new BrevoException("Chave API do Brevo não configurada");
        }
        
        // Verificar se a chave começa com o prefixo esperado
        if (!apiKey.startsWith("xkeysib-") && !apiKey.startsWith("xsmtpsib-")) {
            log.warn("Chave API do Brevo não tem formato esperado (deve começar com 'xkeysib-' ou 'xsmtpsib-'). " +
                    "Chave recebida começa com: {}", 
                    apiKey.length() > 10 ? apiKey.substring(0, 10) : apiKey);
        }
        
        headers.set("api-key", apiKey);

        // Log parcial da chave para diagnóstico (mostra apenas primeiros e últimos caracteres)
        String apiKeyMasked = apiKey.length() > 10 
                ? apiKey.substring(0, 8) + "..." + apiKey.substring(apiKey.length() - 4)
                : "N/A";
        log.info("Usando chave API Brevo: {} (tamanho: {} caracteres)", apiKeyMasked, apiKey.length());

        HttpEntity<BrevoEmailRequest> httpEntity = new HttpEntity<>(request, headers);

        try {
            log.debug("Enviando e-mail via Brevo. URL: {}, Destinatário: {}", url, 
                    request.getTo() != null && !request.getTo().isEmpty() 
                            ? request.getTo().get(0).getEmail() : "N/A");

            ResponseEntity<BrevoEmailResponse> response = brevoRestTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    httpEntity,
                    BrevoEmailResponse.class
            );

            BrevoEmailResponse body = response.getBody();
            if (response.getStatusCode().is2xxSuccessful() && body != null) {
                String messageId = body.getMessageId();
                log.info("E-mail enviado com sucesso via Brevo. MessageId: {}", messageId);
                return messageId;
            }

            log.warn("Resposta inesperada do Brevo. Status: {}, Body: {}", 
                    response.getStatusCode(), response.getBody());
            throw new BrevoException("Resposta inesperada do Brevo: " + response.getStatusCode());

        } catch (HttpClientErrorException e) {
            String errorBody = e.getResponseBodyAsString();
            log.error("Erro HTTP 4xx ao enviar e-mail via Brevo. Status: {}, Body: {}", 
                    e.getStatusCode(), errorBody);
            
            // Mensagem mais clara para erro 401
            if (e.getStatusCode().value() == 401) {
                String errorMessage = "Chave API do Brevo não está habilitada ou não tem permissões. " +
                        "Verifique no painel do Brevo (https://app.brevo.com/settings/keys/api) se a chave está: " +
                        "1) Ativa, 2) Com permissões de 'Send emails' habilitadas, 3) Não bloqueada/suspensa. " +
                        "Erro original: " + errorBody;
                throw new BrevoException(errorMessage, e);
            }
            
            throw new BrevoException("Erro ao enviar e-mail via Brevo: " + e.getMessage(), e);
        } catch (HttpServerErrorException e) {
            log.error("Erro HTTP 5xx ao enviar e-mail via Brevo. Status: {}, Body: {}", 
                    e.getStatusCode(), e.getResponseBodyAsString());
            throw new BrevoException("Erro no servidor Brevo: " + e.getMessage(), e);
        } catch (RestClientException e) {
            log.error("Erro de comunicação com Brevo", e);
            throw new BrevoException("Erro de comunicação com Brevo: " + e.getMessage(), e);
        }
    }

    public enum SenderType {
        NOREPLY,
        NOTIFICACOES,
        SUPORTE
    }
}
