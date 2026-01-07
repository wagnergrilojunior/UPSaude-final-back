
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
 *         <element ref="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/filtrolocalizacaoestabelecimentosaude}FiltroLocalizacaoEstabelecimentoSaude"/>
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
    "filtroLocalizacaoEstabelecimentoSaude"
})
@XmlRootElement(name = "requestLocalizarEstabelecimentoSaude")
public class RequestLocalizarEstabelecimentoSaude {

    @XmlElement(name = "FiltroLocalizacaoEstabelecimentoSaude", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/filtrolocalizacaoestabelecimentosaude", required = true)
    protected FiltroLocalizacaoEstabelecimentoSaudeType filtroLocalizacaoEstabelecimentoSaude;

    /**
     * Filtro de Localização.
     * 
     * @return
     *     possible object is
     *     {@link FiltroLocalizacaoEstabelecimentoSaudeType }
     *     
     */
    public FiltroLocalizacaoEstabelecimentoSaudeType getFiltroLocalizacaoEstabelecimentoSaude() {
        return filtroLocalizacaoEstabelecimentoSaude;
    }

    /**
     * Sets the value of the filtroLocalizacaoEstabelecimentoSaude property.
     * 
     * @param value
     *     allowed object is
     *     {@link FiltroLocalizacaoEstabelecimentoSaudeType }
     *     
     */
    public void setFiltroLocalizacaoEstabelecimentoSaude(FiltroLocalizacaoEstabelecimentoSaudeType value) {
        this.filtroLocalizacaoEstabelecimentoSaude = value;
    }

}
