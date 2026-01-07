
package com.upsaude.integration.cnes.wsdl.cnesservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VinculoEmpregaticioType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="VinculoEmpregaticioType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigoVinculacao" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="descricaoVinculacao" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/tipovinculoempregaticio}tipoVinculoEmpregaticio"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VinculoEmpregaticioType", namespace = "http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/vinculoempregaticio", propOrder = {
    "codigoVinculacao",
    "descricaoVinculacao",
    "tipoVinculoEmpregaticio"
})
public class VinculoEmpregaticioType {

    @XmlElement(required = true)
    protected String codigoVinculacao;
    @XmlElement(required = true)
    protected String descricaoVinculacao;
    @XmlElement(namespace = "http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/tipovinculoempregaticio", required = true)
    protected TipoVinculoEmpregaticioType tipoVinculoEmpregaticio;

    /**
     * Gets the value of the codigoVinculacao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoVinculacao() {
        return codigoVinculacao;
    }

    /**
     * Sets the value of the codigoVinculacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoVinculacao(String value) {
        this.codigoVinculacao = value;
    }

    /**
     * Gets the value of the descricaoVinculacao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescricaoVinculacao() {
        return descricaoVinculacao;
    }

    /**
     * Sets the value of the descricaoVinculacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescricaoVinculacao(String value) {
        this.descricaoVinculacao = value;
    }

    /**
     * Tipo do Vínculo Empregatício.
     * 
     * @return
     *     possible object is
     *     {@link TipoVinculoEmpregaticioType }
     *     
     */
    public TipoVinculoEmpregaticioType getTipoVinculoEmpregaticio() {
        return tipoVinculoEmpregaticio;
    }

    /**
     * Sets the value of the tipoVinculoEmpregaticio property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoVinculoEmpregaticioType }
     *     
     */
    public void setTipoVinculoEmpregaticio(TipoVinculoEmpregaticioType value) {
        this.tipoVinculoEmpregaticio = value;
    }

}
