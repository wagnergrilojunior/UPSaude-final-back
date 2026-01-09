
package com.upsaude.integration.cnes.wsdl.equipe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigoEquipe" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/equipe}CodigoEquipeType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codigoEquipe"
})
@XmlRootElement(name = "requestDetalharEquipe")
public class RequestDetalharEquipe {

    @XmlElement(required = true)
    protected String codigoEquipe;

    /**
     * Gets the value of the codigoEquipe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoEquipe() {
        return codigoEquipe;
    }

    /**
     * Sets the value of the codigoEquipe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoEquipe(String value) {
        this.codigoEquipe = value;
    }

}
