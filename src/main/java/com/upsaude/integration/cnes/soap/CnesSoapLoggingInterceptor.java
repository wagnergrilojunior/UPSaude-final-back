package com.upsaude.integration.cnes.soap;

import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CnesSoapLoggingInterceptor implements ClientInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        if (log.isDebugEnabled()) {
            log.debug("CNES SOAP request payload={}", payloadToString(messageContext.getRequest()));
        } else {
            log.info("CNES SOAP request enviado");
        }
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        if (log.isDebugEnabled()) {
            log.debug("CNES SOAP response payload={}", payloadToString(messageContext.getResponse()));
        } else {
            log.info("CNES SOAP response recebido");
        }
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        log.warn("CNES SOAP fault payload={}", payloadToString(messageContext.getResponse()));
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {
        if (ex != null) {
            log.error("CNES SOAP erro de transporte/processamento", ex);
        }
    }

    private String payloadToString(org.springframework.ws.WebServiceMessage message) {
        try {
            Source source = message.getPayloadSource();
            if (source == null) {
                if (message instanceof SoapMessage soapMessage) {
                    source = soapMessage.getEnvelope().getSource();
                } else {
                    return "<payload-vazio/>";
                }
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            return "<erro-serializando-payload/>";
        }
    }
}

