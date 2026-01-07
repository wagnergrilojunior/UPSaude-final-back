
package com.upsaude.integration.cnes.wsdl.cnesservice;

import java.math.BigInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EquipamentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="EquipamentoType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="descricao" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/tipoequipamento}tipoEquipamento"/>
 *         <element name="destinacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="quantidadeEquipamento" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         <element name="quantidadeUso" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         <element name="TpSUS" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/equipamento}indicadorSUSType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EquipamentoType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/equipamento", propOrder = {
    "codigo",
    "descricao",
    "tipoEquipamento",
    "destinacao",
    "quantidadeEquipamento",
    "quantidadeUso",
    "tpSUS"
})
public class EquipamentoType {

    @XmlElement(required = true)
    protected String codigo;
    @XmlElement(required = true)
    protected String descricao;
    @XmlElement(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/tipoequipamento", required = true)
    protected TipoEquipamentoType tipoEquipamento;
    protected String destinacao;
    protected BigInteger quantidadeEquipamento;
    protected BigInteger quantidadeUso;
    @XmlElement(name = "TpSUS")
    protected IndicadorSUSType tpSUS;

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
     * Tipo de Equipamento do Estabelecimento de Saúde..
     * 
     * @return
     *     possible object is
     *     {@link TipoEquipamentoType }
     *     
     */
    public TipoEquipamentoType getTipoEquipamento() {
        return tipoEquipamento;
    }

    /**
     * Sets the value of the tipoEquipamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoEquipamentoType }
     *     
     */
    public void setTipoEquipamento(TipoEquipamentoType value) {
        this.tipoEquipamento = value;
    }

    /**
     * Gets the value of the destinacao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinacao() {
        return destinacao;
    }

    /**
     * Sets the value of the destinacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinacao(String value) {
        this.destinacao = value;
    }

    /**
     * Gets the value of the quantidadeEquipamento property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getQuantidadeEquipamento() {
        return quantidadeEquipamento;
    }

    /**
     * Sets the value of the quantidadeEquipamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setQuantidadeEquipamento(BigInteger value) {
        this.quantidadeEquipamento = value;
    }

    /**
     * Gets the value of the quantidadeUso property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getQuantidadeUso() {
        return quantidadeUso;
    }

    /**
     * Sets the value of the quantidadeUso property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setQuantidadeUso(BigInteger value) {
        this.quantidadeUso = value;
    }

    /**
     * Gets the value of the tpSUS property.
     * 
     * @return
     *     possible object is
     *     {@link IndicadorSUSType }
     *     
     */
    public IndicadorSUSType getTpSUS() {
        return tpSUS;
    }

    /**
     * Sets the value of the tpSUS property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndicadorSUSType }
     *     
     */
    public void setTpSUS(IndicadorSUSType value) {
        this.tpSUS = value;
    }

}
