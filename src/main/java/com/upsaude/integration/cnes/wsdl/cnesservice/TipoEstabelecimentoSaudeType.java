
package com.upsaude.integration.cnes.wsdl.cnesservice;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoEstabelecimentoSaudeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="TipoEstabelecimentoSaudeType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigo">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="4"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="descricao">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="70"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/subtipoestabelecimentosaude}SubtipoEstabelecimentoSaude" maxOccurs="unbounded"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoEstabelecimentoSaudeType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/tipoestabelecimentosaude", propOrder = {
    "codigo",
    "descricao",
    "subtipoEstabelecimentoSaude"
})
public class TipoEstabelecimentoSaudeType {

    @XmlElement(required = true)
    protected String codigo;
    @XmlElement(required = true)
    protected String descricao;
    @XmlElement(name = "SubtipoEstabelecimentoSaude", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/subtipoestabelecimentosaude", required = true)
    protected List<SubtipoEstabelecimentoSaudeType> subtipoEstabelecimentoSaude;

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
     * Subtipo do Estabelecimento de Saúde..Gets the value of the subtipoEstabelecimentoSaude property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the subtipoEstabelecimentoSaude property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubtipoEstabelecimentoSaude().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubtipoEstabelecimentoSaudeType }
     * 
     * 
     * @return
     *     The value of the subtipoEstabelecimentoSaude property.
     */
    public List<SubtipoEstabelecimentoSaudeType> getSubtipoEstabelecimentoSaude() {
        if (subtipoEstabelecimentoSaude == null) {
            subtipoEstabelecimentoSaude = new ArrayList<>();
        }
        return this.subtipoEstabelecimentoSaude;
    }

}
