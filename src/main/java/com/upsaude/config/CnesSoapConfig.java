package com.upsaude.config;

import com.upsaude.integration.cnes.soap.CnesSoapLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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
 * <p>
 * Requisitos atendidos:
 * <ul>
 * <li>SOAP 1.2</li>
 * <li>WS-Security UsernameToken (PasswordText)</li>
 * <li>Timeouts configuráveis</li>
 * <li>Logging (payload) e tratamento de falhas no nível de client</li>
 * </ul>
 */
@Configuration
@EnableConfigurationProperties(CnesProperties.class)
@RequiredArgsConstructor
@Lazy
public class CnesSoapConfig {

    private final CnesProperties properties;

    @Bean
    @Lazy
    public SaajSoapMessageFactory cnesSoapMessageFactory() {
        SaajSoapMessageFactory factory = new SaajSoapMessageFactory();
        factory.setSoapVersion(SoapVersion.SOAP_12);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    @Lazy
    public Jaxb2Marshaller cnesServiceMarshaller() {
        return createMarshaller("com.upsaude.integration.cnes.wsdl.cnesservice");
    }

    @Bean
    @Lazy
    public Jaxb2Marshaller profissionalMarshaller() {
        return createMarshaller("com.upsaude.integration.cnes.wsdl.profissional");
    }

    @Bean
    @Lazy
    public Jaxb2Marshaller equipeMarshaller() {
        return createMarshaller("com.upsaude.integration.cnes.wsdl.equipe");
    }

    @Bean
    @Lazy
    public Jaxb2Marshaller equipamentoMarshaller() {
        return createMarshaller("com.upsaude.integration.cnes.wsdl.equipamento");
    }

    @Bean
    @Lazy
    public Jaxb2Marshaller leitoMarshaller() {
        return createMarshaller("com.upsaude.integration.cnes.wsdl.leito");
    }

    @Bean
    @Lazy
    public Jaxb2Marshaller estabelecimentoV1r0Marshaller() {
        return createMarshaller("com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0");
    }

    private Jaxb2Marshaller createMarshaller(String contextPath) {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(contextPath);
        marshaller.setMtomEnabled(false);
        marshaller.setProcessExternalEntities(false);
        return marshaller;
    }

    @Bean
    @Lazy
    public HttpUrlConnectionMessageSender cnesMessageSender() {
        HttpUrlConnectionMessageSender sender = new HttpUrlConnectionMessageSender();
        sender.setConnectionTimeout(Duration.ofMillis(properties.getSoap().getConnectTimeoutMs()));
        sender.setReadTimeout(Duration.ofMillis(properties.getSoap().getReadTimeoutMs()));
        return sender;
    }

    @Bean
    @Lazy
    public Wss4jSecurityInterceptor cnesSecurityInterceptor() throws WSSecurityException {
        Wss4jSecurityInterceptor interceptor = new Wss4jSecurityInterceptor();
        interceptor.setSecurementActions(WSHandlerConstants.USERNAME_TOKEN);
        interceptor.setSecurementUsername(properties.getSoap().getUsername());
        interceptor.setSecurementPassword(properties.getSoap().getPassword());
        interceptor.setSecurementPasswordType(WSConstants.PW_TEXT);
        // compatibilidade com alguns gateways governamentais que rejeitam
        // mustUnderstand
        interceptor.setSecurementMustUnderstand(false);
        return interceptor;
    }

    @Bean
    @Lazy
    public ClientInterceptor cnesLoggingInterceptor() {
        return new CnesSoapLoggingInterceptor();
    }

    @Bean
    @Lazy
    public WebServiceTemplate cnesServiceTemplate(
            SaajSoapMessageFactory cnesSoapMessageFactory,
            @Qualifier("cnesServiceMarshaller") Jaxb2Marshaller marshaller,
            HttpUrlConnectionMessageSender cnesMessageSender,
            Wss4jSecurityInterceptor cnesSecurityInterceptor,
            ClientInterceptor cnesLoggingInterceptor) {
        return createTemplate(cnesSoapMessageFactory, marshaller, cnesMessageSender, cnesSecurityInterceptor,
                cnesLoggingInterceptor);
    }

    @Bean
    @Lazy
    public WebServiceTemplate profissionalTemplate(
            SaajSoapMessageFactory cnesSoapMessageFactory,
            @Qualifier("profissionalMarshaller") Jaxb2Marshaller marshaller,
            HttpUrlConnectionMessageSender cnesMessageSender,
            Wss4jSecurityInterceptor cnesSecurityInterceptor,
            ClientInterceptor cnesLoggingInterceptor) {
        return createTemplate(cnesSoapMessageFactory, marshaller, cnesMessageSender, cnesSecurityInterceptor,
                cnesLoggingInterceptor);
    }

    @Bean
    @Lazy
    public WebServiceTemplate equipeTemplate(
            SaajSoapMessageFactory cnesSoapMessageFactory,
            @Qualifier("equipeMarshaller") Jaxb2Marshaller marshaller,
            HttpUrlConnectionMessageSender cnesMessageSender,
            Wss4jSecurityInterceptor cnesSecurityInterceptor,
            ClientInterceptor cnesLoggingInterceptor) {
        return createTemplate(cnesSoapMessageFactory, marshaller, cnesMessageSender, cnesSecurityInterceptor,
                cnesLoggingInterceptor);
    }

    @Bean
    @Lazy
    public WebServiceTemplate equipamentoTemplate(
            SaajSoapMessageFactory cnesSoapMessageFactory,
            @Qualifier("equipamentoMarshaller") Jaxb2Marshaller marshaller,
            HttpUrlConnectionMessageSender cnesMessageSender,
            Wss4jSecurityInterceptor cnesSecurityInterceptor,
            ClientInterceptor cnesLoggingInterceptor) {
        return createTemplate(cnesSoapMessageFactory, marshaller, cnesMessageSender, cnesSecurityInterceptor,
                cnesLoggingInterceptor);
    }

    @Bean
    @Lazy
    public WebServiceTemplate leitoTemplate(
            SaajSoapMessageFactory cnesSoapMessageFactory,
            @Qualifier("leitoMarshaller") Jaxb2Marshaller marshaller,
            HttpUrlConnectionMessageSender cnesMessageSender,
            Wss4jSecurityInterceptor cnesSecurityInterceptor,
            ClientInterceptor cnesLoggingInterceptor) {
        return createTemplate(cnesSoapMessageFactory, marshaller, cnesMessageSender, cnesSecurityInterceptor,
                cnesLoggingInterceptor);
    }

    @Bean
    @Lazy
    public WebServiceTemplate estabelecimentoV1r0Template(
            SaajSoapMessageFactory cnesSoapMessageFactory,
            @Qualifier("estabelecimentoV1r0Marshaller") Jaxb2Marshaller marshaller,
            HttpUrlConnectionMessageSender cnesMessageSender,
            Wss4jSecurityInterceptor cnesSecurityInterceptor,
            ClientInterceptor cnesLoggingInterceptor) {
        return createTemplate(cnesSoapMessageFactory, marshaller, cnesMessageSender, cnesSecurityInterceptor,
                cnesLoggingInterceptor);
    }

    private WebServiceTemplate createTemplate(
            SaajSoapMessageFactory factory,
            Jaxb2Marshaller marshaller,
            HttpUrlConnectionMessageSender sender,
            Wss4jSecurityInterceptor security,
            ClientInterceptor logging) {
        WebServiceTemplate template = new WebServiceTemplate(factory);
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        template.setMessageSender(sender);
        template.setInterceptors(new ClientInterceptor[] { security, logging });
        return template;
    }
}
