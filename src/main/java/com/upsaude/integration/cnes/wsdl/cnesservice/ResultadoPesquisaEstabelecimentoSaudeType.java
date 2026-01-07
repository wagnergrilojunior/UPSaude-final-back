
package com.upsaude.integration.cnes.wsdl.cnesservice;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResultadoPesquisaEstabelecimentoSaudeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ResultadoPesquisaEstabelecimentoSaudeType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="EstabelecimentoSaude" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/dadosgeraiscnes}DadosGeraisEstabelecimentoSaudeType" minOccurs="0"/>
 *         <element name="profissional" type="{http://servicos.saude.gov.br/schema/profissionalsaude/v1r0/profissionalsaude}ProfissionalSaudeType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="leito" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/leito}LeitoType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="habilitacao" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/habilitacao}HabilitacaoType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="equipamento" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/equipamento}EquipamentoType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="samu" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/samu}SamuType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="sumario" type="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisaestabelecimentosaude}SumarioEstabelecimentoType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultadoPesquisaEstabelecimentoSaudeType", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisaestabelecimentosaude", propOrder = {
    "estabelecimentoSaude",
    "profissional",
    "leito",
    "habilitacao",
    "equipamento",
    "samu",
    "sumario"
})
public class ResultadoPesquisaEstabelecimentoSaudeType {

    @XmlElement(name = "EstabelecimentoSaude")
    protected DadosGeraisEstabelecimentoSaudeType estabelecimentoSaude;
    protected List<ProfissionalSaudeType> profissional;
    protected List<LeitoType> leito;
    protected List<HabilitacaoType> habilitacao;
    protected List<EquipamentoType> equipamento;
    protected List<SamuType> samu;
    @XmlElement(required = true)
    protected SumarioEstabelecimentoType sumario;

    /**
     * Gets the value of the estabelecimentoSaude property.
     * 
     * @return
     *     possible object is
     *     {@link DadosGeraisEstabelecimentoSaudeType }
     *     
     */
    public DadosGeraisEstabelecimentoSaudeType getEstabelecimentoSaude() {
        return estabelecimentoSaude;
    }

    /**
     * Sets the value of the estabelecimentoSaude property.
     * 
     * @param value
     *     allowed object is
     *     {@link DadosGeraisEstabelecimentoSaudeType }
     *     
     */
    public void setEstabelecimentoSaude(DadosGeraisEstabelecimentoSaudeType value) {
        this.estabelecimentoSaude = value;
    }

    /**
     * Gets the value of the profissional property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the profissional property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProfissional().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProfissionalSaudeType }
     * 
     * 
     * @return
     *     The value of the profissional property.
     */
    public List<ProfissionalSaudeType> getProfissional() {
        if (profissional == null) {
            profissional = new ArrayList<>();
        }
        return this.profissional;
    }

    /**
     * Gets the value of the leito property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the leito property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLeito().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LeitoType }
     * 
     * 
     * @return
     *     The value of the leito property.
     */
    public List<LeitoType> getLeito() {
        if (leito == null) {
            leito = new ArrayList<>();
        }
        return this.leito;
    }

    /**
     * Gets the value of the habilitacao property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the habilitacao property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHabilitacao().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HabilitacaoType }
     * 
     * 
     * @return
     *     The value of the habilitacao property.
     */
    public List<HabilitacaoType> getHabilitacao() {
        if (habilitacao == null) {
            habilitacao = new ArrayList<>();
        }
        return this.habilitacao;
    }

    /**
     * Gets the value of the equipamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the equipamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEquipamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EquipamentoType }
     * 
     * 
     * @return
     *     The value of the equipamento property.
     */
    public List<EquipamentoType> getEquipamento() {
        if (equipamento == null) {
            equipamento = new ArrayList<>();
        }
        return this.equipamento;
    }

    /**
     * Gets the value of the samu property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the samu property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSamu().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SamuType }
     * 
     * 
     * @return
     *     The value of the samu property.
     */
    public List<SamuType> getSamu() {
        if (samu == null) {
            samu = new ArrayList<>();
        }
        return this.samu;
    }

    /**
     * Gets the value of the sumario property.
     * 
     * @return
     *     possible object is
     *     {@link SumarioEstabelecimentoType }
     *     
     */
    public SumarioEstabelecimentoType getSumario() {
        return sumario;
    }

    /**
     * Sets the value of the sumario property.
     * 
     * @param value
     *     allowed object is
     *     {@link SumarioEstabelecimentoType }
     *     
     */
    public void setSumario(SumarioEstabelecimentoType value) {
        this.sumario = value;
    }

}
