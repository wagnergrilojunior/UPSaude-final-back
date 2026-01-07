
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
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/dadosgeraiscnes}DadosGeraisEstabelecimentoSaude" minOccurs="0"/>
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
    "dadosGeraisEstabelecimentoSaude"
})
@XmlRootElement(name = "responseConsultarEstabelecimentoSaude")
public class ResponseConsultarEstabelecimentoSaude {

    @XmlElement(name = "DadosGeraisEstabelecimentoSaude", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosgeraiscnes")
    protected DadosGeraisEstabelecimentoSaudeType dadosGeraisEstabelecimentoSaude;

    /**
     * Gets the value of the dadosGeraisEstabelecimentoSaude property.
     * 
     * @return
     *     possible object is
     *     {@link DadosGeraisEstabelecimentoSaudeType }
     *     
     */
    public DadosGeraisEstabelecimentoSaudeType getDadosGeraisEstabelecimentoSaude() {
        return dadosGeraisEstabelecimentoSaude;
    }

    /**
     * Sets the value of the dadosGeraisEstabelecimentoSaude property.
     * 
     * @param value
     *     allowed object is
     *     {@link DadosGeraisEstabelecimentoSaudeType }
     *     
     */
    public void setDadosGeraisEstabelecimentoSaude(DadosGeraisEstabelecimentoSaudeType value) {
        this.dadosGeraisEstabelecimentoSaude = value;
    }

}
