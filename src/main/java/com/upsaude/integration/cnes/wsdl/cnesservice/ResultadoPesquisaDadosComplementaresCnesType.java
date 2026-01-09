
package com.upsaude.integration.cnes.wsdl.cnesservice;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResultadoPesquisaDadosComplementaresCnesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ResultadoPesquisaDadosComplementaresCnesType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/complementoestabelecimentosaude}ComplementoEstabelecimentoSaude" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/complementoesferaadministrativa}ComplementoEsferaAdministrativa" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/complementoprofissionalsaude}ComplementoProfissionalSaude" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/cmpt}cmpt"/>
 *         <element name="sumario" type="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisadadoscomplementarescnes}SumarioDadosComplementaresType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultadoPesquisaDadosComplementaresCnesType", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisadadoscomplementarescnes", propOrder = {
    "complementoEstabelecimentoSaude",
    "complementoEsferaAdministrativa",
    "complementoProfissionalSaude",
    "cmpt",
    "sumario"
})
public class ResultadoPesquisaDadosComplementaresCnesType {

    @XmlElement(name = "ComplementoEstabelecimentoSaude", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/complementoestabelecimentosaude")
    protected List<ComplementoEstabelecimentoSaudeType> complementoEstabelecimentoSaude;
    @XmlElement(name = "ComplementoEsferaAdministrativa", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/complementoesferaadministrativa")
    protected List<ComplementoEsferaAdministrativaType> complementoEsferaAdministrativa;
    @XmlElement(name = "ComplementoProfissionalSaude", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/complementoprofissionalsaude")
    protected List<ComplementoProfissionalSaudeType> complementoProfissionalSaude;
    @XmlElement(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/cmpt", required = true)
    protected CmptType cmpt;
    @XmlElement(required = true)
    protected SumarioDadosComplementaresType sumario;

    /**
     * Dados Complementares do Estabelecimento de Saúde.Gets the value of the complementoEstabelecimentoSaude property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the complementoEstabelecimentoSaude property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComplementoEstabelecimentoSaude().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplementoEstabelecimentoSaudeType }
     * 
     * 
     * @return
     *     The value of the complementoEstabelecimentoSaude property.
     */
    public List<ComplementoEstabelecimentoSaudeType> getComplementoEstabelecimentoSaude() {
        if (complementoEstabelecimentoSaude == null) {
            complementoEstabelecimentoSaude = new ArrayList<>();
        }
        return this.complementoEstabelecimentoSaude;
    }

    /**
     * Dados Complementares da Esfera Administrativa.Gets the value of the complementoEsferaAdministrativa property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the complementoEsferaAdministrativa property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComplementoEsferaAdministrativa().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplementoEsferaAdministrativaType }
     * 
     * 
     * @return
     *     The value of the complementoEsferaAdministrativa property.
     */
    public List<ComplementoEsferaAdministrativaType> getComplementoEsferaAdministrativa() {
        if (complementoEsferaAdministrativa == null) {
            complementoEsferaAdministrativa = new ArrayList<>();
        }
        return this.complementoEsferaAdministrativa;
    }

    /**
     * Dados Complementares dos Profissionais de Saúde.Gets the value of the complementoProfissionalSaude property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the complementoProfissionalSaude property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComplementoProfissionalSaude().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComplementoProfissionalSaudeType }
     * 
     * 
     * @return
     *     The value of the complementoProfissionalSaude property.
     */
    public List<ComplementoProfissionalSaudeType> getComplementoProfissionalSaude() {
        if (complementoProfissionalSaude == null) {
            complementoProfissionalSaude = new ArrayList<>();
        }
        return this.complementoProfissionalSaude;
    }

    /**
     * Ano do Exercício.
     * 
     * @return
     *     possible object is
     *     {@link CmptType }
     *     
     */
    public CmptType getCmpt() {
        return cmpt;
    }

    /**
     * Sets the value of the cmpt property.
     * 
     * @param value
     *     allowed object is
     *     {@link CmptType }
     *     
     */
    public void setCmpt(CmptType value) {
        this.cmpt = value;
    }

    /**
     * Gets the value of the sumario property.
     * 
     * @return
     *     possible object is
     *     {@link SumarioDadosComplementaresType }
     *     
     */
    public SumarioDadosComplementaresType getSumario() {
        return sumario;
    }

    /**
     * Sets the value of the sumario property.
     * 
     * @param value
     *     allowed object is
     *     {@link SumarioDadosComplementaresType }
     *     
     */
    public void setSumario(SumarioDadosComplementaresType value) {
        this.sumario = value;
    }

}
