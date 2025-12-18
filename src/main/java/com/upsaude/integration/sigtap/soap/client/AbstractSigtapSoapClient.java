package com.upsaude.integration.sigtap.soap.client;

import com.upsaude.exception.SigtapSoapException;
import jakarta.xml.bind.JAXBElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

/**
 * Base comum para clients SOAP do SIGTAP.
 *
 * <p>Centraliza:
 * <ul>
 *   <li>Envio/recebimento via WebServiceTemplate</li>
 *   <li>Unwrap de JAXBElement</li>
 *   <li>Tratamento consistente de falhas</li>
 * </ul>
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractSigtapSoapClient {

    protected final WebServiceTemplate webServiceTemplate;

    protected <T> T call(String endpoint, Object request, Class<T> responseType, String operacao) {
        try {
            Object raw = webServiceTemplate.marshalSendAndReceive(endpoint, request);
            Object value = (raw instanceof JAXBElement<?> jaxb) ? jaxb.getValue() : raw;
            if (responseType.isInstance(value)) {
                return responseType.cast(value);
            }
            throw new SigtapSoapException("Resposta SOAP inesperada na opera??o " + operacao
                    + " (tipo=" + (value == null ? "null" : value.getClass().getName()) + ")");
        } catch (SoapFaultClientException e) {
            log.warn("SIGTAP SOAP fault operacao={} endpoint={} faultCode={} faultString={}",
                    operacao, endpoint, e.getFaultCode(), e.getFaultStringOrReason());
            throw new SigtapSoapException("Fault SOAP do SIGTAP na opera??o " + operacao, e);
        } catch (WebServiceIOException e) {
            throw new SigtapSoapException("Falha de transporte/timeout no SIGTAP na opera??o " + operacao, e);
        } catch (Exception e) {
            throw new SigtapSoapException("Falha ao executar opera??o SIGTAP " + operacao, e);
        }
    }
}

