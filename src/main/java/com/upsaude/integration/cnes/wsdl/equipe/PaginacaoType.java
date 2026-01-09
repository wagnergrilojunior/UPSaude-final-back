
package com.upsaude.integration.cnes.wsdl.equipe;

import java.math.BigInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaginacaoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="PaginacaoType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="registroInicial">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               <minInclusive value="1"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="quantidadeRegistros">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               <minInclusive value="1"/>
 *               <maxInclusive value="100"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="totalRegistros" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               <minInclusive value="0"/>
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
@XmlType(name = "PaginacaoType", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1/paginacao", propOrder = {
    "registroInicial",
    "quantidadeRegistros",
    "totalRegistros"
})
public class PaginacaoType {

    @XmlElement(required = true)
    protected BigInteger registroInicial;
    protected int quantidadeRegistros;
    protected BigInteger totalRegistros;

    /**
     * Gets the value of the registroInicial property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRegistroInicial() {
        return registroInicial;
    }

    /**
     * Sets the value of the registroInicial property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRegistroInicial(BigInteger value) {
        this.registroInicial = value;
    }

    /**
     * Gets the value of the quantidadeRegistros property.
     * 
     */
    public int getQuantidadeRegistros() {
        return quantidadeRegistros;
    }

    /**
     * Sets the value of the quantidadeRegistros property.
     * 
     */
    public void setQuantidadeRegistros(int value) {
        this.quantidadeRegistros = value;
    }

    /**
     * Gets the value of the totalRegistros property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalRegistros() {
        return totalRegistros;
    }

    /**
     * Sets the value of the totalRegistros property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalRegistros(BigInteger value) {
        this.totalRegistros = value;
    }

}
