
package com.upsaude.integration.cnes.wsdl.profissional;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FiltroPesquisaProfissionalSaudeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="FiltroPesquisaProfissionalSaudeType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://servicos.saude.gov.br/schema/cadsus/v5r0/cns}CNS" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/corporativo/documento/v1r2/cpf}CPF" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FiltroPesquisaProfissionalSaudeType", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/filtropesquisaprofissionalsaude", propOrder = {
    "cns",
    "cpf"
})
public class FiltroPesquisaProfissionalSaudeType {

    @XmlElement(name = "CNS", namespace = "http://servicos.saude.gov.br/schema/cadsus/v5r0/cns")
    protected CNSType cns;
    @XmlElement(name = "CPF", namespace = "http://servicos.saude.gov.br/schema/corporativo/documento/v1r2/cpf")
    protected CPFType cpf;

    /**
     * CNS do Profissional de Saúde
     * 
     * @return
     *     possible object is
     *     {@link CNSType }
     *     
     */
    public CNSType getCNS() {
        return cns;
    }

    /**
     * Sets the value of the cns property.
     * 
     * @param value
     *     allowed object is
     *     {@link CNSType }
     *     
     */
    public void setCNS(CNSType value) {
        this.cns = value;
    }

    /**
     * CPF do Profissional de Saúde
     * 
     * @return
     *     possible object is
     *     {@link CPFType }
     *     
     */
    public CPFType getCPF() {
        return cpf;
    }

    /**
     * Sets the value of the cpf property.
     * 
     * @param value
     *     allowed object is
     *     {@link CPFType }
     *     
     */
    public void setCPF(CPFType value) {
        this.cpf = value;
    }

}
