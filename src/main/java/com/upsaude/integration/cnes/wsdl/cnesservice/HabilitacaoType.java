
package com.upsaude.integration.cnes.wsdl.cnesservice;

import java.math.BigInteger;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HabilitacaoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="HabilitacaoType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/grupohabilitacao}grupoHabilitacao"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/subgrupohabilitacao}subGrupoHabilitacao"/>
 *         <element name="dataAtualizacao" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         <element name="cmptInicio" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/cmpt}cmptType" minOccurs="0"/>
 *         <element name="cmptFim" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/cmpt}cmptType" minOccurs="0"/>
 *         <element name="nomePortaria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="dataPortaria" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         <element name="quantidadeLeito" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HabilitacaoType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/habilitacao", propOrder = {
    "grupoHabilitacao",
    "subGrupoHabilitacao",
    "dataAtualizacao",
    "cmptInicio",
    "cmptFim",
    "nomePortaria",
    "dataPortaria",
    "quantidadeLeito"
})
public class HabilitacaoType {

    @XmlElement(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/grupohabilitacao", required = true)
    protected GrupoHabilitacaoType grupoHabilitacao;
    @XmlElement(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/subgrupohabilitacao", required = true)
    protected SubGrupoHabilitacaoType subGrupoHabilitacao;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAtualizacao;
    protected CmptType cmptInicio;
    protected CmptType cmptFim;
    protected String nomePortaria;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPortaria;
    protected BigInteger quantidadeLeito;

    /**
     * Grupo de Habilitação do Estabelecimento de Saúde..
     * 
     * @return
     *     possible object is
     *     {@link GrupoHabilitacaoType }
     *     
     */
    public GrupoHabilitacaoType getGrupoHabilitacao() {
        return grupoHabilitacao;
    }

    /**
     * Sets the value of the grupoHabilitacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link GrupoHabilitacaoType }
     *     
     */
    public void setGrupoHabilitacao(GrupoHabilitacaoType value) {
        this.grupoHabilitacao = value;
    }

    /**
     * Sub-Grupo de Habilitação do Estabelecimento de Saúde..
     * 
     * @return
     *     possible object is
     *     {@link SubGrupoHabilitacaoType }
     *     
     */
    public SubGrupoHabilitacaoType getSubGrupoHabilitacao() {
        return subGrupoHabilitacao;
    }

    /**
     * Sets the value of the subGrupoHabilitacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubGrupoHabilitacaoType }
     *     
     */
    public void setSubGrupoHabilitacao(SubGrupoHabilitacaoType value) {
        this.subGrupoHabilitacao = value;
    }

    /**
     * Gets the value of the dataAtualizacao property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAtualizacao() {
        return dataAtualizacao;
    }

    /**
     * Sets the value of the dataAtualizacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAtualizacao(XMLGregorianCalendar value) {
        this.dataAtualizacao = value;
    }

    /**
     * Gets the value of the cmptInicio property.
     * 
     * @return
     *     possible object is
     *     {@link CmptType }
     *     
     */
    public CmptType getCmptInicio() {
        return cmptInicio;
    }

    /**
     * Sets the value of the cmptInicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link CmptType }
     *     
     */
    public void setCmptInicio(CmptType value) {
        this.cmptInicio = value;
    }

    /**
     * Gets the value of the cmptFim property.
     * 
     * @return
     *     possible object is
     *     {@link CmptType }
     *     
     */
    public CmptType getCmptFim() {
        return cmptFim;
    }

    /**
     * Sets the value of the cmptFim property.
     * 
     * @param value
     *     allowed object is
     *     {@link CmptType }
     *     
     */
    public void setCmptFim(CmptType value) {
        this.cmptFim = value;
    }

    /**
     * Gets the value of the nomePortaria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomePortaria() {
        return nomePortaria;
    }

    /**
     * Sets the value of the nomePortaria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomePortaria(String value) {
        this.nomePortaria = value;
    }

    /**
     * Gets the value of the dataPortaria property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPortaria() {
        return dataPortaria;
    }

    /**
     * Sets the value of the dataPortaria property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPortaria(XMLGregorianCalendar value) {
        this.dataPortaria = value;
    }

    /**
     * Gets the value of the quantidadeLeito property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getQuantidadeLeito() {
        return quantidadeLeito;
    }

    /**
     * Sets the value of the quantidadeLeito property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setQuantidadeLeito(BigInteger value) {
        this.quantidadeLeito = value;
    }

}
