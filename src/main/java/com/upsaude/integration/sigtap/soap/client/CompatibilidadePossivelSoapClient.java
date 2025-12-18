package com.upsaude.integration.sigtap.soap.client;

import com.upsaude.config.SigtapProperties;
import com.upsaude.integration.sigtap.wsdl.RequestListarCompatibilidadesPossiveis;
import com.upsaude.integration.sigtap.wsdl.ResponseListarCompatibilidadesPossiveis;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Client SOAP do CompatibilidadePossivelService (SIGTAP).
 */
@Component
public class CompatibilidadePossivelSoapClient extends AbstractSigtapSoapClient {

    private final SigtapProperties properties;

    public CompatibilidadePossivelSoapClient(WebServiceTemplate webServiceTemplate, SigtapProperties properties) {
        super(webServiceTemplate);
        this.properties = properties;
    }

    public ResponseListarCompatibilidadesPossiveis listarCompatibilidadesPossiveis() {
        RequestListarCompatibilidadesPossiveis request = new RequestListarCompatibilidadesPossiveis();
        return call(properties.getSoap().compatibilidadePossivelEndpoint(), request, ResponseListarCompatibilidadesPossiveis.class, "listarCompatibilidadesPossiveis");
    }
}

