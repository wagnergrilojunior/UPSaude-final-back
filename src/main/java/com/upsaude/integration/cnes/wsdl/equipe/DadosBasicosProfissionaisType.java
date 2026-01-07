
package com.upsaude.integration.cnes.wsdl.equipe;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DadosBasicosProfissionaisType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="DadosBasicosProfissionaisType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="DadosBasicosProfissional" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosprofissional}DadosBasicosProfissionalType" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DadosBasicosProfissionaisType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosprofissional", propOrder = {
    "dadosBasicosProfissional"
})
public class DadosBasicosProfissionaisType {

    @XmlElement(name = "DadosBasicosProfissional")
    protected List<DadosBasicosProfissionalType> dadosBasicosProfissional;

    /**
     * Gets the value of the dadosBasicosProfissional property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the dadosBasicosProfissional property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDadosBasicosProfissional().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DadosBasicosProfissionalType }
     * 
     * 
     * @return
     *     The value of the dadosBasicosProfissional property.
     */
    public List<DadosBasicosProfissionalType> getDadosBasicosProfissional() {
        if (dadosBasicosProfissional == null) {
            dadosBasicosProfissional = new ArrayList<>();
        }
        return this.dadosBasicosProfissional;
    }

}
