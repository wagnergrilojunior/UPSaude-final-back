
package com.upsaude.integration.cnes.wsdl.profissional;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.upsaude.integration.cnes.wsdl.profissional package. 
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

    private static final QName _FiltroPesquisaEstabelecimentoSaude_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/v1r0/filtropesquisaestabelecimentosaude", "FiltroPesquisaEstabelecimentoSaude");
    private static final QName _ProfissionalSaude_QNAME = new QName("http://servicos.saude.gov.br/schema/profissionalsaude/v2r0/profissionalsaude", "ProfissionalSaude");
    private static final QName _FiltroPesquisaProfissionalSaude_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/v1r0/filtropesquisaprofissionalsaude", "FiltroPesquisaProfissionalSaude");
    private static final QName _CodigoCNES_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/codigocnes", "CodigoCNES");
    private static final QName _CNPJ_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/cnpj", "CNPJ");
    private static final QName _DadosGeraisEstabelecimentoSaude_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/dadosgeraiscnes", "DadosGeraisEstabelecimentoSaude");
    private static final QName _CodigoUnidade_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/codigounidade", "CodigoUnidade");
    private static final QName _NomeJuridico_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/nomejuridico", "NomeJuridico");
    private static final QName _Endereco_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/endereco/v1r2/endereco", "Endereco");
    private static final QName _Municipio_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/v1r2/municipio", "Municipio");
    private static final QName _UF_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/v1r1/uf", "UF");
    private static final QName _Pais_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/v1r2/pais", "Pais");
    private static final QName _TipoLogradouro_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/tipologradouro", "TipoLogradouro");
    private static final QName _Bairro_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/bairro", "Bairro");
    private static final QName _CEP_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/cep", "CEP");
    private static final QName _EsferaAdministrativa_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/esferaadministrativa", "esferaAdministrativa");
    private static final QName _TipoUnidade_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/tipounidade", "tipoUnidade");
    private static final QName _Diretor_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/diretor", "Diretor");
    private static final QName _CPF_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/documento/v1r2/cpf", "CPF");
    private static final QName _NomeCompleto_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/pessoafisica/v1r2/nomecompleto", "NomeCompleto");
    private static final QName _Telefone_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/telefone/v1r2/telefone", "Telefone");
    private static final QName _TipoTelefone_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/telefone/v1r1/tipotelefone", "TipoTelefone");
    private static final QName _Email_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/v1r2/email", "Email");
    private static final QName _Mensagem_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/mensagem", "Mensagem");
    private static final QName _CBO_QNAME = new QName("http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/cbo", "CBO");
    private static final QName _CNS_QNAME = new QName("http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", "CNS");
    private static final QName _ListaCNS_QNAME = new QName("http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", "ListaCNS");
    private static final QName _DadosBasicosEstabelecimento_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosestabelecimento", "DadosBasicosEstabelecimento");
    private static final QName _MSFalhaIdentificador_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/msfalha", "identificador");
    private static final QName _CNSTypeDataAtribuicao_QNAME = new QName("http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", "dataAtribuicao");
    private static final QName _CNSTypeTipoCartao_QNAME = new QName("http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", "tipoCartao");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.upsaude.integration.cnes.wsdl.profissional
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RequestConsultarProfissionaisSaude }
     * 
     * @return
     *     the new instance of {@link RequestConsultarProfissionaisSaude }
     */
    public RequestConsultarProfissionaisSaude createRequestConsultarProfissionaisSaude() {
        return new RequestConsultarProfissionaisSaude();
    }

    /**
     * Create an instance of {@link FiltroPesquisaEstabelecimentoSaudeType }
     * 
     * @return
     *     the new instance of {@link FiltroPesquisaEstabelecimentoSaudeType }
     */
    public FiltroPesquisaEstabelecimentoSaudeType createFiltroPesquisaEstabelecimentoSaudeType() {
        return new FiltroPesquisaEstabelecimentoSaudeType();
    }

    /**
     * Create an instance of {@link ResponseConsultarProfissionaisSaude }
     * 
     * @return
     *     the new instance of {@link ResponseConsultarProfissionaisSaude }
     */
    public ResponseConsultarProfissionaisSaude createResponseConsultarProfissionaisSaude() {
        return new ResponseConsultarProfissionaisSaude();
    }

    /**
     * Create an instance of {@link ProfissionalSaudeType }
     * 
     * @return
     *     the new instance of {@link ProfissionalSaudeType }
     */
    public ProfissionalSaudeType createProfissionalSaudeType() {
        return new ProfissionalSaudeType();
    }

    /**
     * Create an instance of {@link RequestConsultarProfissionalSaude }
     * 
     * @return
     *     the new instance of {@link RequestConsultarProfissionalSaude }
     */
    public RequestConsultarProfissionalSaude createRequestConsultarProfissionalSaude() {
        return new RequestConsultarProfissionalSaude();
    }

    /**
     * Create an instance of {@link FiltroPesquisaProfissionalSaudeType }
     * 
     * @return
     *     the new instance of {@link FiltroPesquisaProfissionalSaudeType }
     */
    public FiltroPesquisaProfissionalSaudeType createFiltroPesquisaProfissionalSaudeType() {
        return new FiltroPesquisaProfissionalSaudeType();
    }

    /**
     * Create an instance of {@link ResponseConsultarProfissionalSaude }
     * 
     * @return
     *     the new instance of {@link ResponseConsultarProfissionalSaude }
     */
    public ResponseConsultarProfissionalSaude createResponseConsultarProfissionalSaude() {
        return new ResponseConsultarProfissionalSaude();
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
     * Create an instance of {@link CNPJType }
     * 
     * @return
     *     the new instance of {@link CNPJType }
     */
    public CNPJType createCNPJType() {
        return new CNPJType();
    }

    /**
     * Create an instance of {@link DadosGeraisEstabelecimentoSaudeType }
     * 
     * @return
     *     the new instance of {@link DadosGeraisEstabelecimentoSaudeType }
     */
    public DadosGeraisEstabelecimentoSaudeType createDadosGeraisEstabelecimentoSaudeType() {
        return new DadosGeraisEstabelecimentoSaudeType();
    }

    /**
     * Create an instance of {@link CodigoUnidadeType }
     * 
     * @return
     *     the new instance of {@link CodigoUnidadeType }
     */
    public CodigoUnidadeType createCodigoUnidadeType() {
        return new CodigoUnidadeType();
    }

    /**
     * Create an instance of {@link NomeJuridicoType }
     * 
     * @return
     *     the new instance of {@link NomeJuridicoType }
     */
    public NomeJuridicoType createNomeJuridicoType() {
        return new NomeJuridicoType();
    }

    /**
     * Create an instance of {@link EnderecoType }
     * 
     * @return
     *     the new instance of {@link EnderecoType }
     */
    public EnderecoType createEnderecoType() {
        return new EnderecoType();
    }

    /**
     * Create an instance of {@link MunicipioType }
     * 
     * @return
     *     the new instance of {@link MunicipioType }
     */
    public MunicipioType createMunicipioType() {
        return new MunicipioType();
    }

    /**
     * Create an instance of {@link UFType }
     * 
     * @return
     *     the new instance of {@link UFType }
     */
    public UFType createUFType() {
        return new UFType();
    }

    /**
     * Create an instance of {@link PaisType }
     * 
     * @return
     *     the new instance of {@link PaisType }
     */
    public PaisType createPaisType() {
        return new PaisType();
    }

    /**
     * Create an instance of {@link TipoLogradouroType }
     * 
     * @return
     *     the new instance of {@link TipoLogradouroType }
     */
    public TipoLogradouroType createTipoLogradouroType() {
        return new TipoLogradouroType();
    }

    /**
     * Create an instance of {@link BairroType }
     * 
     * @return
     *     the new instance of {@link BairroType }
     */
    public BairroType createBairroType() {
        return new BairroType();
    }

    /**
     * Create an instance of {@link CEPType }
     * 
     * @return
     *     the new instance of {@link CEPType }
     */
    public CEPType createCEPType() {
        return new CEPType();
    }

    /**
     * Create an instance of {@link EsferaAdministrativaType }
     * 
     * @return
     *     the new instance of {@link EsferaAdministrativaType }
     */
    public EsferaAdministrativaType createEsferaAdministrativaType() {
        return new EsferaAdministrativaType();
    }

    /**
     * Create an instance of {@link TipoUnidadeType }
     * 
     * @return
     *     the new instance of {@link TipoUnidadeType }
     */
    public TipoUnidadeType createTipoUnidadeType() {
        return new TipoUnidadeType();
    }

    /**
     * Create an instance of {@link DiretorType }
     * 
     * @return
     *     the new instance of {@link DiretorType }
     */
    public DiretorType createDiretorType() {
        return new DiretorType();
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
     * Create an instance of {@link TelefoneType }
     * 
     * @return
     *     the new instance of {@link TelefoneType }
     */
    public TelefoneType createTelefoneType() {
        return new TelefoneType();
    }

    /**
     * Create an instance of {@link TipoTelefoneType }
     * 
     * @return
     *     the new instance of {@link TipoTelefoneType }
     */
    public TipoTelefoneType createTipoTelefoneType() {
        return new TipoTelefoneType();
    }

    /**
     * Create an instance of {@link EmailType }
     * 
     * @return
     *     the new instance of {@link EmailType }
     */
    public EmailType createEmailType() {
        return new EmailType();
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
     * Create an instance of {@link CBOType }
     * 
     * @return
     *     the new instance of {@link CBOType }
     */
    public CBOType createCBOType() {
        return new CBOType();
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
     * Create an instance of {@link DadosBasicosEstabelecimentoType }
     * 
     * @return
     *     the new instance of {@link DadosBasicosEstabelecimentoType }
     */
    public DadosBasicosEstabelecimentoType createDadosBasicosEstabelecimentoType() {
        return new DadosBasicosEstabelecimentoType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FiltroPesquisaEstabelecimentoSaudeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FiltroPesquisaEstabelecimentoSaudeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/filtropesquisaestabelecimentosaude", name = "FiltroPesquisaEstabelecimentoSaude")
    public JAXBElement<FiltroPesquisaEstabelecimentoSaudeType> createFiltroPesquisaEstabelecimentoSaude(FiltroPesquisaEstabelecimentoSaudeType value) {
        return new JAXBElement<>(_FiltroPesquisaEstabelecimentoSaude_QNAME, FiltroPesquisaEstabelecimentoSaudeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProfissionalSaudeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ProfissionalSaudeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/profissionalsaude/v2r0/profissionalsaude", name = "ProfissionalSaude")
    public JAXBElement<ProfissionalSaudeType> createProfissionalSaude(ProfissionalSaudeType value) {
        return new JAXBElement<>(_ProfissionalSaude_QNAME, ProfissionalSaudeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FiltroPesquisaProfissionalSaudeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FiltroPesquisaProfissionalSaudeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/filtropesquisaprofissionalsaude", name = "FiltroPesquisaProfissionalSaude")
    public JAXBElement<FiltroPesquisaProfissionalSaudeType> createFiltroPesquisaProfissionalSaude(FiltroPesquisaProfissionalSaudeType value) {
        return new JAXBElement<>(_FiltroPesquisaProfissionalSaude_QNAME, FiltroPesquisaProfissionalSaudeType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link CNPJType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CNPJType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/cnpj", name = "CNPJ")
    public JAXBElement<CNPJType> createCNPJ(CNPJType value) {
        return new JAXBElement<>(_CNPJ_QNAME, CNPJType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DadosGeraisEstabelecimentoSaudeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DadosGeraisEstabelecimentoSaudeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosgeraiscnes", name = "DadosGeraisEstabelecimentoSaude")
    public JAXBElement<DadosGeraisEstabelecimentoSaudeType> createDadosGeraisEstabelecimentoSaude(DadosGeraisEstabelecimentoSaudeType value) {
        return new JAXBElement<>(_DadosGeraisEstabelecimentoSaude_QNAME, DadosGeraisEstabelecimentoSaudeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodigoUnidadeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CodigoUnidadeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/codigounidade", name = "CodigoUnidade")
    public JAXBElement<CodigoUnidadeType> createCodigoUnidade(CodigoUnidadeType value) {
        return new JAXBElement<>(_CodigoUnidade_QNAME, CodigoUnidadeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NomeJuridicoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NomeJuridicoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/nomejuridico", name = "NomeJuridico")
    public JAXBElement<NomeJuridicoType> createNomeJuridico(NomeJuridicoType value) {
        return new JAXBElement<>(_NomeJuridico_QNAME, NomeJuridicoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnderecoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EnderecoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/endereco/v1r2/endereco", name = "Endereco")
    public JAXBElement<EnderecoType> createEndereco(EnderecoType value) {
        return new JAXBElement<>(_Endereco_QNAME, EnderecoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MunicipioType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MunicipioType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/v1r2/municipio", name = "Municipio")
    public JAXBElement<MunicipioType> createMunicipio(MunicipioType value) {
        return new JAXBElement<>(_Municipio_QNAME, MunicipioType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UFType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UFType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/v1r1/uf", name = "UF")
    public JAXBElement<UFType> createUF(UFType value) {
        return new JAXBElement<>(_UF_QNAME, UFType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaisType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PaisType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/v1r2/pais", name = "Pais")
    public JAXBElement<PaisType> createPais(PaisType value) {
        return new JAXBElement<>(_Pais_QNAME, PaisType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoLogradouroType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TipoLogradouroType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/tipologradouro", name = "TipoLogradouro")
    public JAXBElement<TipoLogradouroType> createTipoLogradouro(TipoLogradouroType value) {
        return new JAXBElement<>(_TipoLogradouro_QNAME, TipoLogradouroType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BairroType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BairroType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/bairro", name = "Bairro")
    public JAXBElement<BairroType> createBairro(BairroType value) {
        return new JAXBElement<>(_Bairro_QNAME, BairroType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CEPType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CEPType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/cep", name = "CEP")
    public JAXBElement<CEPType> createCEP(CEPType value) {
        return new JAXBElement<>(_CEP_QNAME, CEPType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsferaAdministrativaType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EsferaAdministrativaType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/esferaadministrativa", name = "esferaAdministrativa")
    public JAXBElement<EsferaAdministrativaType> createEsferaAdministrativa(EsferaAdministrativaType value) {
        return new JAXBElement<>(_EsferaAdministrativa_QNAME, EsferaAdministrativaType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoUnidadeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TipoUnidadeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/tipounidade", name = "tipoUnidade")
    public JAXBElement<TipoUnidadeType> createTipoUnidade(TipoUnidadeType value) {
        return new JAXBElement<>(_TipoUnidade_QNAME, TipoUnidadeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiretorType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DiretorType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/diretor", name = "Diretor")
    public JAXBElement<DiretorType> createDiretor(DiretorType value) {
        return new JAXBElement<>(_Diretor_QNAME, DiretorType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link NomeCompletoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NomeCompletoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/pessoafisica/v1r2/nomecompleto", name = "NomeCompleto")
    public JAXBElement<NomeCompletoType> createNomeCompleto(NomeCompletoType value) {
        return new JAXBElement<>(_NomeCompleto_QNAME, NomeCompletoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TelefoneType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TelefoneType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/telefone/v1r2/telefone", name = "Telefone")
    public JAXBElement<TelefoneType> createTelefone(TelefoneType value) {
        return new JAXBElement<>(_Telefone_QNAME, TelefoneType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoTelefoneType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TipoTelefoneType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/telefone/v1r1/tipotelefone", name = "TipoTelefone")
    public JAXBElement<TipoTelefoneType> createTipoTelefone(TipoTelefoneType value) {
        return new JAXBElement<>(_TipoTelefone_QNAME, TipoTelefoneType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmailType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EmailType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/v1r2/email", name = "Email")
    public JAXBElement<EmailType> createEmail(EmailType value) {
        return new JAXBElement<>(_Email_QNAME, EmailType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link CBOType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CBOType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/cbo", name = "CBO")
    public JAXBElement<CBOType> createCBO(CBOType value) {
        return new JAXBElement<>(_CBO_QNAME, CBOType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DadosBasicosEstabelecimentoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DadosBasicosEstabelecimentoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosestabelecimento", name = "DadosBasicosEstabelecimento")
    public JAXBElement<DadosBasicosEstabelecimentoType> createDadosBasicosEstabelecimento(DadosBasicosEstabelecimentoType value) {
        return new JAXBElement<>(_DadosBasicosEstabelecimento_QNAME, DadosBasicosEstabelecimentoType.class, null, value);
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

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", name = "dataAtribuicao", scope = CNSType.class)
    public JAXBElement<XMLGregorianCalendar> createCNSTypeDataAtribuicao(XMLGregorianCalendar value) {
        return new JAXBElement<>(_CNSTypeDataAtribuicao_QNAME, XMLGregorianCalendar.class, CNSType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoCNSType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TipoCNSType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", name = "tipoCartao", scope = CNSType.class)
    public JAXBElement<TipoCNSType> createCNSTypeTipoCartao(TipoCNSType value) {
        return new JAXBElement<>(_CNSTypeTipoCartao_QNAME, TipoCNSType.class, CNSType.class, value);
    }

}
