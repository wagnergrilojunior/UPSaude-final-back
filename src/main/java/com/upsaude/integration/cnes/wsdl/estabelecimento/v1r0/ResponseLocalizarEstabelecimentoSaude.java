
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

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
 *         <element ref="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadoslocalizacaoestabelecimentosaude}ResultadosLocalizacaoEstabelecimentoSaude" minOccurs="0"/>
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
    "resultadosLocalizacaoEstabelecimentoSaude"
})
@XmlRootElement(name = "responseLocalizarEstabelecimentoSaude")
public class ResponseLocalizarEstabelecimentoSaude {

    @XmlElement(name = "ResultadosLocalizacaoEstabelecimentoSaude", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadoslocalizacaoestabelecimentosaude")
    protected ResultadosLocalizacaoEstabelecimentoSaudeType resultadosLocalizacaoEstabelecimentoSaude;

    /**
     * Resultado localização de estabelecimentos.
     * 
     * @return
     *     possible object is
     *     {@link ResultadosLocalizacaoEstabelecimentoSaudeType }
     *     
     */
    public ResultadosLocalizacaoEstabelecimentoSaudeType getResultadosLocalizacaoEstabelecimentoSaude() {
        return resultadosLocalizacaoEstabelecimentoSaude;
    }

    /**
     * Sets the value of the resultadosLocalizacaoEstabelecimentoSaude property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultadosLocalizacaoEstabelecimentoSaudeType }
     *     
     */
    public void setResultadosLocalizacaoEstabelecimentoSaude(ResultadosLocalizacaoEstabelecimentoSaudeType value) {
        this.resultadosLocalizacaoEstabelecimentoSaude = value;
    }

}
