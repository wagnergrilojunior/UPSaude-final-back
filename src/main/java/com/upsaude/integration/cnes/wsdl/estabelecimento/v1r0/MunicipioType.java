
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MunicipioType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="MunicipioType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigoMunicipio" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="1"/>
 *               <maxLength value="6"/>
 *               <pattern value="[0-9]*"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="nomeMunicipio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="UF" type="{http://servicos.saude.gov.br/schema/corporativo/v1r1/uf}UFType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MunicipioType", namespace = "http://servicos.saude.gov.br/schema/corporativo/v1r2/municipio", propOrder = {
    "codigoMunicipio",
    "nomeMunicipio",
    "uf"
})
public class MunicipioType {

    protected String codigoMunicipio;
    protected String nomeMunicipio;
    @XmlElement(name = "UF")
    protected UFType uf;

    /**
     * Gets the value of the codigoMunicipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    /**
     * Sets the value of the codigoMunicipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoMunicipio(String value) {
        this.codigoMunicipio = value;
    }

    /**
     * Gets the value of the nomeMunicipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeMunicipio() {
        return nomeMunicipio;
    }

    /**
     * Sets the value of the nomeMunicipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeMunicipio(String value) {
        this.nomeMunicipio = value;
    }

    /**
     * Gets the value of the uf property.
     * 
     * @return
     *     possible object is
     *     {@link UFType }
     *     
     */
    public UFType getUF() {
        return uf;
    }

    /**
     * Sets the value of the uf property.
     * 
     * @param value
     *     allowed object is
     *     {@link UFType }
     *     
     */
    public void setUF(UFType value) {
        this.uf = value;
    }

}
