package com.upsaude.integration.sigtap.soap.client;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.upsaude.config.SigtapProperties;
import com.upsaude.integration.sigtap.wsdl.RequestListarCompatibilidadesPossiveis;
import com.upsaude.integration.sigtap.wsdl.ResponseListarCompatibilidadesPossiveis;

@Component
@Lazy
public class CompatibilidadePossivelSoapClient extends AbstractSigtapSoapClient {

    private final SigtapProperties properties;

    public CompatibilidadePossivelSoapClient(
            @org.springframework.beans.factory.annotation.Qualifier("sigtapWebServiceTemplate") WebServiceTemplate webServiceTemplate,
            SigtapProperties properties) {
        super(webServiceTemplate);
        this.properties = properties;
    }

    public ResponseListarCompatibilidadesPossiveis listarCompatibilidadesPossiveis() {
        RequestListarCompatibilidadesPossiveis request = new RequestListarCompatibilidadesPossiveis();
        return call(properties.getSoap().compatibilidadePossivelEndpoint(), request,
                ResponseListarCompatibilidadesPossiveis.class, "listarCompatibilidadesPossiveis");
    }
}
