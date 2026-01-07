
package com.upsaude.integration.cnes.wsdl.cnesservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DadosComplementaresType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DadosComplementaresType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="CnaePrincipal" type="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/cnae}CNAEType"/>
 *         <element name="CnaeSecundario" type="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/cnae}CNAEType"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/alvarasanitario}AlvaraSanitario"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DadosComplementaresType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadoscomplementaresestabelecimentosaude", propOrder = {
    "cnaePrincipal",
    "cnaeSecundario",
    "alvaraSanitario"
})
public class DadosComplementaresType {

    @XmlElement(name = "CnaePrincipal", required = true)
    protected CNAEType cnaePrincipal;
    @XmlElement(name = "CnaeSecundario", required = true)
    protected CNAEType cnaeSecundario;
    @XmlElement(name = "AlvaraSanitario", namespace = "http://servicos.saude.gov.br/schema/corporativo/pessoajuridica/v1r0/alvarasanitario", required = true)
    protected AlvaraSanitarioType alvaraSanitario;

    /**
     * Gets the value of the cnaePrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link CNAEType }
     *     
     */
    public CNAEType getCnaePrincipal() {
        return cnaePrincipal;
    }

    /**
     * Sets the value of the cnaePrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link CNAEType }
     *     
     */
    public void setCnaePrincipal(CNAEType value) {
        this.cnaePrincipal = value;
    }

    /**
     * Gets the value of the cnaeSecundario property.
     * 
     * @return
     *     possible object is
     *     {@link CNAEType }
     *     
     */
    public CNAEType getCnaeSecundario() {
        return cnaeSecundario;
    }

    /**
     * Sets the value of the cnaeSecundario property.
     * 
     * @param value
     *     allowed object is
     *     {@link CNAEType }
     *     
     */
    public void setCnaeSecundario(CNAEType value) {
        this.cnaeSecundario = value;
    }

    /**
     * Alvará Sanitário.
     * 
     * @return
     *     possible object is
     *     {@link AlvaraSanitarioType }
     *     
     */
    public AlvaraSanitarioType getAlvaraSanitario() {
        return alvaraSanitario;
    }

    /**
     * Sets the value of the alvaraSanitario property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlvaraSanitarioType }
     *     
     */
    public void setAlvaraSanitario(AlvaraSanitarioType value) {
        this.alvaraSanitario = value;
    }

}
