package com.upsaude.integration.sigtap.soap.client;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.beans.factory.annotation.Qualifier;

import com.upsaude.config.SigtapProperties;
import com.upsaude.integration.sigtap.wsdl.RequestListarFormaOrganizacao;
import com.upsaude.integration.sigtap.wsdl.RequestListarGrupos;
import com.upsaude.integration.sigtap.wsdl.RequestListarSubgrupos;
import com.upsaude.integration.sigtap.wsdl.ResponseListarFormaOrganizacao;
import com.upsaude.integration.sigtap.wsdl.ResponseListarGrupos;
import com.upsaude.integration.sigtap.wsdl.ResponseListarSubgrupos;

@Component
@Lazy
public class NivelAgregacaoSoapClient extends AbstractSigtapSoapClient {

    private final SigtapProperties properties;

    public NivelAgregacaoSoapClient(@Qualifier("sigtapWebServiceTemplate") WebServiceTemplate webServiceTemplate,
            SigtapProperties properties) {
        super(webServiceTemplate);
        this.properties = properties;
    }

    public ResponseListarGrupos listarGrupos() {
        RequestListarGrupos request = new RequestListarGrupos();
        return call(properties.getSoap().nivelAgregacaoEndpoint(), request, ResponseListarGrupos.class, "listarGrupos");
    }

    public ResponseListarSubgrupos listarSubgrupos(String codigoGrupo) {
        RequestListarSubgrupos request = new RequestListarSubgrupos();
        request.setCodigoGrupo(codigoGrupo);
        return call(properties.getSoap().nivelAgregacaoEndpoint(), request, ResponseListarSubgrupos.class,
                "listarSubgrupos");
    }

    public ResponseListarFormaOrganizacao listarFormaOrganizacao(String codigoSubgrupo) {
        RequestListarFormaOrganizacao request = new RequestListarFormaOrganizacao();
        request.setCodigoSubgrupo(codigoSubgrupo);
        return call(properties.getSoap().nivelAgregacaoEndpoint(), request, ResponseListarFormaOrganizacao.class,
                "listarFormaOrganizacao");
    }
}
