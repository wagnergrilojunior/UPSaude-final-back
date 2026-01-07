
package com.upsaude.integration.cnes.wsdl.profissional;

import java.math.BigInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnderecoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="EnderecoType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="identificador" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *         <element name="TipoEndereco" type="{http://servicos.saude.gov.br/schema/corporativo/endereco/v1r2/endereco}TipoEnderecoType" minOccurs="0"/>
 *         <element name="TipoLogradouro" type="{http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/tipologradouro}TipoLogradouroType" minOccurs="0"/>
 *         <element name="nomeLogradouro" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="250"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="numero" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="7"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="complemento" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="50"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="Bairro" type="{http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/bairro}BairroType" minOccurs="0"/>
 *         <element name="CEP" type="{http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/cep}CEPType" minOccurs="0"/>
 *         <element name="Municipio" type="{http://servicos.saude.gov.br/schema/corporativo/v1r2/municipio}MunicipioType" minOccurs="0"/>
 *         <element name="Pais" type="{http://servicos.saude.gov.br/schema/corporativo/v1r2/pais}PaisType" minOccurs="0"/>
 *         <element name="municipioInternacional" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="70"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnderecoType", namespace = "http://servicos.saude.gov.br/schema/corporativo/endereco/v1r2/endereco", propOrder = {
    "identificador",
    "tipoEndereco",
    "tipoLogradouro",
    "nomeLogradouro",
    "numero",
    "complemento",
    "bairro",
    "cep",
    "municipio",
    "pais",
    "municipioInternacional"
})
public class EnderecoType {

    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger identificador;
    @XmlElement(name = "TipoEndereco")
    protected String tipoEndereco;
    @XmlElement(name = "TipoLogradouro")
    protected TipoLogradouroType tipoLogradouro;
    protected String nomeLogradouro;
    protected String numero;
    protected String complemento;
    @XmlElement(name = "Bairro")
    protected BairroType bairro;
    @XmlElement(name = "CEP")
    protected CEPType cep;
    @XmlElement(name = "Municipio")
    protected MunicipioType municipio;
    @XmlElement(name = "Pais")
    protected PaisType pais;
    protected String municipioInternacional;

    /**
     * Gets the value of the identificador property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdentificador() {
        return identificador;
    }

    /**
     * Sets the value of the identificador property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdentificador(BigInteger value) {
        this.identificador = value;
    }

    /**
     * Gets the value of the tipoEndereco property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoEndereco() {
        return tipoEndereco;
    }

    /**
     * Sets the value of the tipoEndereco property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoEndereco(String value) {
        this.tipoEndereco = value;
    }

    /**
     * Gets the value of the tipoLogradouro property.
     * 
     * @return
     *     possible object is
     *     {@link TipoLogradouroType }
     *     
     */
    public TipoLogradouroType getTipoLogradouro() {
        return tipoLogradouro;
    }

    /**
     * Sets the value of the tipoLogradouro property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoLogradouroType }
     *     
     */
    public void setTipoLogradouro(TipoLogradouroType value) {
        this.tipoLogradouro = value;
    }

    /**
     * Gets the value of the nomeLogradouro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeLogradouro() {
        return nomeLogradouro;
    }

    /**
     * Sets the value of the nomeLogradouro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeLogradouro(String value) {
        this.nomeLogradouro = value;
    }

    /**
     * Gets the value of the numero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumero(String value) {
        this.numero = value;
    }

    /**
     * Gets the value of the complemento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * Sets the value of the complemento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplemento(String value) {
        this.complemento = value;
    }

    /**
     * Gets the value of the bairro property.
     * 
     * @return
     *     possible object is
     *     {@link BairroType }
     *     
     */
    public BairroType getBairro() {
        return bairro;
    }

    /**
     * Sets the value of the bairro property.
     * 
     * @param value
     *     allowed object is
     *     {@link BairroType }
     *     
     */
    public void setBairro(BairroType value) {
        this.bairro = value;
    }

    /**
     * Gets the value of the cep property.
     * 
     * @return
     *     possible object is
     *     {@link CEPType }
     *     
     */
    public CEPType getCEP() {
        return cep;
    }

    /**
     * Sets the value of the cep property.
     * 
     * @param value
     *     allowed object is
     *     {@link CEPType }
     *     
     */
    public void setCEP(CEPType value) {
        this.cep = value;
    }

    /**
     * Gets the value of the municipio property.
     * 
     * @return
     *     possible object is
     *     {@link MunicipioType }
     *     
     */
    public MunicipioType getMunicipio() {
        return municipio;
    }

    /**
     * Sets the value of the municipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link MunicipioType }
     *     
     */
    public void setMunicipio(MunicipioType value) {
        this.municipio = value;
    }

    /**
     * Gets the value of the pais property.
     * 
     * @return
     *     possible object is
     *     {@link PaisType }
     *     
     */
    public PaisType getPais() {
        return pais;
    }

    /**
     * Sets the value of the pais property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaisType }
     *     
     */
    public void setPais(PaisType value) {
        this.pais = value;
    }

    /**
     * Gets the value of the municipioInternacional property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMunicipioInternacional() {
        return municipioInternacional;
    }

    /**
     * Sets the value of the municipioInternacional property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMunicipioInternacional(String value) {
        this.municipioInternacional = value;
    }

}
