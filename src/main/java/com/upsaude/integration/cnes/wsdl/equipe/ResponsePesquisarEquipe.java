
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
 *         <element name="Equipes" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/equipe}EquipesType" minOccurs="0"/>
 *         <element name="Paginacao" type="{http://servicos.saude.gov.br/wsdl/mensageria/v1/paginacao}PaginacaoType"/>
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
    "equipes",
    "paginacao"
})
@XmlRootElement(name = "responsePesquisarEquipe")
public class ResponsePesquisarEquipe {

    @XmlElement(name = "Equipes")
    protected EquipesType equipes;
    @XmlElement(name = "Paginacao", required = true)
    protected PaginacaoType paginacao;

    /**
     * Gets the value of the equipes property.
     * 
     * @return
     *     possible object is
     *     {@link EquipesType }
     *     
     */
    public EquipesType getEquipes() {
        return equipes;
    }

    /**
     * Sets the value of the equipes property.
     * 
     * @param value
     *     allowed object is
     *     {@link EquipesType }
     *     
     */
    public void setEquipes(EquipesType value) {
        this.equipes = value;
    }

    /**
     * Gets the value of the paginacao property.
     * 
     * @return
     *     possible object is
     *     {@link PaginacaoType }
     *     
     */
    public PaginacaoType getPaginacao() {
        return paginacao;
    }

    /**
     * Sets the value of the paginacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaginacaoType }
     *     
     */
    public void setPaginacao(PaginacaoType value) {
        this.paginacao = value;
    }

}
