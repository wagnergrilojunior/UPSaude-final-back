
package com.upsaude.integration.cnes.wsdl.cnesservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CBOType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="CBOType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigoCBO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="descricaoCBO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CBOType", namespace = "http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/cbo", propOrder = {
    "codigoCBO",
    "descricaoCBO"
})
public class CBOType {

    @XmlElement(required = true)
    protected String codigoCBO;
    protected String descricaoCBO;

    /**
     * Gets the value of the codigoCBO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoCBO() {
        return codigoCBO;
    }

    /**
     * Sets the value of the codigoCBO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoCBO(String value) {
        this.codigoCBO = value;
    }

    /**
     * Gets the value of the descricaoCBO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescricaoCBO() {
        return descricaoCBO;
    }

    /**
     * Sets the value of the descricaoCBO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescricaoCBO(String value) {
        this.descricaoCBO = value;
    }

}
