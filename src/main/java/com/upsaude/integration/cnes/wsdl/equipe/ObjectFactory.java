
package com.upsaude.integration.cnes.wsdl.equipe;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.upsaude.integration.cnes.wsdl.equipe package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _CodigoCNES_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/codigocnes", "CodigoCNES");
    private static final QName _Mensagem_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/mensagem", "Mensagem");
    private static final QName _Equipes_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/equipe", "Equipes");
    private static final QName _Paginacao_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/v1/paginacao", "Paginacao");
    private static final QName _DadosBasicosProfissionais_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosprofissional", "DadosBasicosProfissionais");
    private static final QName _DadosBasicosProfissional_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosprofissional", "DadosBasicosProfissional");
    private static final QName _CNS_QNAME = new QName("http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", "CNS");
    private static final QName _ListaCNS_QNAME = new QName("http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", "ListaCNS");
    private static final QName _CPF_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/documento/v1r2/cpf", "CPF");
    private static final QName _NomeCompleto_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/pessoafisica/v1r2/nomecompleto", "NomeCompleto");
    private static final QName _MSFalhaIdentificador_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/msfalha", "identificador");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.upsaude.integration.cnes.wsdl.equipe
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RequestPesquisarEquipe }
     * 
     * @return
     *     the new instance of {@link RequestPesquisarEquipe }
     */
    public RequestPesquisarEquipe createRequestPesquisarEquipe() {
        return new RequestPesquisarEquipe();
    }

    /**
     * Create an instance of {@link CodigoCNESType }
     * 
     * @return
     *     the new instance of {@link CodigoCNESType }
     */
    public CodigoCNESType createCodigoCNESType() {
        return new CodigoCNESType();
    }

    /**
     * Create an instance of {@link PaginacaoType }
     * 
     * @return
     *     the new instance of {@link PaginacaoType }
     */
    public PaginacaoType createPaginacaoType() {
        return new PaginacaoType();
    }

    /**
     * Create an instance of {@link ResponsePesquisarEquipe }
     * 
     * @return
     *     the new instance of {@link ResponsePesquisarEquipe }
     */
    public ResponsePesquisarEquipe createResponsePesquisarEquipe() {
        return new ResponsePesquisarEquipe();
    }

    /**
     * Create an instance of {@link EquipesType }
     * 
     * @return
     *     the new instance of {@link EquipesType }
     */
    public EquipesType createEquipesType() {
        return new EquipesType();
    }

    /**
     * Create an instance of {@link RequestDetalharEquipe }
     * 
     * @return
     *     the new instance of {@link RequestDetalharEquipe }
     */
    public RequestDetalharEquipe createRequestDetalharEquipe() {
        return new RequestDetalharEquipe();
    }

    /**
     * Create an instance of {@link ResponseDetalharEquipe }
     * 
     * @return
     *     the new instance of {@link ResponseDetalharEquipe }
     */
    public ResponseDetalharEquipe createResponseDetalharEquipe() {
        return new ResponseDetalharEquipe();
    }

    /**
     * Create an instance of {@link DadosBasicosProfissionaisType }
     * 
     * @return
     *     the new instance of {@link DadosBasicosProfissionaisType }
     */
    public DadosBasicosProfissionaisType createDadosBasicosProfissionaisType() {
        return new DadosBasicosProfissionaisType();
    }

    /**
     * Create an instance of {@link MSFalha }
     * 
     * @return
     *     the new instance of {@link MSFalha }
     */
    public MSFalha createMSFalha() {
        return new MSFalha();
    }

    /**
     * Create an instance of {@link MensagemType }
     * 
     * @return
     *     the new instance of {@link MensagemType }
     */
    public MensagemType createMensagemType() {
        return new MensagemType();
    }

    /**
     * Create an instance of {@link EquipeType }
     * 
     * @return
     *     the new instance of {@link EquipeType }
     */
    public EquipeType createEquipeType() {
        return new EquipeType();
    }

    /**
     * Create an instance of {@link DadosBasicosProfissionalType }
     * 
     * @return
     *     the new instance of {@link DadosBasicosProfissionalType }
     */
    public DadosBasicosProfissionalType createDadosBasicosProfissionalType() {
        return new DadosBasicosProfissionalType();
    }

    /**
     * Create an instance of {@link CNSType }
     * 
     * @return
     *     the new instance of {@link CNSType }
     */
    public CNSType createCNSType() {
        return new CNSType();
    }

    /**
     * Create an instance of {@link ListaCNSType }
     * 
     * @return
     *     the new instance of {@link ListaCNSType }
     */
    public ListaCNSType createListaCNSType() {
        return new ListaCNSType();
    }

    /**
     * Create an instance of {@link NumeroCNSType }
     * 
     * @return
     *     the new instance of {@link NumeroCNSType }
     */
    public NumeroCNSType createNumeroCNSType() {
        return new NumeroCNSType();
    }

    /**
     * Create an instance of {@link CPFType }
     * 
     * @return
     *     the new instance of {@link CPFType }
     */
    public CPFType createCPFType() {
        return new CPFType();
    }

    /**
     * Create an instance of {@link NomeCompletoType }
     * 
     * @return
     *     the new instance of {@link NomeCompletoType }
     */
    public NomeCompletoType createNomeCompletoType() {
        return new NomeCompletoType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodigoCNESType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CodigoCNESType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/codigocnes", name = "CodigoCNES")
    public JAXBElement<CodigoCNESType> createCodigoCNES(CodigoCNESType value) {
        return new JAXBElement<>(_CodigoCNES_QNAME, CodigoCNESType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MensagemType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MensagemType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/mensagem", name = "Mensagem")
    public JAXBElement<MensagemType> createMensagem(MensagemType value) {
        return new JAXBElement<>(_Mensagem_QNAME, MensagemType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EquipesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EquipesType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/equipe", name = "Equipes")
    public JAXBElement<EquipesType> createEquipes(EquipesType value) {
        return new JAXBElement<>(_Equipes_QNAME, EquipesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaginacaoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PaginacaoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1/paginacao", name = "Paginacao")
    public JAXBElement<PaginacaoType> createPaginacao(PaginacaoType value) {
        return new JAXBElement<>(_Paginacao_QNAME, PaginacaoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DadosBasicosProfissionaisType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DadosBasicosProfissionaisType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosprofissional", name = "DadosBasicosProfissionais")
    public JAXBElement<DadosBasicosProfissionaisType> createDadosBasicosProfissionais(DadosBasicosProfissionaisType value) {
        return new JAXBElement<>(_DadosBasicosProfissionais_QNAME, DadosBasicosProfissionaisType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DadosBasicosProfissionalType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DadosBasicosProfissionalType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosprofissional", name = "DadosBasicosProfissional")
    public JAXBElement<DadosBasicosProfissionalType> createDadosBasicosProfissional(DadosBasicosProfissionalType value) {
        return new JAXBElement<>(_DadosBasicosProfissional_QNAME, DadosBasicosProfissionalType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CNSType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CNSType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", name = "CNS")
    public JAXBElement<CNSType> createCNS(CNSType value) {
        return new JAXBElement<>(_CNS_QNAME, CNSType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaCNSType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListaCNSType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", name = "ListaCNS")
    public JAXBElement<ListaCNSType> createListaCNS(ListaCNSType value) {
        return new JAXBElement<>(_ListaCNS_QNAME, ListaCNSType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CPFType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CPFType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/documento/v1r2/cpf", name = "CPF")
    public JAXBElement<CPFType> createCPF(CPFType value) {
        return new JAXBElement<>(_CPF_QNAME, CPFType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/pessoafisica/v1r2/nomecompleto", name = "NomeCompleto")
    public JAXBElement<String> createNomeCompleto(String value) {
        return new JAXBElement<>(_NomeCompleto_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/msfalha", name = "identificador", scope = MSFalha.class)
    public JAXBElement<String> createMSFalhaIdentificador(String value) {
        return new JAXBElement<>(_MSFalhaIdentificador_QNAME, String.class, MSFalha.class, value);
    }

}
