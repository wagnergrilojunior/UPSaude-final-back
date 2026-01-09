
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for localizacaoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="localizacaoType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="longitude" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="latitude" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="geoJson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "localizacaoType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/localizacao", propOrder = {
    "longitude",
    "latitude",
    "geoJson"
})
public class LocalizacaoType {

    @XmlElement(required = true)
    protected String longitude;
    @XmlElement(required = true)
    protected String latitude;
    protected String geoJson;

    /**
     * Gets the value of the longitude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongitude(String value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the latitude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatitude(String value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the geoJson property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeoJson() {
        return geoJson;
    }

    /**
     * Sets the value of the geoJson property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeoJson(String value) {
        this.geoJson = value;
    }

}
