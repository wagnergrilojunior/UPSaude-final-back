
package com.upsaude.integration.cnes.wsdl.cnesservice;

import java.math.BigInteger;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LeitoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="LeitoType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="descricao" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="dataAtualizacao" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="quantidadeLeito" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         <element name="quantidadeLeitoSUS" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LeitoType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/leito", propOrder = {
    "codigo",
    "descricao",
    "dataAtualizacao",
    "quantidadeLeito",
    "quantidadeLeitoSUS"
})
public class LeitoType {

    @XmlElement(required = true)
    protected String codigo;
    @XmlElement(required = true)
    protected String descricao;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAtualizacao;
    protected BigInteger quantidadeLeito;
    protected BigInteger quantidadeLeitoSUS;

    /**
     * Gets the value of the codigo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Sets the value of the codigo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Gets the value of the descricao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Sets the value of the descricao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescricao(String value) {
        this.descricao = value;
    }

    /**
     * Gets the value of the dataAtualizacao property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAtualizacao() {
        return dataAtualizacao;
    }

    /**
     * Sets the value of the dataAtualizacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAtualizacao(XMLGregorianCalendar value) {
        this.dataAtualizacao = value;
    }

    /**
     * Gets the value of the quantidadeLeito property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getQuantidadeLeito() {
        return quantidadeLeito;
    }

    /**
     * Sets the value of the quantidadeLeito property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setQuantidadeLeito(BigInteger value) {
        this.quantidadeLeito = value;
    }

    /**
     * Gets the value of the quantidadeLeitoSUS property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getQuantidadeLeitoSUS() {
        return quantidadeLeitoSUS;
    }

    /**
     * Sets the value of the quantidadeLeitoSUS property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setQuantidadeLeitoSUS(BigInteger value) {
        this.quantidadeLeitoSUS = value;
    }

}
