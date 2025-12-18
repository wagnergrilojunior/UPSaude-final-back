package com.upsaude.integration.sigtap.soap.client;

import com.upsaude.config.SigtapProperties;
import com.upsaude.integration.sigtap.wsdl.CategoriaDetalheAdicionalType;
import com.upsaude.integration.sigtap.wsdl.DetalheAdicionalType;
import com.upsaude.integration.sigtap.wsdl.PaginacaoType;
import com.upsaude.integration.sigtap.wsdl.RequestDetalharProcedimento;
import com.upsaude.integration.sigtap.wsdl.RequestPesquisarProcedimentos;
import com.upsaude.integration.sigtap.wsdl.ResponseDetalharProcedimento;
import com.upsaude.integration.sigtap.wsdl.ResponsePesquisarProcedimentos;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * Client SOAP do ProcedimentoService (SIGTAP).
 */
@Component
public class ProcedimentoSoapClient extends AbstractSigtapSoapClient {

    private final SigtapProperties properties;

    public ProcedimentoSoapClient(WebServiceTemplate webServiceTemplate, SigtapProperties properties) {
        super(webServiceTemplate);
        this.properties = properties;
    }

    public ResponsePesquisarProcedimentos pesquisarProcedimentos(
            String codigoGrupo,
            String codigoSubgrupo,
            String competencia,
            int registroInicial,
            int quantidadeRegistros
    ) {
        RequestPesquisarProcedimentos request = new RequestPesquisarProcedimentos();
        request.setCodigoGrupo(codigoGrupo);
        request.setCodigoSubgrupo(codigoSubgrupo);
        request.setCompetencia(competencia);

        PaginacaoType paginacao = new PaginacaoType();
        paginacao.setRegistroInicial(BigInteger.valueOf(registroInicial));
        paginacao.setQuantidadeRegistros(quantidadeRegistros);
        request.setPaginacao(paginacao);

        return call(properties.getSoap().procedimentoEndpoint(), request, ResponsePesquisarProcedimentos.class, "pesquisarProcedimentos");
    }

    /**
     * Detalha um procedimento, solicitando categorias de detalhe adicionais.
     *
     * <p>Por padr?o, usamos todas as categorias suportadas pelo esquema do DATASUS.
     */
    public ResponseDetalharProcedimento detalharProcedimento(String codigoProcedimento, String competencia) {
        return detalharProcedimento(codigoProcedimento, competencia, categoriasPadraoDetalhe(), 1, 100);
    }

    public ResponseDetalharProcedimento detalharProcedimento(
            String codigoProcedimento,
            String competencia,
            List<CategoriaDetalheAdicionalType> categorias,
            int registroInicial,
            int quantidadeRegistros
    ) {
        RequestDetalharProcedimento request = new RequestDetalharProcedimento();
        request.setCodigoProcedimento(codigoProcedimento);
        request.setCompetencia(competencia);

        RequestDetalharProcedimento.DetalhesAdicionais detalhes = new RequestDetalharProcedimento.DetalhesAdicionais();
        for (CategoriaDetalheAdicionalType categoria : categorias) {
            DetalheAdicionalType detalhe = new DetalheAdicionalType();
            detalhe.setCategoriaDetalheAdicional(categoria);

            // Pagina??o ? opcional, mas ajuda em categorias com listas grandes (CIDs, CBOs etc).
            PaginacaoType pag = new PaginacaoType();
            pag.setRegistroInicial(BigInteger.valueOf(registroInicial));
            pag.setQuantidadeRegistros(quantidadeRegistros);
            detalhe.setPaginacao(pag);

            detalhes.getDetalheAdicional().add(detalhe);
        }
        request.setDetalhesAdicionais(detalhes);

        return call(properties.getSoap().procedimentoEndpoint(), request, ResponseDetalharProcedimento.class, "detalharProcedimento");
    }

    private List<CategoriaDetalheAdicionalType> categoriasPadraoDetalhe() {
        return Arrays.asList(CategoriaDetalheAdicionalType.values());
    }
}

