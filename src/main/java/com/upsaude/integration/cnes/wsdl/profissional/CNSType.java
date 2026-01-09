
package com.upsaude.integration.cnes.wsdl.profissional;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
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
 *         <element name="numeroCNS">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="15"/>
 *               <maxLength value="15"/>
 *               <pattern value="[0-9]*"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
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
    @XmlElementRef(name = "dataAtribuicao", namespace = "http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dataAtribuicao;
    @XmlElementRef(name = "tipoCartao", namespace = "http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", type = JAXBElement.class, required = false)
    protected JAXBElement<TipoCNSType> tipoCartao;
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
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDataAtribuicao() {
        return dataAtribuicao;
    }

    /**
     * Sets the value of the dataAtribuicao property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDataAtribuicao(JAXBElement<XMLGregorianCalendar> value) {
        this.dataAtribuicao = value;
    }

    /**
     * Gets the value of the tipoCartao property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link TipoCNSType }{@code >}
     *     
     */
    public JAXBElement<TipoCNSType> getTipoCartao() {
        return tipoCartao;
    }

    /**
     * Sets the value of the tipoCartao property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link TipoCNSType }{@code >}
     *     
     */
    public void setTipoCartao(JAXBElement<TipoCNSType> value) {
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
