
package com.upsaude.integration.cnes.wsdl.cnesservice;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SamuType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SamuType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="placa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="chassi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="codigoPrefixoAeronave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="numeroEmbacacaoMarinha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="dataAtivacao" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         <element name="dataDesativacao" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         <element name="codigoDesativacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SamuType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/samu", propOrder = {
    "placa",
    "chassi",
    "codigoPrefixoAeronave",
    "numeroEmbacacaoMarinha",
    "dataAtivacao",
    "dataDesativacao",
    "codigoDesativacao"
})
public class SamuType {

    protected String placa;
    protected String chassi;
    protected String codigoPrefixoAeronave;
    protected String numeroEmbacacaoMarinha;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataAtivacao;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataDesativacao;
    protected String codigoDesativacao;

    /**
     * Gets the value of the placa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Sets the value of the placa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlaca(String value) {
        this.placa = value;
    }

    /**
     * Gets the value of the chassi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChassi() {
        return chassi;
    }

    /**
     * Sets the value of the chassi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChassi(String value) {
        this.chassi = value;
    }

    /**
     * Gets the value of the codigoPrefixoAeronave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoPrefixoAeronave() {
        return codigoPrefixoAeronave;
    }

    /**
     * Sets the value of the codigoPrefixoAeronave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoPrefixoAeronave(String value) {
        this.codigoPrefixoAeronave = value;
    }

    /**
     * Gets the value of the numeroEmbacacaoMarinha property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroEmbacacaoMarinha() {
        return numeroEmbacacaoMarinha;
    }

    /**
     * Sets the value of the numeroEmbacacaoMarinha property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroEmbacacaoMarinha(String value) {
        this.numeroEmbacacaoMarinha = value;
    }

    /**
     * Gets the value of the dataAtivacao property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAtivacao() {
        return dataAtivacao;
    }

    /**
     * Sets the value of the dataAtivacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAtivacao(XMLGregorianCalendar value) {
        this.dataAtivacao = value;
    }

    /**
     * Gets the value of the dataDesativacao property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDesativacao() {
        return dataDesativacao;
    }

    /**
     * Sets the value of the dataDesativacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDesativacao(XMLGregorianCalendar value) {
        this.dataDesativacao = value;
    }

    /**
     * Gets the value of the codigoDesativacao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoDesativacao() {
        return codigoDesativacao;
    }

    /**
     * Sets the value of the codigoDesativacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoDesativacao(String value) {
        this.codigoDesativacao = value;
    }

}
