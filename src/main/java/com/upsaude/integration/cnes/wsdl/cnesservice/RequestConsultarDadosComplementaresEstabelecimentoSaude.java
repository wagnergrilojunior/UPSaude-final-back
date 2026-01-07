
package com.upsaude.integration.cnes.wsdl.cnesservice;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="MunicipioCNES" type="{http://servicos.saude.gov.br/schema/corporativo/v1r2/municipio}MunicipioType"/>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/cmpt}cmpt"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "municipioCNES",
    "cmpt"
})
@XmlRootElement(name = "requestConsultarDadosComplementaresEstabelecimentoSaude")
public class RequestConsultarDadosComplementaresEstabelecimentoSaude {

    @XmlElement(name = "MunicipioCNES", required = true)
    protected MunicipioType municipioCNES;
    @XmlElement(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/cmpt", required = true)
    protected CmptType cmpt;

    /**
     * Gets the value of the municipioCNES property.
     * 
     * @return
     *     possible object is
     *     {@link MunicipioType }
     *     
     */
    public MunicipioType getMunicipioCNES() {
        return municipioCNES;
    }

    /**
     * Sets the value of the municipioCNES property.
     * 
     * @param value
     *     allowed object is
     *     {@link MunicipioType }
     *     
     */
    public void setMunicipioCNES(MunicipioType value) {
        this.municipioCNES = value;
    }

    /**
     * Gets the value of the cmpt property.
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

}
