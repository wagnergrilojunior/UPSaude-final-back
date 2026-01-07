
package com.upsaude.integration.cnes.wsdl.cnesservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComplementoProfissionalSaudeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ComplementoProfissionalSaudeType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="tipoVinculacao" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/vinculoempregaticio}vinculoempregaticio"/>
 *         <element name="quantidadeProfissionaisSaude" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComplementoProfissionalSaudeType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/complementoprofissionalsaude", propOrder = {
    "tipoVinculacao",
    "vinculoempregaticio",
    "quantidadeProfissionaisSaude"
})
public class ComplementoProfissionalSaudeType {

    @XmlElement(required = true)
    protected String tipoVinculacao;
    @XmlElement(namespace = "http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/vinculoempregaticio", required = true)
    protected VinculoEmpregaticioType vinculoempregaticio;
    protected int quantidadeProfissionaisSaude;

    /**
     * Gets the value of the tipoVinculacao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoVinculacao() {
        return tipoVinculacao;
    }

    /**
     * Sets the value of the tipoVinculacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoVinculacao(String value) {
        this.tipoVinculacao = value;
    }

    /**
     * Vínculo Empregatício.
     * 
     * @return
     *     possible object is
     *     {@link VinculoEmpregaticioType }
     *     
     */
    public VinculoEmpregaticioType getVinculoempregaticio() {
        return vinculoempregaticio;
    }

    /**
     * Sets the value of the vinculoempregaticio property.
     * 
     * @param value
     *     allowed object is
     *     {@link VinculoEmpregaticioType }
     *     
     */
    public void setVinculoempregaticio(VinculoEmpregaticioType value) {
        this.vinculoempregaticio = value;
    }

    /**
     * Gets the value of the quantidadeProfissionaisSaude property.
     * 
     */
    public int getQuantidadeProfissionaisSaude() {
        return quantidadeProfissionaisSaude;
    }

    /**
     * Sets the value of the quantidadeProfissionaisSaude property.
     * 
     */
    public void setQuantidadeProfissionaisSaude(int value) {
        this.quantidadeProfissionaisSaude = value;
    }

}
