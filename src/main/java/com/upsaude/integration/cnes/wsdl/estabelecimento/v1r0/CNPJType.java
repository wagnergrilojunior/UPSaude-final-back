
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CNPJType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="CNPJType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="numeroCNPJ">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="14"/>
 *               <maxLength value="14"/>
 *               <pattern value="[0-9]*"/>
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
@XmlType(name = "CNPJType", namespace = "http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/cnpj", propOrder = {
    "numeroCNPJ"
})
public class CNPJType {

    @XmlElement(required = true)
    protected String numeroCNPJ;

    /**
     * Gets the value of the numeroCNPJ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCNPJ() {
        return numeroCNPJ;
    }

    /**
     * Sets the value of the numeroCNPJ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCNPJ(String value) {
        this.numeroCNPJ = value;
    }

}
