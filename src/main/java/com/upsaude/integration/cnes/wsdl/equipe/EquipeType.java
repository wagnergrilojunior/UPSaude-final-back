
package com.upsaude.integration.cnes.wsdl.equipe;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EquipeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="EquipeType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigoEquipe" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/equipe}CodigoEquipeType"/>
 *         <element name="descricaoEquipe" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="codigoTipoEquipe" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="siglaTipoEquipe" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="descricaoTipoEquipe" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EquipeType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/equipe", propOrder = {
    "codigoEquipe",
    "descricaoEquipe",
    "codigoTipoEquipe",
    "siglaTipoEquipe",
    "descricaoTipoEquipe"
})
public class EquipeType {

    @XmlElement(required = true)
    protected String codigoEquipe;
    @XmlElement(required = true)
    protected String descricaoEquipe;
    @XmlElement(required = true)
    protected String codigoTipoEquipe;
    @XmlElement(required = true)
    protected String siglaTipoEquipe;
    @XmlElement(required = true)
    protected String descricaoTipoEquipe;

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
     * Gets the value of the descricaoEquipe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescricaoEquipe() {
        return descricaoEquipe;
    }

    /**
     * Sets the value of the descricaoEquipe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescricaoEquipe(String value) {
        this.descricaoEquipe = value;
    }

    /**
     * Gets the value of the codigoTipoEquipe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoTipoEquipe() {
        return codigoTipoEquipe;
    }

    /**
     * Sets the value of the codigoTipoEquipe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoTipoEquipe(String value) {
        this.codigoTipoEquipe = value;
    }

    /**
     * Gets the value of the siglaTipoEquipe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaTipoEquipe() {
        return siglaTipoEquipe;
    }

    /**
     * Sets the value of the siglaTipoEquipe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaTipoEquipe(String value) {
        this.siglaTipoEquipe = value;
    }

    /**
     * Gets the value of the descricaoTipoEquipe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescricaoTipoEquipe() {
        return descricaoTipoEquipe;
    }

    /**
     * Sets the value of the descricaoTipoEquipe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescricaoTipoEquipe(String value) {
        this.descricaoTipoEquipe = value;
    }

}
