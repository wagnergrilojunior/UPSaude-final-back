package com.upsaude.integration.cnes.soap.client;

import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.upsaude.exception.CnesSoapException;

import jakarta.xml.bind.JAXBElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractCnesSoapClient {

    protected final WebServiceTemplate webServiceTemplate;

    protected <T> T call(String endpoint, Object request, Class<T> responseType, String operacao) {
        try {
            Object raw = webServiceTemplate.marshalSendAndReceive(endpoint, request);
            Object value = (raw instanceof JAXBElement<?> jaxb) ? jaxb.getValue() : raw;
            if (responseType.isInstance(value)) {
                return responseType.cast(value);
            }
            throw new CnesSoapException("Resposta SOAP inesperada na operação " + operacao
                    + " (tipo=" + (value == null ? "null" : value.getClass().getName()) + ")");
        } catch (SoapFaultClientException e) {
            log.warn("CNES SOAP fault operacao={} endpoint={} faultCode={} faultString={}",
                    operacao, endpoint, e.getFaultCode(), e.getFaultStringOrReason());
            throw new CnesSoapException("Fault SOAP do CNES na operação " + operacao, e);
        } catch (WebServiceIOException e) {
            throw new CnesSoapException("Falha de transporte/timeout no CNES na operação " + operacao, e);
        } catch (Exception e) {
            throw new CnesSoapException("Falha ao executar operação CNES " + operacao, e);
        }
    }
}

