package com.upsaude.integration.cnes.soap.client;

import com.upsaude.config.CnesProperties;
import com.upsaude.integration.cnes.wsdl.equipe.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.math.BigInteger;

/**
 * Cliente SOAP para serviços de Equipe de Saúde do CNES.
 */
@Component
@Lazy
@Slf4j
public class EquipeCnesSoapClient extends AbstractCnesSoapClient {

    private final CnesProperties properties;

    public EquipeCnesSoapClient(@Qualifier("equipeTemplate") WebServiceTemplate webServiceTemplate,
            CnesProperties properties) {
        super(webServiceTemplate);
        this.properties = properties;
    }

    /**
     * Pesquisa equipes de um estabelecimento.
     * 
     * @param codigoCnes          Código CNES do estabelecimento
     * @param registroInicial     Registro inicial para paginação (começa em 1)
     * @param quantidadeRegistros Quantidade de registros por página (máximo 100)
     * @return Resposta com lista de equipes
     */
    public ResponsePesquisarEquipe pesquisarEquipes(String codigoCnes, int registroInicial, int quantidadeRegistros) {
        log.debug("Pesquisando equipes do estabelecimento CNES: {}, página: {}, tamanho: {}", codigoCnes,
                registroInicial, quantidadeRegistros);

        ObjectFactory objectFactory = new ObjectFactory();
        RequestPesquisarEquipe request = objectFactory.createRequestPesquisarEquipe();

        CodigoCNESType codigoCNESType = new CodigoCNESType();
        codigoCNESType.setCodigo(codigoCnes);
        request.setCodigoCNES(codigoCNESType);

        PaginacaoType paginacao = new PaginacaoType();
        paginacao.setRegistroInicial(BigInteger.valueOf(registroInicial));
        paginacao.setQuantidadeRegistros(quantidadeRegistros);
        request.setPaginacao(paginacao);

        return call(properties.getSoap().equipeServiceEndpoint(), request,
                ResponsePesquisarEquipe.class, "pesquisarEquipes");
    }

    /**
     * Detalha uma equipe específica.
     * 
     * @param codigoIne Identificador Nacional de Equipes (INE)
     * @return Resposta com detalhes da equipe
     */
    public ResponseDetalharEquipe detalharEquipe(String codigoIne) {
        log.debug("Detalhando equipe INE: {}", codigoIne);

        ObjectFactory objectFactory = new ObjectFactory();
        RequestDetalharEquipe request = objectFactory.createRequestDetalharEquipe();
        request.setCodigoEquipe(codigoIne);

        return call(properties.getSoap().equipeServiceEndpoint(), request,
                ResponseDetalharEquipe.class, "detalharEquipe");
    }
}
