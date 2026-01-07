package com.upsaude.integration.cnes.soap.client;

import com.upsaude.config.CnesProperties;
import com.upsaude.integration.cnes.wsdl.leito.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Cliente SOAP para serviços de Leito do CNES.
 */
@Component
@Lazy
@Slf4j
public class LeitoCnesSoapClient extends AbstractCnesSoapClient {

    private final CnesProperties properties;

    public LeitoCnesSoapClient(@Qualifier("leitoTemplate") WebServiceTemplate webServiceTemplate,
            CnesProperties properties) {
        super(webServiceTemplate);
        this.properties = properties;
    }

    /**
     * Consulta leitos de um estabelecimento.
     * 
     * @param codigoCnes Código CNES do estabelecimento
     * @return Resposta com lista de leitos
     */
    public ResponseConsultarLeitosCNES consultarLeitos(String codigoCnes) {
        log.debug("Consultando leitos do estabelecimento CNES: {}", codigoCnes);

        ObjectFactory objectFactory = new ObjectFactory();
        RequestConsultarLeitosCNES request = objectFactory.createRequestConsultarLeitosCNES();

        CodigoCNESType codigoCNESType = new CodigoCNESType();
        codigoCNESType.setCodigo(codigoCnes);
        request.setCodigoCNES(codigoCNESType);

        return call(properties.getSoap().leitoServiceEndpoint(), request,
                ResponseConsultarLeitosCNES.class, "consultarLeitos");
    }
}
