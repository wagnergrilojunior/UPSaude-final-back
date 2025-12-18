package com.upsaude.integration.sigtap.soap.client;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.upsaude.config.SigtapProperties;
import com.upsaude.integration.sigtap.wsdl.CategoriaDetalheAdicionalType;
import com.upsaude.integration.sigtap.wsdl.DetalheAdicionalType;
import com.upsaude.integration.sigtap.wsdl.PaginacaoType;
import com.upsaude.integration.sigtap.wsdl.RequestDetalharProcedimento;
import com.upsaude.integration.sigtap.wsdl.RequestPesquisarProcedimentos;
import com.upsaude.integration.sigtap.wsdl.ResponseDetalharProcedimento;
import com.upsaude.integration.sigtap.wsdl.ResponsePesquisarProcedimentos;

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

