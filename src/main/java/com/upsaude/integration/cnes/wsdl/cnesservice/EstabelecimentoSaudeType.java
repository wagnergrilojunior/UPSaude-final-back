
package com.upsaude.integration.cnes.wsdl.cnesservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EstabelecimentoSaudeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="EstabelecimentoSaudeType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="codigoCNES">
 *           <simpleType>
 *             <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               <maxLength value="15"/>
 *             </restriction>
 *           </simpleType>
 *         </element>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/identificacaoestabelecimentosaude}IdentificacaoEstabelecimentoSaude" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstabelecimentoSaudeType", namespace = "http://servicos.saude.gov.br/schema/cnes/v2r0/estabelecimentosaude", propOrder = {
    "codigoCNES",
    "identificacaoEstabelecimentoSaude"
})
public class EstabelecimentoSaudeType {

    @XmlElement(required = true)
    protected String codigoCNES;
    @XmlElement(name = "IdentificacaoEstabelecimentoSaude", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/identificacaoestabelecimentosaude")
    protected IdentificacaoEstabelecimentoSaudeType identificacaoEstabelecimentoSaude;

    /**
     * Gets the value of the codigoCNES property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoCNES() {
        return codigoCNES;
    }

    /**
     * Sets the value of the codigoCNES property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoCNES(String value) {
        this.codigoCNES = value;
    }

    /**
     * Identificação do Estabelecimento de Saúde..
     * 
     * @return
     *     possible object is
     *     {@link IdentificacaoEstabelecimentoSaudeType }
     *     
     */
    public IdentificacaoEstabelecimentoSaudeType getIdentificacaoEstabelecimentoSaude() {
        return identificacaoEstabelecimentoSaude;
    }

    /**
     * Sets the value of the identificacaoEstabelecimentoSaude property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificacaoEstabelecimentoSaudeType }
     *     
     */
    public void setIdentificacaoEstabelecimentoSaude(IdentificacaoEstabelecimentoSaudeType value) {
        this.identificacaoEstabelecimentoSaude = value;
    }

}
