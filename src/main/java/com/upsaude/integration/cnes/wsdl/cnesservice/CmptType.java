
package com.upsaude.integration.cnes.wsdl.cnesservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cmptType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="cmptType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="cmpt">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="6"/>
 *               <maxLength value="6"/>
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
@XmlType(name = "cmptType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/cmpt", propOrder = {
    "cmpt"
})
public class CmptType {

    @XmlElement(required = true)
    protected String cmpt;

    /**
     * Gets the value of the cmpt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCmpt() {
        return cmpt;
    }

    /**
     * Sets the value of the cmpt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCmpt(String value) {
        this.cmpt = value;
    }

}
