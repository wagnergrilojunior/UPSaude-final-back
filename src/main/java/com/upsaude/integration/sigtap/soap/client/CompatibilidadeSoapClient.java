package com.upsaude.integration.sigtap.soap.client;

import java.math.BigInteger;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.beans.factory.annotation.Qualifier;

import com.upsaude.config.SigtapProperties;
import com.upsaude.integration.sigtap.wsdl.PaginacaoType;
import com.upsaude.integration.sigtap.wsdl.RequestPesquisarCompatibilidades;
import com.upsaude.integration.sigtap.wsdl.ResponsePesquisarCompatibilidades;

@Component
public class CompatibilidadeSoapClient extends AbstractSigtapSoapClient {

    private final SigtapProperties properties;

    public CompatibilidadeSoapClient(@Qualifier("sigtapWebServiceTemplate") WebServiceTemplate webServiceTemplate,
            SigtapProperties properties) {
        super(webServiceTemplate);
        this.properties = properties;
    }

    public ResponsePesquisarCompatibilidades pesquisarCompatibilidades(
            String codigoCompatibilidadePossivel,
            String competencia,
            String codigoGrupo,
            String codigoSubgrupo,
            String codigoProcedimento,
            int registroInicial,
            int quantidadeRegistros) {
        RequestPesquisarCompatibilidades request = new RequestPesquisarCompatibilidades();
        request.setCodigoCompatibilidadePossivel(codigoCompatibilidadePossivel);
        request.setCompetencia(competencia);
        request.setCodigoGrupo(codigoGrupo);
        request.setCodigoSubgrupo(codigoSubgrupo);
        request.setCodigoProcedimento(codigoProcedimento);

        PaginacaoType pag = new PaginacaoType();
        pag.setRegistroInicial(BigInteger.valueOf(registroInicial));
        pag.setQuantidadeRegistros(quantidadeRegistros);
        request.setPaginacao(pag);

        return call(properties.getSoap().compatibilidadeEndpoint(), request, ResponsePesquisarCompatibilidades.class,
                "pesquisarCompatibilidades");
    }
}
