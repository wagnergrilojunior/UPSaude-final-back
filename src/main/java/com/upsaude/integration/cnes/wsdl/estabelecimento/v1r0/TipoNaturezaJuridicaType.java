
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoNaturezaJuridicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="TipoNaturezaJuridicaType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigo">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <enumeration value="1"/>
 *               <enumeration value="2"/>
 *               <enumeration value="3"/>
 *               <enumeration value="4"/>
 *               <enumeration value="5"/>
 *               <enumeration value="8"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="descricao" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoNaturezaJuridicaType", namespace = "http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/tiponaturezajuridica", propOrder = {
    "codigo",
    "descricao"
})
public class TipoNaturezaJuridicaType {

    @XmlElement(required = true)
    protected String codigo;
    @XmlElement(required = true)
    protected String descricao;

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

}
