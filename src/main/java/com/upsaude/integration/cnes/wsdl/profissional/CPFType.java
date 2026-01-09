
package com.upsaude.integration.cnes.wsdl.profissional;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CPFType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="CPFType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="numeroCPF">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="11"/>
 *               <maxLength value="11"/>
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
@XmlType(name = "CPFType", namespace = "http://servicos.saude.gov.br/schema/corporativo/documento/v1r2/cpf", propOrder = {
    "numeroCPF"
})
public class CPFType {

    @XmlElement(required = true)
    protected String numeroCPF;

    /**
     * Gets the value of the numeroCPF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCPF() {
        return numeroCPF;
    }

    /**
     * Sets the value of the numeroCPF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCPF(String value) {
        this.numeroCPF = value;
    }

}
