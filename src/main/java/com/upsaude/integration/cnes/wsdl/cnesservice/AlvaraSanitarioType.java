
package com.upsaude.integration.cnes.wsdl.cnesservice;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AlvaraSanitarioType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="AlvaraSanitarioType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="numeroAlvara" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="0"/>
 *               <maxLength value="25"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="dataVigenciaInicial" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         <element name="dataVigenciaFinal" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlvaraSanitarioType", namespace = "http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/alvarasanitario", propOrder = {
    "numeroAlvara",
    "dataVigenciaInicial",
    "dataVigenciaFinal"
})
public class AlvaraSanitarioType {

    protected String numeroAlvara;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataVigenciaInicial;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataVigenciaFinal;

    /**
     * Gets the value of the numeroAlvara property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroAlvara() {
        return numeroAlvara;
    }

    /**
     * Sets the value of the numeroAlvara property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroAlvara(String value) {
        this.numeroAlvara = value;
    }

    /**
     * Gets the value of the dataVigenciaInicial property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVigenciaInicial() {
        return dataVigenciaInicial;
    }

    /**
     * Sets the value of the dataVigenciaInicial property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVigenciaInicial(XMLGregorianCalendar value) {
        this.dataVigenciaInicial = value;
    }

    /**
     * Gets the value of the dataVigenciaFinal property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVigenciaFinal() {
        return dataVigenciaFinal;
    }

    /**
     * Sets the value of the dataVigenciaFinal property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVigenciaFinal(XMLGregorianCalendar value) {
        this.dataVigenciaFinal = value;
    }

}
