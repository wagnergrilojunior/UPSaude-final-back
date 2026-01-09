
package com.upsaude.integration.cnes.wsdl.cnesservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DadosBasicosEstabelecimentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DadosBasicosEstabelecimentoType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/codigocnes}CodigoCNES"/>
 *         <element name="NomeFantasia" type="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/nomejuridico}NomeJuridicoType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DadosBasicosEstabelecimentoType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosestabelecimento", propOrder = {
    "codigoCNES",
    "nomeFantasia"
})
public class DadosBasicosEstabelecimentoType {

    @XmlElement(name = "CodigoCNES", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/codigocnes", required = true)
    protected CodigoCNESType codigoCNES;
    @XmlElement(name = "NomeFantasia")
    protected NomeJuridicoType nomeFantasia;

    /**
     * Código do Estabelecimento de Saúde..
     * 
     * @return
     *     possible object is
     *     {@link CodigoCNESType }
     *     
     */
    public CodigoCNESType getCodigoCNES() {
        return codigoCNES;
    }

    /**
     * Sets the value of the codigoCNES property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodigoCNESType }
     *     
     */
    public void setCodigoCNES(CodigoCNESType value) {
        this.codigoCNES = value;
    }

    /**
     * Gets the value of the nomeFantasia property.
     * 
     * @return
     *     possible object is
     *     {@link NomeJuridicoType }
     *     
     */
    public NomeJuridicoType getNomeFantasia() {
        return nomeFantasia;
    }

    /**
     * Sets the value of the nomeFantasia property.
     * 
     * @param value
     *     allowed object is
     *     {@link NomeJuridicoType }
     *     
     */
    public void setNomeFantasia(NomeJuridicoType value) {
        this.nomeFantasia = value;
    }

}
