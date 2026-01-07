package com.upsaude.integration.cnes.soap.client;

import com.upsaude.config.CnesProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Cliente SOAP para serviços de Estabelecimento de Saúde do CNES.
 * 
 * <p>
 * Implementa chamadas aos serviços:
 * <ul>
 * <li>EstabelecimentoSaudeService v1r0</li>
 * <li>CnesService v1r0</li>
 * </ul>
 */
@Component
@Lazy
@Slf4j
public class EstabelecimentoCnesSoapClient extends AbstractCnesSoapClient {

        private final CnesProperties properties;
        private final WebServiceTemplate v1r0Template;

        public EstabelecimentoCnesSoapClient(
                        @Qualifier("cnesServiceTemplate") WebServiceTemplate webServiceTemplate,
                        @Qualifier("estabelecimentoV1r0Template") WebServiceTemplate v1r0Template,
                        CnesProperties properties) {
                super(webServiceTemplate);
                this.v1r0Template = v1r0Template;
                this.properties = properties;
        }

        /**
         * Consulta estabelecimento por código CNES usando CnesService v1r0.
         * 
         * @param codigoCnes Código CNES do estabelecimento (7 dígitos)
         * @return Resposta com dados do estabelecimento
         */
        public com.upsaude.integration.cnes.wsdl.cnesservice.ResponseConsultarEstabelecimentoSaude consultarEstabelecimentoPorCnes(
                        String codigoCnes) {
                log.debug("Consultando estabelecimento CNES: {}", codigoCnes);

                com.upsaude.integration.cnes.wsdl.cnesservice.ObjectFactory objectFactory = new com.upsaude.integration.cnes.wsdl.cnesservice.ObjectFactory();
                com.upsaude.integration.cnes.wsdl.cnesservice.RequestConsultarEstabelecimentoSaude request = objectFactory
                                .createRequestConsultarEstabelecimentoSaude();

                com.upsaude.integration.cnes.wsdl.cnesservice.CodigoCNESType codigoCNESType = objectFactory
                                .createCodigoCNESType();
                codigoCNESType.setCodigo(codigoCnes);
                request.setCodigoCNES(codigoCNESType);

                return call(properties.getSoap().cnesServiceEndpoint(), request,
                                com.upsaude.integration.cnes.wsdl.cnesservice.ResponseConsultarEstabelecimentoSaude.class,
                                "consultarEstabelecimentoPorCnes");
        }

        /**
         * Consulta estabelecimentos por município usando CnesService v1r0.
         * 
         * @param codigoMunicipio Código do município (IBGE)
         * @param competencia     Competência no formato AAAAMM
         * @return Resposta com lista de estabelecimentos
         */
        public com.upsaude.integration.cnes.wsdl.cnesservice.ResponseConsultarEstabelecimentoSaudePorMunicipio consultarEstabelecimentosPorMunicipio(
                        String codigoMunicipio, String competencia) {
                log.debug("Consultando estabelecimentos por município: {}, competência: {}", codigoMunicipio,
                                competencia);

                com.upsaude.integration.cnes.wsdl.cnesservice.ObjectFactory objectFactory = new com.upsaude.integration.cnes.wsdl.cnesservice.ObjectFactory();
                com.upsaude.integration.cnes.wsdl.cnesservice.RequestConsultarEstabelecimentoSaudePorMunicipio request = objectFactory
                                .createRequestConsultarEstabelecimentoSaudePorMunicipio();

                com.upsaude.integration.cnes.wsdl.cnesservice.MunicipioType municipio = objectFactory
                                .createMunicipioType();
                municipio.setCodigoMunicipio(codigoMunicipio);
                request.setMunicipio(municipio);

                return call(properties.getSoap().cnesServiceEndpoint(), request,
                                com.upsaude.integration.cnes.wsdl.cnesservice.ResponseConsultarEstabelecimentoSaudePorMunicipio.class,
                                "consultarEstabelecimentosPorMunicipio");
        }

        /**
         * Consulta dados complementares de estabelecimento usando CnesService v1r0.
         * 
         * @param codigoMunicipio Código do município (IBGE)
         * @param competencia     Competência no formato AAAAMM
         * @return Resposta com dados complementares
         */
        public com.upsaude.integration.cnes.wsdl.cnesservice.ResponseConsultarDadosComplementaresEstabelecimentoSaude consultarDadosComplementares(
                        String codigoMunicipio, String competencia) {
                log.debug("Consultando dados complementares - município: {}, competência: {}", codigoMunicipio,
                                competencia);

                com.upsaude.integration.cnes.wsdl.cnesservice.ObjectFactory objectFactory = new com.upsaude.integration.cnes.wsdl.cnesservice.ObjectFactory();
                com.upsaude.integration.cnes.wsdl.cnesservice.RequestConsultarDadosComplementaresEstabelecimentoSaude request = objectFactory
                                .createRequestConsultarDadosComplementaresEstabelecimentoSaude();

                com.upsaude.integration.cnes.wsdl.cnesservice.MunicipioType municipio = objectFactory
                                .createMunicipioType();
                municipio.setCodigoMunicipio(codigoMunicipio);
                request.setMunicipioCNES(municipio);

                com.upsaude.integration.cnes.wsdl.cnesservice.CmptType cmpt = objectFactory.createCmptType();
                cmpt.setCmpt(competencia);
                request.setCmpt(cmpt);

                return call(properties.getSoap().cnesServiceEndpoint(), request,
                                com.upsaude.integration.cnes.wsdl.cnesservice.ResponseConsultarDadosComplementaresEstabelecimentoSaude.class,
                                "consultarDadosComplementares");
        }

        /**
         * Consulta estabelecimento usando EstabelecimentoSaudeService v1r0.
         * 
         * @param codigoCnes Código CNES do estabelecimento (7 dígitos)
         * @return Resposta com dados do estabelecimento
         */
        public com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0.ResponseConsultarEstabelecimentoSaude consultarEstabelecimentoV1r0(
                        String codigoCnes) {
                log.debug("Consultando estabelecimento CNES v1r0: {}", codigoCnes);

                com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0.ObjectFactory objectFactory = new com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0.ObjectFactory();
                com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0.RequestConsultarEstabelecimentoSaude request = objectFactory
                                .createRequestConsultarEstabelecimentoSaude();

                com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0.FiltroPesquisaEstabelecimentoSaudeType filtro = new com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0.FiltroPesquisaEstabelecimentoSaudeType();
                com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0.CodigoCNESType codigoCNESType = new com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0.CodigoCNESType();
                codigoCNESType.setCodigo(codigoCnes);
                filtro.setCodigoCNES(codigoCNESType);
                request.setFiltroPesquisaEstabelecimentoSaude(filtro);

                try {
                        Object raw = v1r0Template.marshalSendAndReceive(
                                        properties.getSoap().estabelecimentoServiceV1r0Endpoint(),
                                        request);
                        if (raw instanceof com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0.ResponseConsultarEstabelecimentoSaude resp) {
                                return resp;
                        } else if (raw instanceof jakarta.xml.bind.JAXBElement<?> jaxb) {
                                return (com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0.ResponseConsultarEstabelecimentoSaude) jaxb
                                                .getValue();
                        }
                        throw new RuntimeException("Resposta inesperada v1r0: "
                                        + (raw == null ? "null" : raw.getClass().getName()));
                } catch (Exception e) {
                        log.error("Erro ao consultar estabelecimento v1r0", e);
                        throw new RuntimeException("Erro ao consultar estabelecimento v1r0", e);
                }
        }
}
