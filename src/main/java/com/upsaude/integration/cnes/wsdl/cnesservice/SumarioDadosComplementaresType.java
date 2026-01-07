
package com.upsaude.integration.cnes.wsdl.cnesservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SumarioDadosComplementaresType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SumarioDadosComplementaresType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="quantidadeEstabelecimentosSaude" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="quantidadeEsferasAdministrativas" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "SumarioDadosComplementaresType", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisadadoscomplementarescnes", propOrder = {
    "quantidadeEstabelecimentosSaude",
    "quantidadeEsferasAdministrativas",
    "quantidadeProfissionaisSaude"
})
public class SumarioDadosComplementaresType {

    protected int quantidadeEstabelecimentosSaude;
    protected int quantidadeEsferasAdministrativas;
    protected int quantidadeProfissionaisSaude;

    /**
     * Gets the value of the quantidadeEstabelecimentosSaude property.
     * 
     */
    public int getQuantidadeEstabelecimentosSaude() {
        return quantidadeEstabelecimentosSaude;
    }

    /**
     * Sets the value of the quantidadeEstabelecimentosSaude property.
     * 
     */
    public void setQuantidadeEstabelecimentosSaude(int value) {
        this.quantidadeEstabelecimentosSaude = value;
    }

    /**
     * Gets the value of the quantidadeEsferasAdministrativas property.
     * 
     */
    public int getQuantidadeEsferasAdministrativas() {
        return quantidadeEsferasAdministrativas;
    }

    /**
     * Sets the value of the quantidadeEsferasAdministrativas property.
     * 
     */
    public void setQuantidadeEsferasAdministrativas(int value) {
        this.quantidadeEsferasAdministrativas = value;
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
