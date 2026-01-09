
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
 *         <element ref="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/filtropesquisaprofissionalsaude}FiltroPesquisaProfissionalSaude"/>
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
    "filtroPesquisaProfissionalSaude"
})
@XmlRootElement(name = "requestConsultarProfissionalSaude")
public class RequestConsultarProfissionalSaude {

    @XmlElement(name = "FiltroPesquisaProfissionalSaude", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/filtropesquisaprofissionalsaude", required = true)
    protected FiltroPesquisaProfissionalSaudeType filtroPesquisaProfissionalSaude;

    /**
     * Filtro de Pesquisa do Profissional por CNS e CPF.
     * 
     * @return
     *     possible object is
     *     {@link FiltroPesquisaProfissionalSaudeType }
     *     
     */
    public FiltroPesquisaProfissionalSaudeType getFiltroPesquisaProfissionalSaude() {
        return filtroPesquisaProfissionalSaude;
    }

    /**
     * Sets the value of the filtroPesquisaProfissionalSaude property.
     * 
     * @param value
     *     allowed object is
     *     {@link FiltroPesquisaProfissionalSaudeType }
     *     
     */
    public void setFiltroPesquisaProfissionalSaude(FiltroPesquisaProfissionalSaudeType value) {
        this.filtroPesquisaProfissionalSaude = value;
    }

}
