
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
 *         <element ref="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisadadoscomplementarescnes}ResultadoPesquisaDadosComplementaresCnes" minOccurs="0"/>
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
    "resultadoPesquisaDadosComplementaresCnes"
})
@XmlRootElement(name = "responseConsultarDadosComplementaresEstabelecimentoSaude")
public class ResponseConsultarDadosComplementaresEstabelecimentoSaude {

    @XmlElement(name = "ResultadoPesquisaDadosComplementaresCnes", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisadadoscomplementarescnes")
    protected ResultadoPesquisaDadosComplementaresCnesType resultadoPesquisaDadosComplementaresCnes;

    /**
     * Gets the value of the resultadoPesquisaDadosComplementaresCnes property.
     * 
     * @return
     *     possible object is
     *     {@link ResultadoPesquisaDadosComplementaresCnesType }
     *     
     */
    public ResultadoPesquisaDadosComplementaresCnesType getResultadoPesquisaDadosComplementaresCnes() {
        return resultadoPesquisaDadosComplementaresCnes;
    }

    /**
     * Sets the value of the resultadoPesquisaDadosComplementaresCnes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultadoPesquisaDadosComplementaresCnesType }
     *     
     */
    public void setResultadoPesquisaDadosComplementaresCnes(ResultadoPesquisaDadosComplementaresCnesType value) {
        this.resultadoPesquisaDadosComplementaresCnes = value;
    }

}
