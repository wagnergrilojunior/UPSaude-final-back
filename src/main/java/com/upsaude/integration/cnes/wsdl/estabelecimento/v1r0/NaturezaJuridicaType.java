
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NaturezaJuridicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="NaturezaJuridicaType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigoNaturezaJuridica">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="4"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="descricaoNaturezaJuridica">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="80"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="codigoNaturezaJuridicaConcla">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="4"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="tipoNaturezaJuridica" type="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/tiponaturezajuridica}TipoNaturezaJuridicaType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NaturezaJuridicaType", namespace = "http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/naturezajuridica", propOrder = {
    "codigoNaturezaJuridica",
    "descricaoNaturezaJuridica",
    "codigoNaturezaJuridicaConcla",
    "tipoNaturezaJuridica"
})
public class NaturezaJuridicaType {

    @XmlElement(required = true)
    protected String codigoNaturezaJuridica;
    @XmlElement(required = true)
    protected String descricaoNaturezaJuridica;
    @XmlElement(required = true)
    protected String codigoNaturezaJuridicaConcla;
    @XmlElement(required = true)
    protected TipoNaturezaJuridicaType tipoNaturezaJuridica;

    /**
     * Gets the value of the codigoNaturezaJuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoNaturezaJuridica() {
        return codigoNaturezaJuridica;
    }

    /**
     * Sets the value of the codigoNaturezaJuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoNaturezaJuridica(String value) {
        this.codigoNaturezaJuridica = value;
    }

    /**
     * Gets the value of the descricaoNaturezaJuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescricaoNaturezaJuridica() {
        return descricaoNaturezaJuridica;
    }

    /**
     * Sets the value of the descricaoNaturezaJuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescricaoNaturezaJuridica(String value) {
        this.descricaoNaturezaJuridica = value;
    }

    /**
     * Gets the value of the codigoNaturezaJuridicaConcla property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoNaturezaJuridicaConcla() {
        return codigoNaturezaJuridicaConcla;
    }

    /**
     * Sets the value of the codigoNaturezaJuridicaConcla property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoNaturezaJuridicaConcla(String value) {
        this.codigoNaturezaJuridicaConcla = value;
    }

    /**
     * Gets the value of the tipoNaturezaJuridica property.
     * 
     * @return
     *     possible object is
     *     {@link TipoNaturezaJuridicaType }
     *     
     */
    public TipoNaturezaJuridicaType getTipoNaturezaJuridica() {
        return tipoNaturezaJuridica;
    }

    /**
     * Sets the value of the tipoNaturezaJuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoNaturezaJuridicaType }
     *     
     */
    public void setTipoNaturezaJuridica(TipoNaturezaJuridicaType value) {
        this.tipoNaturezaJuridica = value;
    }

}
