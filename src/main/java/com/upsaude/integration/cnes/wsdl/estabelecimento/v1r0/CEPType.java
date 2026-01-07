
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Número do CEP.
 * 
 * <p>Java class for CEPType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="CEPType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="numeroCEP" type="{http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/cep}NumeroCEPType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CEPType", namespace = "http://servicos.saude.gov.br/schema/corporativo/endereco/v1r1/cep", propOrder = {
    "numeroCEP"
})
public class CEPType {

    @XmlElement(required = true)
    protected String numeroCEP;

    /**
     * Gets the value of the numeroCEP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCEP() {
        return numeroCEP;
    }

    /**
     * Sets the value of the numeroCEP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCEP(String value) {
        this.numeroCEP = value;
    }

}
