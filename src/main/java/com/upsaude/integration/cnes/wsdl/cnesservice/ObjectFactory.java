
package com.upsaude.integration.cnes.wsdl.cnesservice;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.upsaude.integration.cnes.wsdl.cnesservice package. 
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
    private static final QName _ResultadoPesquisaEstabelecimentoSaude_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisaestabelecimentosaude", "ResultadoPesquisaEstabelecimentoSaude");
    private static final QName _Municipio_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/v1r2/municipio", "Municipio");
    private static final QName _ResultadoPesquisaCnesMunicipio_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisacnesmunicipio", "ResultadoPesquisaCnesMunicipio");
    private static final QName _Cmpt_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/cmpt", "cmpt");
    private static final QName _ResultadoPesquisaDadosComplementaresCnes_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisadadoscomplementarescnes", "ResultadoPesquisaDadosComplementaresCnes");
    private static final QName _ComplementoEstabelecimentoSaude_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/complementoestabelecimentosaude", "ComplementoEstabelecimentoSaude");
    private static final QName _TipoUnidade_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/tipounidade", "tipoUnidade");
    private static final QName _TipoGestao_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/tipogestao", "tipoGestao");
    private static final QName _ComplementoProfissionalSaude_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/complementoprofissionalsaude", "ComplementoProfissionalSaude");
    private static final QName _Vinculoempregaticio_QNAME = new QName("http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/vinculoempregaticio", "vinculoempregaticio");
    private static final QName _TipoVinculoEmpregaticio_QNAME = new QName("http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/tipovinculoempregaticio", "tipoVinculoEmpregaticio");
    private static final QName _ComplementoEsferaAdministrativa_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/complementoesferaadministrativa", "ComplementoEsferaAdministrativa");
    private static final QName _EsferaAdministrativa_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/esferaadministrativa", "esferaAdministrativa");
    private static final QName _Endereco_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/endereco/v1r2/endereco", "Endereco");
    private static final QName _UF_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/v1r1/uf", "UF");
    private static final QName _Pais_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/v1r2/pais", "Pais");
    private static final QName _TipoLogradouro_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/tipologradouro", "TipoLogradouro");
    private static final QName _Bairro_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/bairro", "Bairro");
    private static final QName _CEP_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/cep", "CEP");
    private static final QName _NomeJuridico_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/nomejuridico", "NomeJuridico");
    private static final QName _DadosBasicosEstabelecimento_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosestabelecimento", "DadosBasicosEstabelecimento");
    private static final QName _ProfissionalSaude_QNAME = new QName("http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/profissionalsaude", "ProfissionalSaude");
    private static final QName _CBO_QNAME = new QName("http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/cbo", "CBO");
    private static final QName _CNS_QNAME = new QName("http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", "CNS");
    private static final QName _ListaCNS_QNAME = new QName("http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", "ListaCNS");
    private static final QName _CPF_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/documento/v1r2/cpf", "CPF");
    private static final QName _NomeCompleto_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/pessoafisica/v1r2/nomecompleto", "NomeCompleto");
    private static final QName _EstabelecimentoSaude_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v2r0/estabelecimentosaude", "EstabelecimentoSaude");
    private static final QName _IdentificacaoEstabelecimentoSaude_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/identificacaoestabelecimentosaude", "IdentificacaoEstabelecimentoSaude");
    private static final QName _DadosPrincipais_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/dadosprincipaisestabelecimentosaude", "DadosPrincipais");
    private static final QName _TipoEstabelecimentoSaude_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/tipoestabelecimentosaude", "TipoEstabelecimentoSaude");
    private static final QName _SubtipoEstabelecimentoSaude_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/subtipoestabelecimentosaude", "SubtipoEstabelecimentoSaude");
    private static final QName _AtividadeEnsino_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/atividadeensino", "AtividadeEnsino");
    private static final QName _DadosComplementares_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/dadoscomplementaresestabelecimentosaude", "DadosComplementares");
    private static final QName _CNAE_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/cnae", "CNAE");
    private static final QName _AlvaraSanitario_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/alvarasanitario", "AlvaraSanitario");
    private static final QName _Email_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/v1r2/email", "Email");
    private static final QName _Leito_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/leito", "leito");
    private static final QName _Habilitacao_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/habilitacao", "habilitacao");
    private static final QName _GrupoHabilitacao_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/grupohabilitacao", "grupoHabilitacao");
    private static final QName _SubGrupoHabilitacao_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/subgrupohabilitacao", "subGrupoHabilitacao");
    private static final QName _Equipamento_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/equipamento", "Equipamento");
    private static final QName _TipoEquipamento_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/tipoequipamento", "tipoEquipamento");
    private static final QName _Samu_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/samu", "samu");
    private static final QName _DadosGeraisEstabelecimentoSaude_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/dadosgeraiscnes", "DadosGeraisEstabelecimentoSaude");
    private static final QName _CodigoUnidade_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/codigounidade", "CodigoUnidade");
    private static final QName _CNPJ_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/cnpj", "CNPJ");
    private static final QName _Diretor_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/diretor", "Diretor");
    private static final QName _Telefone_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/telefone/v1r2/telefone", "Telefone");
    private static final QName _TipoTelefone_QNAME = new QName("http://servicos.saude.gov.br/schema/corporativo/telefone/v1r1/tipotelefone", "TipoTelefone");
    private static final QName _Localizacao_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/localizacao", "Localizacao");
    private static final QName _Mensagem_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/mensagem", "Mensagem");
    private static final QName _MSFalhaIdentificador_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/msfalha", "identificador");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.upsaude.integration.cnes.wsdl.cnesservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RequestConsultarEstabelecimentoSaude }
     * 
     * @return
     *     the new instance of {@link RequestConsultarEstabelecimentoSaude }
     */
    public RequestConsultarEstabelecimentoSaude createRequestConsultarEstabelecimentoSaude() {
        return new RequestConsultarEstabelecimentoSaude();
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
     * Create an instance of {@link ResponseConsultarEstabelecimentoSaude }
     * 
     * @return
     *     the new instance of {@link ResponseConsultarEstabelecimentoSaude }
     */
    public ResponseConsultarEstabelecimentoSaude createResponseConsultarEstabelecimentoSaude() {
        return new ResponseConsultarEstabelecimentoSaude();
    }

    /**
     * Create an instance of {@link ResultadoPesquisaEstabelecimentoSaudeType }
     * 
     * @return
     *     the new instance of {@link ResultadoPesquisaEstabelecimentoSaudeType }
     */
    public ResultadoPesquisaEstabelecimentoSaudeType createResultadoPesquisaEstabelecimentoSaudeType() {
        return new ResultadoPesquisaEstabelecimentoSaudeType();
    }

    /**
     * Create an instance of {@link RequestConsultarEstabelecimentoSaudePorMunicipio }
     * 
     * @return
     *     the new instance of {@link RequestConsultarEstabelecimentoSaudePorMunicipio }
     */
    public RequestConsultarEstabelecimentoSaudePorMunicipio createRequestConsultarEstabelecimentoSaudePorMunicipio() {
        return new RequestConsultarEstabelecimentoSaudePorMunicipio();
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
     * Create an instance of {@link ResponseConsultarEstabelecimentoSaudePorMunicipio }
     * 
     * @return
     *     the new instance of {@link ResponseConsultarEstabelecimentoSaudePorMunicipio }
     */
    public ResponseConsultarEstabelecimentoSaudePorMunicipio createResponseConsultarEstabelecimentoSaudePorMunicipio() {
        return new ResponseConsultarEstabelecimentoSaudePorMunicipio();
    }

    /**
     * Create an instance of {@link ResultadoPesquisaCnesMunicipioType }
     * 
     * @return
     *     the new instance of {@link ResultadoPesquisaCnesMunicipioType }
     */
    public ResultadoPesquisaCnesMunicipioType createResultadoPesquisaCnesMunicipioType() {
        return new ResultadoPesquisaCnesMunicipioType();
    }

    /**
     * Create an instance of {@link RequestConsultarDadosComplementaresEstabelecimentoSaude }
     * 
     * @return
     *     the new instance of {@link RequestConsultarDadosComplementaresEstabelecimentoSaude }
     */
    public RequestConsultarDadosComplementaresEstabelecimentoSaude createRequestConsultarDadosComplementaresEstabelecimentoSaude() {
        return new RequestConsultarDadosComplementaresEstabelecimentoSaude();
    }

    /**
     * Create an instance of {@link CmptType }
     * 
     * @return
     *     the new instance of {@link CmptType }
     */
    public CmptType createCmptType() {
        return new CmptType();
    }

    /**
     * Create an instance of {@link ResponseConsultarDadosComplementaresEstabelecimentoSaude }
     * 
     * @return
     *     the new instance of {@link ResponseConsultarDadosComplementaresEstabelecimentoSaude }
     */
    public ResponseConsultarDadosComplementaresEstabelecimentoSaude createResponseConsultarDadosComplementaresEstabelecimentoSaude() {
        return new ResponseConsultarDadosComplementaresEstabelecimentoSaude();
    }

    /**
     * Create an instance of {@link ResultadoPesquisaDadosComplementaresCnesType }
     * 
     * @return
     *     the new instance of {@link ResultadoPesquisaDadosComplementaresCnesType }
     */
    public ResultadoPesquisaDadosComplementaresCnesType createResultadoPesquisaDadosComplementaresCnesType() {
        return new ResultadoPesquisaDadosComplementaresCnesType();
    }

    /**
     * Create an instance of {@link SumarioDadosComplementaresType }
     * 
     * @return
     *     the new instance of {@link SumarioDadosComplementaresType }
     */
    public SumarioDadosComplementaresType createSumarioDadosComplementaresType() {
        return new SumarioDadosComplementaresType();
    }

    /**
     * Create an instance of {@link ComplementoEstabelecimentoSaudeType }
     * 
     * @return
     *     the new instance of {@link ComplementoEstabelecimentoSaudeType }
     */
    public ComplementoEstabelecimentoSaudeType createComplementoEstabelecimentoSaudeType() {
        return new ComplementoEstabelecimentoSaudeType();
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
     * Create an instance of {@link TipoGestaoType }
     * 
     * @return
     *     the new instance of {@link TipoGestaoType }
     */
    public TipoGestaoType createTipoGestaoType() {
        return new TipoGestaoType();
    }

    /**
     * Create an instance of {@link ComplementoProfissionalSaudeType }
     * 
     * @return
     *     the new instance of {@link ComplementoProfissionalSaudeType }
     */
    public ComplementoProfissionalSaudeType createComplementoProfissionalSaudeType() {
        return new ComplementoProfissionalSaudeType();
    }

    /**
     * Create an instance of {@link VinculoEmpregaticioType }
     * 
     * @return
     *     the new instance of {@link VinculoEmpregaticioType }
     */
    public VinculoEmpregaticioType createVinculoEmpregaticioType() {
        return new VinculoEmpregaticioType();
    }

    /**
     * Create an instance of {@link TipoVinculoEmpregaticioType }
     * 
     * @return
     *     the new instance of {@link TipoVinculoEmpregaticioType }
     */
    public TipoVinculoEmpregaticioType createTipoVinculoEmpregaticioType() {
        return new TipoVinculoEmpregaticioType();
    }

    /**
     * Create an instance of {@link ComplementoEsferaAdministrativaType }
     * 
     * @return
     *     the new instance of {@link ComplementoEsferaAdministrativaType }
     */
    public ComplementoEsferaAdministrativaType createComplementoEsferaAdministrativaType() {
        return new ComplementoEsferaAdministrativaType();
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
     * Create an instance of {@link EnderecoType }
     * 
     * @return
     *     the new instance of {@link EnderecoType }
     */
    public EnderecoType createEnderecoType() {
        return new EnderecoType();
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
     * Create an instance of {@link NomeJuridicoType }
     * 
     * @return
     *     the new instance of {@link NomeJuridicoType }
     */
    public NomeJuridicoType createNomeJuridicoType() {
        return new NomeJuridicoType();
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
     * Create an instance of {@link SumarioEstabelecimentoType }
     * 
     * @return
     *     the new instance of {@link SumarioEstabelecimentoType }
     */
    public SumarioEstabelecimentoType createSumarioEstabelecimentoType() {
        return new SumarioEstabelecimentoType();
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
     * Create an instance of {@link EstabelecimentoSaudeType }
     * 
     * @return
     *     the new instance of {@link EstabelecimentoSaudeType }
     */
    public EstabelecimentoSaudeType createEstabelecimentoSaudeType() {
        return new EstabelecimentoSaudeType();
    }

    /**
     * Create an instance of {@link IdentificacaoEstabelecimentoSaudeType }
     * 
     * @return
     *     the new instance of {@link IdentificacaoEstabelecimentoSaudeType }
     */
    public IdentificacaoEstabelecimentoSaudeType createIdentificacaoEstabelecimentoSaudeType() {
        return new IdentificacaoEstabelecimentoSaudeType();
    }

    /**
     * Create an instance of {@link DadosPrincipaisType }
     * 
     * @return
     *     the new instance of {@link DadosPrincipaisType }
     */
    public DadosPrincipaisType createDadosPrincipaisType() {
        return new DadosPrincipaisType();
    }

    /**
     * Create an instance of {@link TipoEstabelecimentoSaudeType }
     * 
     * @return
     *     the new instance of {@link TipoEstabelecimentoSaudeType }
     */
    public TipoEstabelecimentoSaudeType createTipoEstabelecimentoSaudeType() {
        return new TipoEstabelecimentoSaudeType();
    }

    /**
     * Create an instance of {@link SubtipoEstabelecimentoSaudeType }
     * 
     * @return
     *     the new instance of {@link SubtipoEstabelecimentoSaudeType }
     */
    public SubtipoEstabelecimentoSaudeType createSubtipoEstabelecimentoSaudeType() {
        return new SubtipoEstabelecimentoSaudeType();
    }

    /**
     * Create an instance of {@link AtividadeEnsinoType }
     * 
     * @return
     *     the new instance of {@link AtividadeEnsinoType }
     */
    public AtividadeEnsinoType createAtividadeEnsinoType() {
        return new AtividadeEnsinoType();
    }

    /**
     * Create an instance of {@link DadosComplementaresType }
     * 
     * @return
     *     the new instance of {@link DadosComplementaresType }
     */
    public DadosComplementaresType createDadosComplementaresType() {
        return new DadosComplementaresType();
    }

    /**
     * Create an instance of {@link CNAEType }
     * 
     * @return
     *     the new instance of {@link CNAEType }
     */
    public CNAEType createCNAEType() {
        return new CNAEType();
    }

    /**
     * Create an instance of {@link AlvaraSanitarioType }
     * 
     * @return
     *     the new instance of {@link AlvaraSanitarioType }
     */
    public AlvaraSanitarioType createAlvaraSanitarioType() {
        return new AlvaraSanitarioType();
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
     * Create an instance of {@link LeitoType }
     * 
     * @return
     *     the new instance of {@link LeitoType }
     */
    public LeitoType createLeitoType() {
        return new LeitoType();
    }

    /**
     * Create an instance of {@link HabilitacaoType }
     * 
     * @return
     *     the new instance of {@link HabilitacaoType }
     */
    public HabilitacaoType createHabilitacaoType() {
        return new HabilitacaoType();
    }

    /**
     * Create an instance of {@link GrupoHabilitacaoType }
     * 
     * @return
     *     the new instance of {@link GrupoHabilitacaoType }
     */
    public GrupoHabilitacaoType createGrupoHabilitacaoType() {
        return new GrupoHabilitacaoType();
    }

    /**
     * Create an instance of {@link SubGrupoHabilitacaoType }
     * 
     * @return
     *     the new instance of {@link SubGrupoHabilitacaoType }
     */
    public SubGrupoHabilitacaoType createSubGrupoHabilitacaoType() {
        return new SubGrupoHabilitacaoType();
    }

    /**
     * Create an instance of {@link EquipamentoType }
     * 
     * @return
     *     the new instance of {@link EquipamentoType }
     */
    public EquipamentoType createEquipamentoType() {
        return new EquipamentoType();
    }

    /**
     * Create an instance of {@link IndicadorSUSType }
     * 
     * @return
     *     the new instance of {@link IndicadorSUSType }
     */
    public IndicadorSUSType createIndicadorSUSType() {
        return new IndicadorSUSType();
    }

    /**
     * Create an instance of {@link TipoEquipamentoType }
     * 
     * @return
     *     the new instance of {@link TipoEquipamentoType }
     */
    public TipoEquipamentoType createTipoEquipamentoType() {
        return new TipoEquipamentoType();
    }

    /**
     * Create an instance of {@link SamuType }
     * 
     * @return
     *     the new instance of {@link SamuType }
     */
    public SamuType createSamuType() {
        return new SamuType();
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
     * Create an instance of {@link CNPJType }
     * 
     * @return
     *     the new instance of {@link CNPJType }
     */
    public CNPJType createCNPJType() {
        return new CNPJType();
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
     * Create an instance of {@link LocalizacaoType }
     * 
     * @return
     *     the new instance of {@link LocalizacaoType }
     */
    public LocalizacaoType createLocalizacaoType() {
        return new LocalizacaoType();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoPesquisaEstabelecimentoSaudeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ResultadoPesquisaEstabelecimentoSaudeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisaestabelecimentosaude", name = "ResultadoPesquisaEstabelecimentoSaude")
    public JAXBElement<ResultadoPesquisaEstabelecimentoSaudeType> createResultadoPesquisaEstabelecimentoSaude(ResultadoPesquisaEstabelecimentoSaudeType value) {
        return new JAXBElement<>(_ResultadoPesquisaEstabelecimentoSaude_QNAME, ResultadoPesquisaEstabelecimentoSaudeType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoPesquisaCnesMunicipioType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ResultadoPesquisaCnesMunicipioType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisacnesmunicipio", name = "ResultadoPesquisaCnesMunicipio")
    public JAXBElement<ResultadoPesquisaCnesMunicipioType> createResultadoPesquisaCnesMunicipio(ResultadoPesquisaCnesMunicipioType value) {
        return new JAXBElement<>(_ResultadoPesquisaCnesMunicipio_QNAME, ResultadoPesquisaCnesMunicipioType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CmptType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CmptType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/cmpt", name = "cmpt")
    public JAXBElement<CmptType> createCmpt(CmptType value) {
        return new JAXBElement<>(_Cmpt_QNAME, CmptType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoPesquisaDadosComplementaresCnesType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ResultadoPesquisaDadosComplementaresCnesType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisadadoscomplementarescnes", name = "ResultadoPesquisaDadosComplementaresCnes")
    public JAXBElement<ResultadoPesquisaDadosComplementaresCnesType> createResultadoPesquisaDadosComplementaresCnes(ResultadoPesquisaDadosComplementaresCnesType value) {
        return new JAXBElement<>(_ResultadoPesquisaDadosComplementaresCnes_QNAME, ResultadoPesquisaDadosComplementaresCnesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComplementoEstabelecimentoSaudeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ComplementoEstabelecimentoSaudeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/complementoestabelecimentosaude", name = "ComplementoEstabelecimentoSaude")
    public JAXBElement<ComplementoEstabelecimentoSaudeType> createComplementoEstabelecimentoSaude(ComplementoEstabelecimentoSaudeType value) {
        return new JAXBElement<>(_ComplementoEstabelecimentoSaude_QNAME, ComplementoEstabelecimentoSaudeType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoGestaoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TipoGestaoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/tipogestao", name = "tipoGestao")
    public JAXBElement<TipoGestaoType> createTipoGestao(TipoGestaoType value) {
        return new JAXBElement<>(_TipoGestao_QNAME, TipoGestaoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComplementoProfissionalSaudeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ComplementoProfissionalSaudeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/complementoprofissionalsaude", name = "ComplementoProfissionalSaude")
    public JAXBElement<ComplementoProfissionalSaudeType> createComplementoProfissionalSaude(ComplementoProfissionalSaudeType value) {
        return new JAXBElement<>(_ComplementoProfissionalSaude_QNAME, ComplementoProfissionalSaudeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VinculoEmpregaticioType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link VinculoEmpregaticioType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/vinculoempregaticio", name = "vinculoempregaticio")
    public JAXBElement<VinculoEmpregaticioType> createVinculoempregaticio(VinculoEmpregaticioType value) {
        return new JAXBElement<>(_Vinculoempregaticio_QNAME, VinculoEmpregaticioType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoVinculoEmpregaticioType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TipoVinculoEmpregaticioType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/tipovinculoempregaticio", name = "tipoVinculoEmpregaticio")
    public JAXBElement<TipoVinculoEmpregaticioType> createTipoVinculoEmpregaticio(TipoVinculoEmpregaticioType value) {
        return new JAXBElement<>(_TipoVinculoEmpregaticio_QNAME, TipoVinculoEmpregaticioType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComplementoEsferaAdministrativaType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ComplementoEsferaAdministrativaType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/complementoesferaadministrativa", name = "ComplementoEsferaAdministrativa")
    public JAXBElement<ComplementoEsferaAdministrativaType> createComplementoEsferaAdministrativa(ComplementoEsferaAdministrativaType value) {
        return new JAXBElement<>(_ComplementoEsferaAdministrativa_QNAME, ComplementoEsferaAdministrativaType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ProfissionalSaudeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ProfissionalSaudeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/profissionalsaude", name = "ProfissionalSaude")
    public JAXBElement<ProfissionalSaudeType> createProfissionalSaude(ProfissionalSaudeType value) {
        return new JAXBElement<>(_ProfissionalSaude_QNAME, ProfissionalSaudeType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link EstabelecimentoSaudeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EstabelecimentoSaudeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v2r0/estabelecimentosaude", name = "EstabelecimentoSaude")
    public JAXBElement<EstabelecimentoSaudeType> createEstabelecimentoSaude(EstabelecimentoSaudeType value) {
        return new JAXBElement<>(_EstabelecimentoSaude_QNAME, EstabelecimentoSaudeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentificacaoEstabelecimentoSaudeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IdentificacaoEstabelecimentoSaudeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/identificacaoestabelecimentosaude", name = "IdentificacaoEstabelecimentoSaude")
    public JAXBElement<IdentificacaoEstabelecimentoSaudeType> createIdentificacaoEstabelecimentoSaude(IdentificacaoEstabelecimentoSaudeType value) {
        return new JAXBElement<>(_IdentificacaoEstabelecimentoSaude_QNAME, IdentificacaoEstabelecimentoSaudeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DadosPrincipaisType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DadosPrincipaisType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosprincipaisestabelecimentosaude", name = "DadosPrincipais")
    public JAXBElement<DadosPrincipaisType> createDadosPrincipais(DadosPrincipaisType value) {
        return new JAXBElement<>(_DadosPrincipais_QNAME, DadosPrincipaisType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoEstabelecimentoSaudeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TipoEstabelecimentoSaudeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/tipoestabelecimentosaude", name = "TipoEstabelecimentoSaude")
    public JAXBElement<TipoEstabelecimentoSaudeType> createTipoEstabelecimentoSaude(TipoEstabelecimentoSaudeType value) {
        return new JAXBElement<>(_TipoEstabelecimentoSaude_QNAME, TipoEstabelecimentoSaudeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubtipoEstabelecimentoSaudeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubtipoEstabelecimentoSaudeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/subtipoestabelecimentosaude", name = "SubtipoEstabelecimentoSaude")
    public JAXBElement<SubtipoEstabelecimentoSaudeType> createSubtipoEstabelecimentoSaude(SubtipoEstabelecimentoSaudeType value) {
        return new JAXBElement<>(_SubtipoEstabelecimentoSaude_QNAME, SubtipoEstabelecimentoSaudeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtividadeEnsinoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AtividadeEnsinoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/atividadeensino", name = "AtividadeEnsino")
    public JAXBElement<AtividadeEnsinoType> createAtividadeEnsino(AtividadeEnsinoType value) {
        return new JAXBElement<>(_AtividadeEnsino_QNAME, AtividadeEnsinoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DadosComplementaresType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DadosComplementaresType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadoscomplementaresestabelecimentosaude", name = "DadosComplementares")
    public JAXBElement<DadosComplementaresType> createDadosComplementares(DadosComplementaresType value) {
        return new JAXBElement<>(_DadosComplementares_QNAME, DadosComplementaresType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CNAEType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CNAEType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/cnae", name = "CNAE")
    public JAXBElement<CNAEType> createCNAE(CNAEType value) {
        return new JAXBElement<>(_CNAE_QNAME, CNAEType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlvaraSanitarioType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AlvaraSanitarioType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/alvarasanitario", name = "AlvaraSanitario")
    public JAXBElement<AlvaraSanitarioType> createAlvaraSanitario(AlvaraSanitarioType value) {
        return new JAXBElement<>(_AlvaraSanitario_QNAME, AlvaraSanitarioType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link LeitoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LeitoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/leito", name = "leito")
    public JAXBElement<LeitoType> createLeito(LeitoType value) {
        return new JAXBElement<>(_Leito_QNAME, LeitoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HabilitacaoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HabilitacaoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/habilitacao", name = "habilitacao")
    public JAXBElement<HabilitacaoType> createHabilitacao(HabilitacaoType value) {
        return new JAXBElement<>(_Habilitacao_QNAME, HabilitacaoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GrupoHabilitacaoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GrupoHabilitacaoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/grupohabilitacao", name = "grupoHabilitacao")
    public JAXBElement<GrupoHabilitacaoType> createGrupoHabilitacao(GrupoHabilitacaoType value) {
        return new JAXBElement<>(_GrupoHabilitacao_QNAME, GrupoHabilitacaoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubGrupoHabilitacaoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SubGrupoHabilitacaoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/subgrupohabilitacao", name = "subGrupoHabilitacao")
    public JAXBElement<SubGrupoHabilitacaoType> createSubGrupoHabilitacao(SubGrupoHabilitacaoType value) {
        return new JAXBElement<>(_SubGrupoHabilitacao_QNAME, SubGrupoHabilitacaoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EquipamentoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EquipamentoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/equipamento", name = "Equipamento")
    public JAXBElement<EquipamentoType> createEquipamento(EquipamentoType value) {
        return new JAXBElement<>(_Equipamento_QNAME, EquipamentoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoEquipamentoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TipoEquipamentoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/tipoequipamento", name = "tipoEquipamento")
    public JAXBElement<TipoEquipamentoType> createTipoEquipamento(TipoEquipamentoType value) {
        return new JAXBElement<>(_TipoEquipamento_QNAME, TipoEquipamentoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SamuType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SamuType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/samu", name = "samu")
    public JAXBElement<SamuType> createSamu(SamuType value) {
        return new JAXBElement<>(_Samu_QNAME, SamuType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link LocalizacaoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LocalizacaoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/localizacao", name = "Localizacao")
    public JAXBElement<LocalizacaoType> createLocalizacao(LocalizacaoType value) {
        return new JAXBElement<>(_Localizacao_QNAME, LocalizacaoType.class, null, value);
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
