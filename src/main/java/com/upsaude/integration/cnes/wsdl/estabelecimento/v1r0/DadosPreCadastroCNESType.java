
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DadosPreCadastroCNESType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DadosPreCadastroCNESType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/codigocnes}CodigoCNES"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/corporativo/documento/v1r2/cpf}CPF" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/cnpj}CNPJ" minOccurs="0"/>
 *         <element name="NomeFantasia" type="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/nomejuridico}NomeJuridicoType" minOccurs="0"/>
 *         <element name="NomeEmpresarial" type="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/nomejuridico}NomeJuridicoType" minOccurs="0"/>
 *         <element name="NaturezaJuridica" type="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/naturezajuridica}NaturezaJuridicaType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="CNPJMantenedora" type="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/cnpj}CNPJType" minOccurs="0"/>
 *         <element name="NaturezaJuridicaMantenedora" type="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/naturezajuridica}NaturezaJuridicaType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="DataAtualizacao" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/corporativo/endereco/v1r2/endereco}Endereco" minOccurs="0"/>
 *         <element name="Telefones" type="{http://servicos.saude.gov.br/schema/corporativo/telefone/v1r2/telefone}TelefoneType" maxOccurs="2" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/corporativo/v1r2/email}Email" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DadosPreCadastroCNESType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosprecadastrocnes", propOrder = {
    "codigoCNES",
    "cpf",
    "cnpj",
    "nomeFantasia",
    "nomeEmpresarial",
    "naturezaJuridica",
    "cnpjMantenedora",
    "naturezaJuridicaMantenedora",
    "dataAtualizacao",
    "endereco",
    "telefones",
    "email"
})
public class DadosPreCadastroCNESType {

    @XmlElement(name = "CodigoCNES", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/codigocnes", required = true)
    protected CodigoCNESType codigoCNES;
    @XmlElement(name = "CPF", namespace = "http://servicos.saude.gov.br/schema/corporativo/documento/v1r2/cpf")
    protected CPFType cpf;
    @XmlElement(name = "CNPJ", namespace = "http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/cnpj")
    protected CNPJType cnpj;
    @XmlElement(name = "NomeFantasia")
    protected NomeJuridicoType nomeFantasia;
    @XmlElement(name = "NomeEmpresarial")
    protected NomeJuridicoType nomeEmpresarial;
    @XmlElement(name = "NaturezaJuridica")
    protected List<NaturezaJuridicaType> naturezaJuridica;
    @XmlElement(name = "CNPJMantenedora")
    protected CNPJType cnpjMantenedora;
    @XmlElement(name = "NaturezaJuridicaMantenedora")
    protected List<NaturezaJuridicaType> naturezaJuridicaMantenedora;
    @XmlElement(name = "DataAtualizacao")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataAtualizacao;
    @XmlElement(name = "Endereco", namespace = "http://servicos.saude.gov.br/schema/corporativo/endereco/v1r2/endereco")
    protected EnderecoType endereco;
    @XmlElement(name = "Telefones")
    protected List<TelefoneType> telefones;
    @XmlElement(name = "Email", namespace = "http://servicos.saude.gov.br/schema/corporativo/v1r2/email")
    protected List<EmailType> email;

    /**
     * Código do Estabelecimento de Saúde.
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
     * CPF do Estabelecimento de Saúde.
     * 
     * @return
     *     possible object is
     *     {@link CPFType }
     *     
     */
    public CPFType getCPF() {
        return cpf;
    }

    /**
     * Sets the value of the cpf property.
     * 
     * @param value
     *     allowed object is
     *     {@link CPFType }
     *     
     */
    public void setCPF(CPFType value) {
        this.cpf = value;
    }

    /**
     * CNPJ do Estabelecimento de Saúde.
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
     * Gets the value of the naturezaJuridica property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the naturezaJuridica property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNaturezaJuridica().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NaturezaJuridicaType }
     * 
     * 
     * @return
     *     The value of the naturezaJuridica property.
     */
    public List<NaturezaJuridicaType> getNaturezaJuridica() {
        if (naturezaJuridica == null) {
            naturezaJuridica = new ArrayList<>();
        }
        return this.naturezaJuridica;
    }

    /**
     * Gets the value of the cnpjMantenedora property.
     * 
     * @return
     *     possible object is
     *     {@link CNPJType }
     *     
     */
    public CNPJType getCNPJMantenedora() {
        return cnpjMantenedora;
    }

    /**
     * Sets the value of the cnpjMantenedora property.
     * 
     * @param value
     *     allowed object is
     *     {@link CNPJType }
     *     
     */
    public void setCNPJMantenedora(CNPJType value) {
        this.cnpjMantenedora = value;
    }

    /**
     * Gets the value of the naturezaJuridicaMantenedora property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the naturezaJuridicaMantenedora property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNaturezaJuridicaMantenedora().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NaturezaJuridicaType }
     * 
     * 
     * @return
     *     The value of the naturezaJuridicaMantenedora property.
     */
    public List<NaturezaJuridicaType> getNaturezaJuridicaMantenedora() {
        if (naturezaJuridicaMantenedora == null) {
            naturezaJuridicaMantenedora = new ArrayList<>();
        }
        return this.naturezaJuridicaMantenedora;
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
     * Endereço do Estabelecimetno de Saúde.
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
     * Gets the value of the telefones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the telefones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTelefones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TelefoneType }
     * 
     * 
     * @return
     *     The value of the telefones property.
     */
    public List<TelefoneType> getTelefones() {
        if (telefones == null) {
            telefones = new ArrayList<>();
        }
        return this.telefones;
    }

    /**
     * Email(s) do Estabelecimento de Saúde Gets the value of the email property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the email property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EmailType }
     * 
     * 
     * @return
     *     The value of the email property.
     */
    public List<EmailType> getEmail() {
        if (email == null) {
            email = new ArrayList<>();
        }
        return this.email;
    }

}
