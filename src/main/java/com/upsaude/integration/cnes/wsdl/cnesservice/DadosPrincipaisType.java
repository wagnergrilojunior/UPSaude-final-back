
package com.upsaude.integration.cnes.wsdl.cnesservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DadosPrincipaisType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DadosPrincipaisType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/tipoestabelecimentosaude}TipoEstabelecimentoSaude"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/atividadeensino}AtividadeEnsino"/>
 *         <element name="sitioInternet" minOccurs="0">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="150"/>
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
@XmlType(name = "DadosPrincipaisType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosprincipaisestabelecimentosaude", propOrder = {
    "tipoEstabelecimentoSaude",
    "atividadeEnsino",
    "sitioInternet"
})
public class DadosPrincipaisType {

    @XmlElement(name = "TipoEstabelecimentoSaude", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/tipoestabelecimentosaude", required = true)
    protected TipoEstabelecimentoSaudeType tipoEstabelecimentoSaude;
    @XmlElement(name = "AtividadeEnsino", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/atividadeensino", required = true)
    protected AtividadeEnsinoType atividadeEnsino;
    protected String sitioInternet;

    /**
     * Tipo do Estabelecimento de Saúde..
     * 
     * @return
     *     possible object is
     *     {@link TipoEstabelecimentoSaudeType }
     *     
     */
    public TipoEstabelecimentoSaudeType getTipoEstabelecimentoSaude() {
        return tipoEstabelecimentoSaude;
    }

    /**
     * Sets the value of the tipoEstabelecimentoSaude property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoEstabelecimentoSaudeType }
     *     
     */
    public void setTipoEstabelecimentoSaude(TipoEstabelecimentoSaudeType value) {
        this.tipoEstabelecimentoSaude = value;
    }

    /**
     * Atividade de Ensino..
     * 
     * @return
     *     possible object is
     *     {@link AtividadeEnsinoType }
     *     
     */
    public AtividadeEnsinoType getAtividadeEnsino() {
        return atividadeEnsino;
    }

    /**
     * Sets the value of the atividadeEnsino property.
     * 
     * @param value
     *     allowed object is
     *     {@link AtividadeEnsinoType }
     *     
     */
    public void setAtividadeEnsino(AtividadeEnsinoType value) {
        this.atividadeEnsino = value;
    }

    /**
     * Gets the value of the sitioInternet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitioInternet() {
        return sitioInternet;
    }

    /**
     * Sets the value of the sitioInternet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitioInternet(String value) {
        this.sitioInternet = value;
    }

}
