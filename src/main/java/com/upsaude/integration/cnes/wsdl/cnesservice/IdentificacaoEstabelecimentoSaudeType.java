
package com.upsaude.integration.cnes.wsdl.cnesservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdentificacaoEstabelecimentoSaudeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="IdentificacaoEstabelecimentoSaudeType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/dadosprincipaisestabelecimentosaude}DadosPrincipais" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/dadoscomplementaresestabelecimentosaude}DadosComplementares" minOccurs="0"/>
 *         <element name="endereco" type="{http://servicos.saude.gov.br/schema/corporativo/endereco/v1r2/endereco}EnderecoType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificacaoEstabelecimentoSaudeType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/identificacaoestabelecimentosaude", propOrder = {
    "dadosPrincipais",
    "dadosComplementares",
    "endereco"
})
public class IdentificacaoEstabelecimentoSaudeType {

    @XmlElement(name = "DadosPrincipais", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosprincipaisestabelecimentosaude")
    protected DadosPrincipaisType dadosPrincipais;
    @XmlElement(name = "DadosComplementares", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadoscomplementaresestabelecimentosaude")
    protected DadosComplementaresType dadosComplementares;
    @XmlElement(required = true)
    protected EnderecoType endereco;

    /**
     * Dados Principais do Estabelecimento de Saúde.
     * 
     * @return
     *     possible object is
     *     {@link DadosPrincipaisType }
     *     
     */
    public DadosPrincipaisType getDadosPrincipais() {
        return dadosPrincipais;
    }

    /**
     * Sets the value of the dadosPrincipais property.
     * 
     * @param value
     *     allowed object is
     *     {@link DadosPrincipaisType }
     *     
     */
    public void setDadosPrincipais(DadosPrincipaisType value) {
        this.dadosPrincipais = value;
    }

    /**
     * Dados Complementares do Estabelecimento de Saúde.
     * 
     * @return
     *     possible object is
     *     {@link DadosComplementaresType }
     *     
     */
    public DadosComplementaresType getDadosComplementares() {
        return dadosComplementares;
    }

    /**
     * Sets the value of the dadosComplementares property.
     * 
     * @param value
     *     allowed object is
     *     {@link DadosComplementaresType }
     *     
     */
    public void setDadosComplementares(DadosComplementaresType value) {
        this.dadosComplementares = value;
    }

    /**
     * Gets the value of the endereco property.
     * 
     * @return
     *     possible object is
     *     {@link EnderecoType }
     *     
     */
    public EnderecoType getEndereco() {
        return endereco;
    }

    /**
     * Sets the value of the endereco property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnderecoType }
     *     
     */
    public void setEndereco(EnderecoType value) {
        this.endereco = value;
    }

}
