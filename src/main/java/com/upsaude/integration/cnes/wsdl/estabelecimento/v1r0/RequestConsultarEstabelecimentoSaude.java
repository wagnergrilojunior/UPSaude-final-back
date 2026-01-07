
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
 *         <element ref="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/filtropesquisaestabelecimentosaude}FiltroPesquisaEstabelecimentoSaude"/>
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
    "filtroPesquisaEstabelecimentoSaude"
})
@XmlRootElement(name = "requestConsultarEstabelecimentoSaude")
public class RequestConsultarEstabelecimentoSaude {

    @XmlElement(name = "FiltroPesquisaEstabelecimentoSaude", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/filtropesquisaestabelecimentosaude", required = true)
    protected FiltroPesquisaEstabelecimentoSaudeType filtroPesquisaEstabelecimentoSaude;

    /**
     * Filtro de Pesquisa.
     * 
     * @return
     *     possible object is
     *     {@link FiltroPesquisaEstabelecimentoSaudeType }
     *     
     */
    public FiltroPesquisaEstabelecimentoSaudeType getFiltroPesquisaEstabelecimentoSaude() {
        return filtroPesquisaEstabelecimentoSaude;
    }

    /**
     * Sets the value of the filtroPesquisaEstabelecimentoSaude property.
     * 
     * @param value
     *     allowed object is
     *     {@link FiltroPesquisaEstabelecimentoSaudeType }
     *     
     */
    public void setFiltroPesquisaEstabelecimentoSaude(FiltroPesquisaEstabelecimentoSaudeType value) {
        this.filtroPesquisaEstabelecimentoSaude = value;
    }

}
