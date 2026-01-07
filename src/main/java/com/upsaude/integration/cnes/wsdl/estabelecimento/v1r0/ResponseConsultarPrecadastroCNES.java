
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
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/dadosprecadastrocnes}DadosPreCadastroCNES" minOccurs="0"/>
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
    "dadosPreCadastroCNES"
})
@XmlRootElement(name = "responseConsultarPrecadastroCNES")
public class ResponseConsultarPrecadastroCNES {

    @XmlElement(name = "DadosPreCadastroCNES", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosprecadastrocnes")
    protected DadosPreCadastroCNESType dadosPreCadastroCNES;

    /**
     * Resultado com os dados de precadastro do Estabelecimento de Saúde.
     * 
     * @return
     *     possible object is
     *     {@link DadosPreCadastroCNESType }
     *     
     */
    public DadosPreCadastroCNESType getDadosPreCadastroCNES() {
        return dadosPreCadastroCNES;
    }

    /**
     * Sets the value of the dadosPreCadastroCNES property.
     * 
     * @param value
     *     allowed object is
     *     {@link DadosPreCadastroCNESType }
     *     
     */
    public void setDadosPreCadastroCNES(DadosPreCadastroCNESType value) {
        this.dadosPreCadastroCNES = value;
    }

}
