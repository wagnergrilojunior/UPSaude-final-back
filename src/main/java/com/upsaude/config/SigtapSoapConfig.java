package com.upsaude.config;

import com.upsaude.integration.sigtap.soap.SigtapSoapLoggingInterceptor;
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
 * Configura??o do cliente SOAP para o SOA-SIGTAP (DATASUS).
 *
 * <p>Requisitos atendidos:
 * <ul>
 *   <li>SOAP 1.2</li>
 *   <li>WS-Security UsernameToken (PasswordText)</li>
 *   <li>Timeouts configur?veis</li>
 *   <li>Logging (payload) e tratamento de falhas no n?vel de client</li>
 * </ul>
 */
@Configuration
@EnableConfigurationProperties(SigtapProperties.class)
@RequiredArgsConstructor
public class SigtapSoapConfig {

    private final SigtapProperties properties;

    @Bean
    public SaajSoapMessageFactory sigtapSoapMessageFactory() {
        SaajSoapMessageFactory factory = new SaajSoapMessageFactory();
        factory.setSoapVersion(SoapVersion.SOAP_12);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public Jaxb2Marshaller sigtapMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // Classes geradas via wsimport (jaxws-maven-plugin)
        // Usa classesToBeBound explicitamente para garantir que todas as classes sejam reconhecidas
        // Inclui ObjectFactory e todas as classes de request/response necessárias
        marshaller.setClassesToBeBound(
                // ObjectFactory (essencial para JAXB context)
                com.upsaude.integration.sigtap.wsdl.ObjectFactory.class,
                // Requests
                com.upsaude.integration.sigtap.wsdl.RequestListarGrupos.class,
                com.upsaude.integration.sigtap.wsdl.RequestListarSubgrupos.class,
                com.upsaude.integration.sigtap.wsdl.RequestListarFormaOrganizacao.class,
                com.upsaude.integration.sigtap.wsdl.RequestPesquisarProcedimentos.class,
                com.upsaude.integration.sigtap.wsdl.RequestDetalharProcedimento.class,
                com.upsaude.integration.sigtap.wsdl.RequestListarCompatibilidadesPossiveis.class,
                com.upsaude.integration.sigtap.wsdl.RequestPesquisarCompatibilidades.class,
                // Responses
                com.upsaude.integration.sigtap.wsdl.ResponseListarGrupos.class,
                com.upsaude.integration.sigtap.wsdl.ResponseListarSubgrupos.class,
                com.upsaude.integration.sigtap.wsdl.ResponseListarFormaOrganizacao.class,
                com.upsaude.integration.sigtap.wsdl.ResponsePesquisarProcedimentos.class,
                com.upsaude.integration.sigtap.wsdl.ResponseDetalharProcedimento.class,
                com.upsaude.integration.sigtap.wsdl.ResponseListarCompatibilidadesPossiveis.class,
                com.upsaude.integration.sigtap.wsdl.ResponsePesquisarCompatibilidades.class,
                // Tipos complexos principais
                com.upsaude.integration.sigtap.wsdl.GrupoType.class,
                com.upsaude.integration.sigtap.wsdl.SubgrupoType.class,
                com.upsaude.integration.sigtap.wsdl.FormaOrganizacaoType.class,
                com.upsaude.integration.sigtap.wsdl.ProcedimentoType.class,
                com.upsaude.integration.sigtap.wsdl.BaseProcedimentoType.class,
                com.upsaude.integration.sigtap.wsdl.CompatibilidadeType.class,
                com.upsaude.integration.sigtap.wsdl.CompatibilidadePossivelType.class,
                com.upsaude.integration.sigtap.wsdl.PaginacaoType.class,
                com.upsaude.integration.sigtap.wsdl.DetalheAdicionalType.class,
                com.upsaude.integration.sigtap.wsdl.CategoriaDetalheAdicionalType.class,
                com.upsaude.integration.sigtap.wsdl.ResultadosPesquisaProcedimentosType.class,
                com.upsaude.integration.sigtap.wsdl.ResultadosDetalhaProcedimentosType.class,
                com.upsaude.integration.sigtap.wsdl.ResultadosPesquisaCompatibilidadesType.class
        );
        // Habilita suporte a JAXBElement (necessário para alguns tipos gerados pelo wsimport)
        marshaller.setMtomEnabled(false);
        // Processa schemas aninhados (imports de XSDs)
        marshaller.setProcessExternalEntities(false);
        try {
            marshaller.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao inicializar JAXB marshaller para SIGTAP. " +
                    "Verifique se as classes foram geradas corretamente via wsimport. Erro: " + e.getMessage(), e);
        }
        return marshaller;
    }

    @Bean
    public HttpUrlConnectionMessageSender sigtapMessageSender() {
        HttpUrlConnectionMessageSender sender = new HttpUrlConnectionMessageSender();
        sender.setConnectionTimeout(Duration.ofMillis(properties.getSoap().getConnectTimeoutMs()));
        sender.setReadTimeout(Duration.ofMillis(properties.getSoap().getReadTimeoutMs()));
        return sender;
    }

    @Bean
    public Wss4jSecurityInterceptor sigtapSecurityInterceptor() throws WSSecurityException {
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
    public ClientInterceptor sigtapLoggingInterceptor() {
        return new SigtapSoapLoggingInterceptor();
    }

    @Bean
    public WebServiceTemplate sigtapWebServiceTemplate(
            SaajSoapMessageFactory sigtapSoapMessageFactory,
            Jaxb2Marshaller sigtapMarshaller,
            HttpUrlConnectionMessageSender sigtapMessageSender,
            Wss4jSecurityInterceptor sigtapSecurityInterceptor,
            ClientInterceptor sigtapLoggingInterceptor
    ) {
        WebServiceTemplate template = new WebServiceTemplate(sigtapSoapMessageFactory);
        template.setMarshaller(sigtapMarshaller);
        template.setUnmarshaller(sigtapMarshaller);
        template.setMessageSender(sigtapMessageSender);
        template.setInterceptors(new ClientInterceptor[]{sigtapSecurityInterceptor, sigtapLoggingInterceptor});
        return template;
    }
}

