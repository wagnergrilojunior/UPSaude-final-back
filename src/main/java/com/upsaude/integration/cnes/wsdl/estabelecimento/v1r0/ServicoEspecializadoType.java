
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServicoEspecializadoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ServicoEspecializadoType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigo">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <minLength value="3"/>
 *               <maxLength value="3"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="descricao" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element name="classificacoes" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/servicoclassificacoes}ServicoClassificacoesType" maxOccurs="unbounded"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServicoEspecializadoType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/servicoespecializado", propOrder = {
    "codigo",
    "descricao",
    "classificacoes"
})
public class ServicoEspecializadoType {

    @XmlElement(required = true)
    protected String codigo;
    protected String descricao;
    @XmlElement(required = true)
    protected List<ServicoClassificacoesType> classificacoes;

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
     * Gets the value of the classificacoes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the classificacoes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClassificacoes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServicoClassificacoesType }
     * 
     * 
     * @return
     *     The value of the classificacoes property.
     */
    public List<ServicoClassificacoesType> getClassificacoes() {
        if (classificacoes == null) {
            classificacoes = new ArrayList<>();
        }
        return this.classificacoes;
    }

}
