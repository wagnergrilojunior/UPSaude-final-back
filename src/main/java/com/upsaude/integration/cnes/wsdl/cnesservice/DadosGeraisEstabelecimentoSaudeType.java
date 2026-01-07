
package com.upsaude.integration.cnes.wsdl.cnesservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DadosGeraisEstabelecimentoSaudeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DadosGeraisEstabelecimentoSaudeType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/codigocnes}CodigoCNES" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/codigounidade}CodigoUnidade" minOccurs="0"/>
 *         <element name="nomeFantasia" type="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/nomejuridico}NomeJuridicoType" minOccurs="0"/>
 *         <element name="nomeEmpresarial" type="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/nomejuridico}NomeJuridicoType" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/cnpj}CNPJ" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/corporativo/endereco/v1r2/endereco}Endereco" minOccurs="0"/>
 *         <element name="dataAtualizacao" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/diretor}Diretor" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/tipounidade}tipoUnidade" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/esferaadministrativa}esferaAdministrativa" minOccurs="0"/>
 *         <element name="MunicipioGestor" type="{http://servicos.saude.gov.br/schema/corporativo/v1r2/municipio}MunicipioType" minOccurs="0"/>
 *         <element name="Telefone" type="{http://servicos.saude.gov.br/schema/corporativo/telefone/v1r2/telefone}TelefoneType" maxOccurs="2" minOccurs="0"/>
 *         <element name="Email" type="{http://servicos.saude.gov.br/schema/corporativo/v1r2/email}EmailType" minOccurs="0"/>
 *         <element name="Localizacao" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/localizacao}localizacaoType" minOccurs="0"/>
 *         <element name="perteceSistemaSUS" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DadosGeraisEstabelecimentoSaudeType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosgeraiscnes", propOrder = {
    "codigoCNES",
    "codigoUnidade",
    "nomeFantasia",
    "nomeEmpresarial",
    "cnpj",
    "endereco",
    "dataAtualizacao",
    "diretor",
    "tipoUnidade",
    "esferaAdministrativa",
    "municipioGestor",
    "telefone",
    "email",
    "localizacao",
    "perteceSistemaSUS"
})
public class DadosGeraisEstabelecimentoSaudeType {

    @XmlElement(name = "CodigoCNES", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/codigocnes")
    protected CodigoCNESType codigoCNES;
    @XmlElement(name = "CodigoUnidade", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/codigounidade")
    protected CodigoUnidadeType codigoUnidade;
    protected NomeJuridicoType nomeFantasia;
    protected NomeJuridicoType nomeEmpresarial;
    @XmlElement(name = "CNPJ", namespace = "http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/cnpj")
    protected CNPJType cnpj;
    @XmlElement(name = "Endereco", namespace = "http://servicos.saude.gov.br/schema/corporativo/endereco/v1r2/endereco")
    protected EnderecoType endereco;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataAtualizacao;
    @XmlElement(name = "Diretor", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/diretor")
    protected DiretorType diretor;
    @XmlElement(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/tipounidade")
    protected TipoUnidadeType tipoUnidade;
    @XmlElement(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/esferaadministrativa")
    protected EsferaAdministrativaType esferaAdministrativa;
    @XmlElement(name = "MunicipioGestor")
    protected MunicipioType municipioGestor;
    @XmlElement(name = "Telefone")
    protected List<TelefoneType> telefone;
    @XmlElement(name = "Email")
    protected EmailType email;
    @XmlElement(name = "Localizacao")
    protected LocalizacaoType localizacao;
    protected Boolean perteceSistemaSUS;

    /**
     * Gets the value of the codigoCNES property.
     * 
     * @return
     *     possible object is
     *     {@link CodigoCNESType }
     *     
     */
    public CodigoCNESType getCodigoCNES() {
        return codigoCNES;
    }

    /**
     * Sets the value of the codigoCNES property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodigoCNESType }
     *     
     */
    public void setCodigoCNES(CodigoCNESType value) {
        this.codigoCNES = value;
    }

    /**
     * Gets the value of the codigoUnidade property.
     * 
     * @return
     *     possible object is
     *     {@link CodigoUnidadeType }
     *     
     */
    public CodigoUnidadeType getCodigoUnidade() {
        return codigoUnidade;
    }

    /**
     * Sets the value of the codigoUnidade property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodigoUnidadeType }
     *     
     */
    public void setCodigoUnidade(CodigoUnidadeType value) {
        this.codigoUnidade = value;
    }

    /**
     * Gets the value of the nomeFantasia property.
     * 
     * @return
     *     possible object is
     *     {@link NomeJuridicoType }
     *     
     */
    public NomeJuridicoType getNomeFantasia() {
        return nomeFantasia;
    }

    /**
     * Sets the value of the nomeFantasia property.
     * 
     * @param value
     *     allowed object is
     *     {@link NomeJuridicoType }
     *     
     */
    public void setNomeFantasia(NomeJuridicoType value) {
        this.nomeFantasia = value;
    }

    /**
     * Gets the value of the nomeEmpresarial property.
     * 
     * @return
     *     possible object is
     *     {@link NomeJuridicoType }
     *     
     */
    public NomeJuridicoType getNomeEmpresarial() {
        return nomeEmpresarial;
    }

    /**
     * Sets the value of the nomeEmpresarial property.
     * 
     * @param value
     *     allowed object is
     *     {@link NomeJuridicoType }
     *     
     */
    public void setNomeEmpresarial(NomeJuridicoType value) {
        this.nomeEmpresarial = value;
    }

    /**
     * Gets the value of the cnpj property.
     * 
     * @return
     *     possible object is
     *     {@link CNPJType }
     *     
     */
    public CNPJType getCNPJ() {
        return cnpj;
    }

    /**
     * Sets the value of the cnpj property.
     * 
     * @param value
     *     allowed object is
     *     {@link CNPJType }
     *     
     */
    public void setCNPJ(CNPJType value) {
        this.cnpj = value;
    }

    /**
     * Gets the value of the endereco property.
     * 
     * @return
     *     possible object is
     *     {@link EnderecoType }
     *     
     */
    public EnderecoType getEndereco() {
        return endereco;
    }

    /**
     * Sets the value of the endereco property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnderecoType }
     *     
     */
    public void setEndereco(EnderecoType value) {
        this.endereco = value;
    }

    /**
     * Gets the value of the dataAtualizacao property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAtualizacao() {
        return dataAtualizacao;
    }

    /**
     * Sets the value of the dataAtualizacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAtualizacao(XMLGregorianCalendar value) {
        this.dataAtualizacao = value;
    }

    /**
     * Gets the value of the diretor property.
     * 
     * @return
     *     possible object is
     *     {@link DiretorType }
     *     
     */
    public DiretorType getDiretor() {
        return diretor;
    }

    /**
     * Sets the value of the diretor property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiretorType }
     *     
     */
    public void setDiretor(DiretorType value) {
        this.diretor = value;
    }

    /**
     * Gets the value of the tipoUnidade property.
     * 
     * @return
     *     possible object is
     *     {@link TipoUnidadeType }
     *     
     */
    public TipoUnidadeType getTipoUnidade() {
        return tipoUnidade;
    }

    /**
     * Sets the value of the tipoUnidade property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoUnidadeType }
     *     
     */
    public void setTipoUnidade(TipoUnidadeType value) {
        this.tipoUnidade = value;
    }

    /**
     * Gets the value of the esferaAdministrativa property.
     * 
     * @return
     *     possible object is
     *     {@link EsferaAdministrativaType }
     *     
     */
    public EsferaAdministrativaType getEsferaAdministrativa() {
        return esferaAdministrativa;
    }

    /**
     * Sets the value of the esferaAdministrativa property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsferaAdministrativaType }
     *     
     */
    public void setEsferaAdministrativa(EsferaAdministrativaType value) {
        this.esferaAdministrativa = value;
    }

    /**
     * Gets the value of the municipioGestor property.
     * 
     * @return
     *     possible object is
     *     {@link MunicipioType }
     *     
     */
    public MunicipioType getMunicipioGestor() {
        return municipioGestor;
    }

    /**
     * Sets the value of the municipioGestor property.
     * 
     * @param value
     *     allowed object is
     *     {@link MunicipioType }
     *     
     */
    public void setMunicipioGestor(MunicipioType value) {
        this.municipioGestor = value;
    }

    /**
     * Gets the value of the telefone property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the telefone property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTelefone().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TelefoneType }
     * 
     * 
     * @return
     *     The value of the telefone property.
     */
    public List<TelefoneType> getTelefone() {
        if (telefone == null) {
            telefone = new ArrayList<>();
        }
        return this.telefone;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link EmailType }
     *     
     */
    public EmailType getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailType }
     *     
     */
    public void setEmail(EmailType value) {
        this.email = value;
    }

    /**
     * Gets the value of the localizacao property.
     * 
     * @return
     *     possible object is
     *     {@link LocalizacaoType }
     *     
     */
    public LocalizacaoType getLocalizacao() {
        return localizacao;
    }

    /**
     * Sets the value of the localizacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizacaoType }
     *     
     */
    public void setLocalizacao(LocalizacaoType value) {
        this.localizacao = value;
    }

    /**
     * Gets the value of the perteceSistemaSUS property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPerteceSistemaSUS() {
        return perteceSistemaSUS;
    }

    /**
     * Sets the value of the perteceSistemaSUS property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPerteceSistemaSUS(Boolean value) {
        this.perteceSistemaSUS = value;
    }

}
