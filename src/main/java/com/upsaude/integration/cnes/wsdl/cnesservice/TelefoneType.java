
package com.upsaude.integration.cnes.wsdl.cnesservice;

import java.math.BigInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TelefoneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="TelefoneType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="identificador" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *         <element name="TipoTelefone" type="{http://servicos.saude.gov.br/schema/corporativo/telefone/v1r1/tipotelefone}TipoTelefoneType" minOccurs="0"/>
 *         <element name="DDI" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *               <maxInclusive value="999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="DDD" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *               <maxInclusive value="99"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="numeroTelefone" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *               <maxInclusive value="999999999"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TelefoneType", namespace = "http://servicos.saude.gov.br/schema/corporativo/telefone/v1r2/telefone", propOrder = {
    "identificador",
    "tipoTelefone",
    "ddi",
    "ddd",
    "numeroTelefone"
})
public class TelefoneType {

    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger identificador;
    @XmlElement(name = "TipoTelefone")
    protected TipoTelefoneType tipoTelefone;
    @XmlElement(name = "DDI")
    protected Integer ddi;
    @XmlElement(name = "DDD")
    protected Integer ddd;
    protected Integer numeroTelefone;

    /**
     * Gets the value of the identificador property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdentificador() {
        return identificador;
    }

    /**
     * Sets the value of the identificador property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdentificador(BigInteger value) {
        this.identificador = value;
    }

    /**
     * Gets the value of the tipoTelefone property.
     * 
     * @return
     *     possible object is
     *     {@link TipoTelefoneType }
     *     
     */
    public TipoTelefoneType getTipoTelefone() {
        return tipoTelefone;
    }

    /**
     * Sets the value of the tipoTelefone property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoTelefoneType }
     *     
     */
    public void setTipoTelefone(TipoTelefoneType value) {
        this.tipoTelefone = value;
    }

    /**
     * Gets the value of the ddi property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDDI() {
        return ddi;
    }

    /**
     * Sets the value of the ddi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDDI(Integer value) {
        this.ddi = value;
    }

    /**
     * Gets the value of the ddd property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDDD() {
        return ddd;
    }

    /**
     * Sets the value of the ddd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDDD(Integer value) {
        this.ddd = value;
    }

    /**
     * Gets the value of the numeroTelefone property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroTelefone() {
        return numeroTelefone;
    }

    /**
     * Sets the value of the numeroTelefone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroTelefone(Integer value) {
        this.numeroTelefone = value;
    }

}
