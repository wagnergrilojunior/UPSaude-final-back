
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

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
 *         <element name="posicaoRegistroInicio" type="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/paginacao}numero"/>
 *         <element name="quantidadeRegistrosPorPagina" type="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/paginacao}numero"/>
 *         <element name="quantidadeRegistros" type="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/paginacao}numero" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaginacaoType", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/paginacao", propOrder = {
    "posicaoRegistroInicio",
    "quantidadeRegistrosPorPagina",
    "quantidadeRegistros"
})
public class PaginacaoType {

    @XmlElement(required = true)
    protected String posicaoRegistroInicio;
    @XmlElement(required = true)
    protected String quantidadeRegistrosPorPagina;
    protected String quantidadeRegistros;

    /**
     * Gets the value of the posicaoRegistroInicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosicaoRegistroInicio() {
        return posicaoRegistroInicio;
    }

    /**
     * Sets the value of the posicaoRegistroInicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosicaoRegistroInicio(String value) {
        this.posicaoRegistroInicio = value;
    }

    /**
     * Gets the value of the quantidadeRegistrosPorPagina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantidadeRegistrosPorPagina() {
        return quantidadeRegistrosPorPagina;
    }

    /**
     * Sets the value of the quantidadeRegistrosPorPagina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantidadeRegistrosPorPagina(String value) {
        this.quantidadeRegistrosPorPagina = value;
    }

    /**
     * Gets the value of the quantidadeRegistros property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantidadeRegistros() {
        return quantidadeRegistros;
    }

    /**
     * Sets the value of the quantidadeRegistros property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantidadeRegistros(String value) {
        this.quantidadeRegistros = value;
    }

}
