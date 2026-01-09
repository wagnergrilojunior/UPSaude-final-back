
package com.upsaude.integration.cnes.wsdl.cnesservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ComplementoEsferaAdministrativaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ComplementoEsferaAdministrativaType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/esferaadministrativa}esferaAdministrativa"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/tipogestao}tipoGestao"/>
 *         <element name="quantidadeEstabelecimentos" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComplementoEsferaAdministrativaType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/complementoesferaadministrativa", propOrder = {
    "esferaAdministrativa",
    "tipoGestao",
    "quantidadeEstabelecimentos"
})
public class ComplementoEsferaAdministrativaType {

    @XmlElement(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/esferaadministrativa", required = true)
    protected EsferaAdministrativaType esferaAdministrativa;
    @XmlElement(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/tipogestao", required = true)
    protected TipoGestaoType tipoGestao;
    protected int quantidadeEstabelecimentos;

    /**
     * Gets the value of the esferaAdministrativa property.
     * 
     * @return
     *     possible object is
     *     {@link EsferaAdministrativaType }
     *     
     */
    public EsferaAdministrativaType getEsferaAdministrativa() {
        return esferaAdministrativa;
    }

    /**
     * Sets the value of the esferaAdministrativa property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsferaAdministrativaType }
     *     
     */
    public void setEsferaAdministrativa(EsferaAdministrativaType value) {
        this.esferaAdministrativa = value;
    }

    /**
     * Gets the value of the tipoGestao property.
     * 
     * @return
     *     possible object is
     *     {@link TipoGestaoType }
     *     
     */
    public TipoGestaoType getTipoGestao() {
        return tipoGestao;
    }

    /**
     * Sets the value of the tipoGestao property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoGestaoType }
     *     
     */
    public void setTipoGestao(TipoGestaoType value) {
        this.tipoGestao = value;
    }

    /**
     * Gets the value of the quantidadeEstabelecimentos property.
     * 
     */
    public int getQuantidadeEstabelecimentos() {
        return quantidadeEstabelecimentos;
    }

    /**
     * Sets the value of the quantidadeEstabelecimentos property.
     * 
     */
    public void setQuantidadeEstabelecimentos(int value) {
        this.quantidadeEstabelecimentos = value;
    }

}
