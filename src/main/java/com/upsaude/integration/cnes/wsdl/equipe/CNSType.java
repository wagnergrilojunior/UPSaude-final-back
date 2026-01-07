
package com.upsaude.integration.cnes.wsdl.equipe;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CNSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="CNSType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="numeroCNS" type="{http://servicos.saude.gov.br/schema/cadsus/v5r0/cns}FormatoNumeroCNSType"/>
 *         <element name="dataAtribuicao" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="tipoCartao" type="{http://servicos.saude.gov.br/schema/cadsus/v5r0/cns}TipoCNSType" minOccurs="0"/>
 *         <element name="manual" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         <element name="justificativaManual" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CNSType", namespace = "http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", propOrder = {
    "numeroCNS",
    "dataAtribuicao",
    "tipoCartao",
    "manual",
    "justificativaManual"
})
public class CNSType {

    @XmlElement(required = true)
    protected String numeroCNS;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAtribuicao;
    @XmlSchemaType(name = "string")
    protected TipoCNSType tipoCartao;
    protected Boolean manual;
    protected String justificativaManual;

    /**
     * Gets the value of the numeroCNS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCNS() {
        return numeroCNS;
    }

    /**
     * Sets the value of the numeroCNS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCNS(String value) {
        this.numeroCNS = value;
    }

    /**
     * Gets the value of the dataAtribuicao property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAtribuicao() {
        return dataAtribuicao;
    }

    /**
     * Sets the value of the dataAtribuicao property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAtribuicao(XMLGregorianCalendar value) {
        this.dataAtribuicao = value;
    }

    /**
     * Gets the value of the tipoCartao property.
     * 
     * @return
     *     possible object is
     *     {@link TipoCNSType }
     *     
     */
    public TipoCNSType getTipoCartao() {
        return tipoCartao;
    }

    /**
     * Sets the value of the tipoCartao property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoCNSType }
     *     
     */
    public void setTipoCartao(TipoCNSType value) {
        this.tipoCartao = value;
    }

    /**
     * Gets the value of the manual property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isManual() {
        return manual;
    }

    /**
     * Sets the value of the manual property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setManual(Boolean value) {
        this.manual = value;
    }

    /**
     * Gets the value of the justificativaManual property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJustificativaManual() {
        return justificativaManual;
    }

    /**
     * Sets the value of the justificativaManual property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJustificativaManual(String value) {
        this.justificativaManual = value;
    }

}
