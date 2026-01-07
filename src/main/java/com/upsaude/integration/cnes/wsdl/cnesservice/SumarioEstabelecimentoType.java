
package com.upsaude.integration.cnes.wsdl.cnesservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SumarioEstabelecimentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="SumarioEstabelecimentoType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="quantidadeProfissionaisSaude" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="quantidadeCBOS" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="quantidadeLeitos" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="quantidadeHabilitacoes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="quantidadeEquipamentos" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="quantidadeSamus" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SumarioEstabelecimentoType", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisaestabelecimentosaude", propOrder = {
    "quantidadeProfissionaisSaude",
    "quantidadeCBOS",
    "quantidadeLeitos",
    "quantidadeHabilitacoes",
    "quantidadeEquipamentos",
    "quantidadeSamus"
})
public class SumarioEstabelecimentoType {

    protected int quantidadeProfissionaisSaude;
    protected int quantidadeCBOS;
    protected int quantidadeLeitos;
    protected int quantidadeHabilitacoes;
    protected int quantidadeEquipamentos;
    protected int quantidadeSamus;

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

    /**
     * Gets the value of the quantidadeCBOS property.
     * 
     */
    public int getQuantidadeCBOS() {
        return quantidadeCBOS;
    }

    /**
     * Sets the value of the quantidadeCBOS property.
     * 
     */
    public void setQuantidadeCBOS(int value) {
        this.quantidadeCBOS = value;
    }

    /**
     * Gets the value of the quantidadeLeitos property.
     * 
     */
    public int getQuantidadeLeitos() {
        return quantidadeLeitos;
    }

    /**
     * Sets the value of the quantidadeLeitos property.
     * 
     */
    public void setQuantidadeLeitos(int value) {
        this.quantidadeLeitos = value;
    }

    /**
     * Gets the value of the quantidadeHabilitacoes property.
     * 
     */
    public int getQuantidadeHabilitacoes() {
        return quantidadeHabilitacoes;
    }

    /**
     * Sets the value of the quantidadeHabilitacoes property.
     * 
     */
    public void setQuantidadeHabilitacoes(int value) {
        this.quantidadeHabilitacoes = value;
    }

    /**
     * Gets the value of the quantidadeEquipamentos property.
     * 
     */
    public int getQuantidadeEquipamentos() {
        return quantidadeEquipamentos;
    }

    /**
     * Sets the value of the quantidadeEquipamentos property.
     * 
     */
    public void setQuantidadeEquipamentos(int value) {
        this.quantidadeEquipamentos = value;
    }

    /**
     * Gets the value of the quantidadeSamus property.
     * 
     */
    public int getQuantidadeSamus() {
        return quantidadeSamus;
    }

    /**
     * Sets the value of the quantidadeSamus property.
     * 
     */
    public void setQuantidadeSamus(int value) {
        this.quantidadeSamus = value;
    }

}
