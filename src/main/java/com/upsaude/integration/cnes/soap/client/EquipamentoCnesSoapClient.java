package com.upsaude.integration.cnes.soap.client;

import com.upsaude.config.CnesProperties;
import com.upsaude.integration.cnes.wsdl.equipamento.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Cliente SOAP para serviços de Equipamento do CNES.
 */
@Component
@Lazy
@Slf4j
public class EquipamentoCnesSoapClient extends AbstractCnesSoapClient {

    private final CnesProperties properties;

    public EquipamentoCnesSoapClient(@Qualifier("equipamentoTemplate") WebServiceTemplate webServiceTemplate,
            CnesProperties properties) {
        super(webServiceTemplate);
        this.properties = properties;
    }

    /**
     * Consulta equipamentos de um estabelecimento.
     * 
     * @param codigoCnes Código CNES do estabelecimento
     * @return Resposta com lista de equipamentos
     */
    public ResponseConsultarEquipamentos consultarEquipamentos(String codigoCnes) {
        log.debug("Consultando equipamentos do estabelecimento CNES: {}", codigoCnes);

        ObjectFactory objectFactory = new ObjectFactory();
        RequestConsultarEquipamentos request = objectFactory.createRequestConsultarEquipamentos();

        CodigoCNESType codigoCNESType = new CodigoCNESType();
        codigoCNESType.setCodigo(codigoCnes);
        request.setCodigoCNES(codigoCNESType);

        return call(properties.getSoap().equipamentoServiceEndpoint(), request,
                ResponseConsultarEquipamentos.class, "consultarEquipamentos");
    }
}
