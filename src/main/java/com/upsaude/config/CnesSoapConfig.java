package com.upsaude.config;

import com.upsaude.integration.cnes.soap.CnesSoapLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.time.Duration;

/**
 * Configuração do cliente SOAP para o SOA-CNES (DATASUS).
 *
 * <p>Requisitos atendidos:
 * <ul>
 *   <li>SOAP 1.2</li>
 *   <li>WS-Security UsernameToken (PasswordText)</li>
 *   <li>Timeouts configuráveis</li>
 *   <li>Logging (payload) e tratamento de falhas no nível de client</li>
 * </ul>
 */
@Configuration
@EnableConfigurationProperties(CnesProperties.class)
@RequiredArgsConstructor
public class CnesSoapConfig {

    private final CnesProperties properties;

    @Bean
    public SaajSoapMessageFactory cnesSoapMessageFactory() {
        SaajSoapMessageFactory factory = new SaajSoapMessageFactory();
        factory.setSoapVersion(SoapVersion.SOAP_12);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public Jaxb2Marshaller cnesMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // Classes geradas via wsimport (jaxws-maven-plugin)
        // Processa todos os packages gerados para os diferentes serviços CNES
        // Nota: v2r0 e vinculacao temporariamente desabilitados devido a conflitos no WSDL
        marshaller.setPackagesToScan(
                "com.upsaude.integration.cnes.wsdl.cnesservice",
                "com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0",
                "com.upsaude.integration.cnes.wsdl.profissional",
                "com.upsaude.integration.cnes.wsdl.equipe",
                "com.upsaude.integration.cnes.wsdl.equipamento",
                "com.upsaude.integration.cnes.wsdl.leito"
        );
        marshaller.setMtomEnabled(false);
        marshaller.setProcessExternalEntities(false);
        try {
            marshaller.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao inicializar JAXB marshaller para CNES. " +
                    "Verifique se as classes foram geradas corretamente via wsimport. Erro: " + e.getMessage(), e);
        }
        return marshaller;
    }

    @Bean
    public HttpUrlConnectionMessageSender cnesMessageSender() {
        HttpUrlConnectionMessageSender sender = new HttpUrlConnectionMessageSender();
        sender.setConnectionTimeout(Duration.ofMillis(properties.getSoap().getConnectTimeoutMs()));
        sender.setReadTimeout(Duration.ofMillis(properties.getSoap().getReadTimeoutMs()));
        return sender;
    }

    @Bean
    public Wss4jSecurityInterceptor cnesSecurityInterceptor() throws WSSecurityException {
        Wss4jSecurityInterceptor interceptor = new Wss4jSecurityInterceptor();
        interceptor.setSecurementActions(WSHandlerConstants.USERNAME_TOKEN);
        interceptor.setSecurementUsername(properties.getSoap().getUsername());
        interceptor.setSecurementPassword(properties.getSoap().getPassword());
        interceptor.setSecurementPasswordType(WSConstants.PW_TEXT);
        // compatibilidade com alguns gateways governamentais que rejeitam mustUnderstand
        interceptor.setSecurementMustUnderstand(false);
        return interceptor;
    }

    @Bean
    public ClientInterceptor cnesLoggingInterceptor() {
        return new CnesSoapLoggingInterceptor();
    }

    @Bean
    public WebServiceTemplate cnesWebServiceTemplate(
            SaajSoapMessageFactory cnesSoapMessageFactory,
            Jaxb2Marshaller cnesMarshaller,
            HttpUrlConnectionMessageSender cnesMessageSender,
            Wss4jSecurityInterceptor cnesSecurityInterceptor,
            ClientInterceptor cnesLoggingInterceptor
    ) {
        WebServiceTemplate template = new WebServiceTemplate(cnesSoapMessageFactory);
        template.setMarshaller(cnesMarshaller);
        template.setUnmarshaller(cnesMarshaller);
        template.setMessageSender(cnesMessageSender);
        template.setInterceptors(new ClientInterceptor[]{cnesSecurityInterceptor, cnesLoggingInterceptor});
        return template;
    }
}

