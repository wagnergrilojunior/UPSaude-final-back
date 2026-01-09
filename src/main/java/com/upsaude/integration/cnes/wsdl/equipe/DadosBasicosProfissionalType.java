
package com.upsaude.integration.cnes.wsdl.equipe;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DadosBasicosProfissionalType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DadosBasicosProfissionalType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Nome" type="{http://servicos.saude.gov.br/schema/corporativo/pessoafisica/v1r2/nomecompleto}NomeCompletoType"/>
 *         <element name="CPF" type="{http://servicos.saude.gov.br/schema/corporativo/documento/v1r2/cpf}CPFType"/>
 *         <element name="CNS" type="{http://servicos.saude.gov.br/schema/cadsus/v5r0/cns}CNSType" maxOccurs="unbounded"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DadosBasicosProfissionalType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosprofissional", propOrder = {
    "nome",
    "cpf",
    "cns"
})
public class DadosBasicosProfissionalType {

    @XmlElement(name = "Nome", required = true)
    protected NomeCompletoType nome;
    @XmlElement(name = "CPF", required = true)
    protected CPFType cpf;
    @XmlElement(name = "CNS", required = true)
    protected List<CNSType> cns;

    /**
     * Gets the value of the nome property.
     * 
     * @return
     *     possible object is
     *     {@link NomeCompletoType }
     *     
     */
    public NomeCompletoType getNome() {
        return nome;
    }

    /**
     * Sets the value of the nome property.
     * 
     * @param value
     *     allowed object is
     *     {@link NomeCompletoType }
     *     
     */
    public void setNome(NomeCompletoType value) {
        this.nome = value;
    }

    /**
     * Gets the value of the cpf property.
     * 
     * @return
     *     possible object is
     *     {@link CPFType }
     *     
     */
    public CPFType getCPF() {
        return cpf;
    }

    /**
     * Sets the value of the cpf property.
     * 
     * @param value
     *     allowed object is
     *     {@link CPFType }
     *     
     */
    public void setCPF(CPFType value) {
        this.cpf = value;
    }

    /**
     * Gets the value of the cns property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the cns property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCNS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CNSType }
     * 
     * 
     * @return
     *     The value of the cns property.
     */
    public List<CNSType> getCNS() {
        if (cns == null) {
            cns = new ArrayList<>();
        }
        return this.cns;
    }

}
