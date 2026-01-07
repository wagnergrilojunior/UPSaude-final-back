
package com.upsaude.integration.cnes.wsdl.profissional;

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
 *         <element ref="{http://servicos.saude.gov.br/schema/profissionalsaude/v2r0/profissionalsaude}ProfissionalSaude"/>
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
    "profissionalSaude"
})
@XmlRootElement(name = "responseConsultarProfissionalSaude")
public class ResponseConsultarProfissionalSaude {

    @XmlElement(name = "ProfissionalSaude", namespace = "http://servicos.saude.gov.br/schema/profissionalsaude/v2r0/profissionalsaude", required = true)
    protected ProfissionalSaudeType profissionalSaude;

    /**
     * Gets the value of the profissionalSaude property.
     * 
     * @return
     *     possible object is
     *     {@link ProfissionalSaudeType }
     *     
     */
    public ProfissionalSaudeType getProfissionalSaude() {
        return profissionalSaude;
    }

    /**
     * Sets the value of the profissionalSaude property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfissionalSaudeType }
     *     
     */
    public void setProfissionalSaude(ProfissionalSaudeType value) {
        this.profissionalSaude = value;
    }

}
