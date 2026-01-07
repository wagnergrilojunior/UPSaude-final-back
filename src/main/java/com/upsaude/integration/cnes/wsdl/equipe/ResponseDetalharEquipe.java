
package com.upsaude.integration.cnes.wsdl.equipe;

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
 *         <element name="codigoCNES" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="codigoEquipe" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/equipe}CodigoEquipeType"/>
 *         <element name="DadosBasicosProfissionais" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosprofissional}DadosBasicosProfissionaisType"/>
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
    "codigoCNES",
    "codigoEquipe",
    "dadosBasicosProfissionais"
})
@XmlRootElement(name = "responseDetalharEquipe")
public class ResponseDetalharEquipe {

    @XmlElement(required = true)
    protected String codigoCNES;
    @XmlElement(required = true)
    protected String codigoEquipe;
    @XmlElement(name = "DadosBasicosProfissionais", required = true)
    protected DadosBasicosProfissionaisType dadosBasicosProfissionais;

    /**
     * Gets the value of the codigoCNES property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoCNES() {
        return codigoCNES;
    }

    /**
     * Sets the value of the codigoCNES property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoCNES(String value) {
        this.codigoCNES = value;
    }

    /**
     * Gets the value of the codigoEquipe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoEquipe() {
        return codigoEquipe;
    }

    /**
     * Sets the value of the codigoEquipe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoEquipe(String value) {
        this.codigoEquipe = value;
    }

    /**
     * Gets the value of the dadosBasicosProfissionais property.
     * 
     * @return
     *     possible object is
     *     {@link DadosBasicosProfissionaisType }
     *     
     */
    public DadosBasicosProfissionaisType getDadosBasicosProfissionais() {
        return dadosBasicosProfissionais;
    }

    /**
     * Sets the value of the dadosBasicosProfissionais property.
     * 
     * @param value
     *     allowed object is
     *     {@link DadosBasicosProfissionaisType }
     *     
     */
    public void setDadosBasicosProfissionais(DadosBasicosProfissionaisType value) {
        this.dadosBasicosProfissionais = value;
    }

}
