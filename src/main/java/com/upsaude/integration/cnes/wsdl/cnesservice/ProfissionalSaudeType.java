
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
 * <p>Java class for ProfissionalSaudeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ProfissionalSaudeType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="dataAtualizacao" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         <element name="Nome" type="{http://servicos.saude.gov.br/schema/corporativo/pessoafisica/v1r2/nomecompleto}NomeCompletoType"/>
 *         <element name="CNS" type="{http://servicos.saude.gov.br/schema/cadsus/v5r0/cns}CNSType"/>
 *         <element name="CPF" type="{http://servicos.saude.gov.br/schema/corporativo/documento/v1r2/cpf}CPFType"/>
 *         <element name="CBO" type="{http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/cbo}CBOType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="CNES" type="{http://servicos.saude.gov.br/schema/cnes/v2r0/estabelecimentosaude}EstabelecimentoSaudeType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="Email" type="{http://servicos.saude.gov.br/schema/corporativo/v1r2/email}EmailType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="Endereco" type="{http://servicos.saude.gov.br/schema/corporativo/endereco/v1r2/endereco}EnderecoType" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfissionalSaudeType", namespace = "http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/profissionalsaude", propOrder = {
    "dataAtualizacao",
    "nome",
    "cns",
    "cpf",
    "cbo",
    "cnes",
    "email",
    "endereco"
})
public class ProfissionalSaudeType {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataAtualizacao;
    @XmlElement(name = "Nome", required = true)
    protected NomeCompletoType nome;
    @XmlElement(name = "CNS", required = true)
    protected CNSType cns;
    @XmlElement(name = "CPF", required = true)
    protected CPFType cpf;
    @XmlElement(name = "CBO")
    protected List<CBOType> cbo;
    @XmlElement(name = "CNES")
    protected List<EstabelecimentoSaudeType> cnes;
    @XmlElement(name = "Email")
    protected List<EmailType> email;
    @XmlElement(name = "Endereco")
    protected List<EnderecoType> endereco;

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
     * Gets the value of the nome property.
     * 
     * @return
     *     possible object is
     *     {@link NomeCompletoType }
     *     
     */
    public NomeCompletoType getNome() {
        return nome;
    }

    /**
     * Sets the value of the nome property.
     * 
     * @param value
     *     allowed object is
     *     {@link NomeCompletoType }
     *     
     */
    public void setNome(NomeCompletoType value) {
        this.nome = value;
    }

    /**
     * Gets the value of the cns property.
     * 
     * @return
     *     possible object is
     *     {@link CNSType }
     *     
     */
    public CNSType getCNS() {
        return cns;
    }

    /**
     * Sets the value of the cns property.
     * 
     * @param value
     *     allowed object is
     *     {@link CNSType }
     *     
     */
    public void setCNS(CNSType value) {
        this.cns = value;
    }

    /**
     * Gets the value of the cpf property.
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
     * Gets the value of the cbo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the cbo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCBO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CBOType }
     * 
     * 
     * @return
     *     The value of the cbo property.
     */
    public List<CBOType> getCBO() {
        if (cbo == null) {
            cbo = new ArrayList<>();
        }
        return this.cbo;
    }

    /**
     * Gets the value of the cnes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the cnes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCNES().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EstabelecimentoSaudeType }
     * 
     * 
     * @return
     *     The value of the cnes property.
     */
    public List<EstabelecimentoSaudeType> getCNES() {
        if (cnes == null) {
            cnes = new ArrayList<>();
        }
        return this.cnes;
    }

    /**
     * Gets the value of the email property.
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

    /**
     * Gets the value of the endereco property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the endereco property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEndereco().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EnderecoType }
     * 
     * 
     * @return
     *     The value of the endereco property.
     */
    public List<EnderecoType> getEndereco() {
        if (endereco == null) {
            endereco = new ArrayList<>();
        }
        return this.endereco;
    }

}
