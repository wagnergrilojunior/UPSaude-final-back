
package com.upsaude.integration.cnes.wsdl.profissional;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaisType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="PaisType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigoPais" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <pattern value="[0-9]*"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="codigoPaisAntigo" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <pattern value="[0-9]*"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="nomePais" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaisType", namespace = "http://servicos.saude.gov.br/schema/corporativo/v1r2/pais", propOrder = {
    "codigoPais",
    "codigoPaisAntigo",
    "nomePais"
})
public class PaisType {

    protected String codigoPais;
    protected String codigoPaisAntigo;
    protected String nomePais;

    /**
     * Gets the value of the codigoPais property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoPais() {
        return codigoPais;
    }

    /**
     * Sets the value of the codigoPais property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoPais(String value) {
        this.codigoPais = value;
    }

    /**
     * Gets the value of the codigoPaisAntigo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoPaisAntigo() {
        return codigoPaisAntigo;
    }

    /**
     * Sets the value of the codigoPaisAntigo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoPaisAntigo(String value) {
        this.codigoPaisAntigo = value;
    }

    /**
     * Gets the value of the nomePais property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomePais() {
        return nomePais;
    }

    /**
     * Sets the value of the nomePais property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomePais(String value) {
        this.nomePais = value;
    }

}
