package com.upsaude.integration.cnes.soap.client;

import com.upsaude.config.CnesProperties;
import com.upsaude.integration.cnes.wsdl.profissional.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Cliente SOAP para serviços de Profissional de Saúde do CNES.
 */
@Component
@Slf4j
public class ProfissionalCnesSoapClient extends AbstractCnesSoapClient {

    private final CnesProperties properties;

    public ProfissionalCnesSoapClient(WebServiceTemplate webServiceTemplate, CnesProperties properties) {
        super(webServiceTemplate);
        this.properties = properties;
    }

    /**
     * Consulta profissional por CNS (Cartão Nacional de Saúde).
     * 
     * @param numeroCns Número do CNS (15 dígitos)
     * @return Resposta com dados do profissional
     */
    public ResponseConsultarProfissionalSaude consultarProfissionalPorCns(String numeroCns) {
        log.debug("Consultando profissional por CNS: {}", numeroCns);
        
        ObjectFactory objectFactory = new ObjectFactory();
        RequestConsultarProfissionalSaude request = objectFactory.createRequestConsultarProfissionalSaude();
        
        FiltroPesquisaProfissionalSaudeType filtro = new FiltroPesquisaProfissionalSaudeType();
        CNSType cns = new CNSType();
        cns.setNumeroCNS(numeroCns);
        filtro.setCNS(cns);
        request.setFiltroPesquisaProfissionalSaude(filtro);
        
        return call(properties.getSoap().profissionalServiceEndpoint(), request, 
                ResponseConsultarProfissionalSaude.class, "consultarProfissionalPorCns");
    }

    /**
     * Consulta profissional por CPF.
     * 
     * @param numeroCpf Número do CPF (11 dígitos)
     * @return Resposta com dados do profissional
     */
    public ResponseConsultarProfissionalSaude consultarProfissionalPorCpf(String numeroCpf) {
        log.debug("Consultando profissional por CPF: {}", numeroCpf);
        
        ObjectFactory objectFactory = new ObjectFactory();
        RequestConsultarProfissionalSaude request = objectFactory.createRequestConsultarProfissionalSaude();
        
        FiltroPesquisaProfissionalSaudeType filtro = new FiltroPesquisaProfissionalSaudeType();
        CPFType cpf = new CPFType();
        cpf.setNumeroCPF(numeroCpf);
        filtro.setCPF(cpf);
        request.setFiltroPesquisaProfissionalSaude(filtro);
        
        return call(properties.getSoap().profissionalServiceEndpoint(), request, 
                ResponseConsultarProfissionalSaude.class, "consultarProfissionalPorCpf");
    }

    /**
     * Consulta profissionais de um estabelecimento usando CNES.
     * 
     * @param codigoCnes Código CNES do estabelecimento
     * @return Resposta com lista de profissionais
     */
    public ResponseConsultarProfissionaisSaude consultarProfissionais(String codigoCnes) {
        log.debug("Consultando profissionais do estabelecimento CNES: {}", codigoCnes);
        
        ObjectFactory objectFactory = new ObjectFactory();
        RequestConsultarProfissionaisSaude request = objectFactory.createRequestConsultarProfissionaisSaude();
        
        FiltroPesquisaEstabelecimentoSaudeType filtro = new FiltroPesquisaEstabelecimentoSaudeType();
        CodigoCNESType codigoCNESType = new CodigoCNESType();
        codigoCNESType.setCodigo(codigoCnes);
        filtro.setCodigoCNES(codigoCNESType);
        request.setFiltroPesquisaEstabelecimentoSaude(filtro);
        
        return call(properties.getSoap().profissionalServiceEndpoint(), request, 
                ResponseConsultarProfissionaisSaude.class, "consultarProfissionais");
    }
}

