
package com.upsaude.integration.cnes.wsdl.cnesservice;

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
 *         <element ref="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisaestabelecimentosaude}ResultadoPesquisaEstabelecimentoSaude" minOccurs="0"/>
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
    "resultadoPesquisaEstabelecimentoSaude"
})
@XmlRootElement(name = "responseConsultarEstabelecimentoSaude")
public class ResponseConsultarEstabelecimentoSaude {

    @XmlElement(name = "ResultadoPesquisaEstabelecimentoSaude", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisaestabelecimentosaude")
    protected ResultadoPesquisaEstabelecimentoSaudeType resultadoPesquisaEstabelecimentoSaude;

    /**
     * Gets the value of the resultadoPesquisaEstabelecimentoSaude property.
     * 
     * @return
     *     possible object is
     *     {@link ResultadoPesquisaEstabelecimentoSaudeType }
     *     
     */
    public ResultadoPesquisaEstabelecimentoSaudeType getResultadoPesquisaEstabelecimentoSaude() {
        return resultadoPesquisaEstabelecimentoSaude;
    }

    /**
     * Sets the value of the resultadoPesquisaEstabelecimentoSaude property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultadoPesquisaEstabelecimentoSaudeType }
     *     
     */
    public void setResultadoPesquisaEstabelecimentoSaude(ResultadoPesquisaEstabelecimentoSaudeType value) {
        this.resultadoPesquisaEstabelecimentoSaude = value;
    }

}
