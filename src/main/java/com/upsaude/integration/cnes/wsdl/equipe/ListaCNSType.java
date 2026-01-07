
package com.upsaude.integration.cnes.wsdl.equipe;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaCNSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ListaCNSType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://servicos.saude.gov.br/schema/cadsus/v5r0/cns}CNS" maxOccurs="unbounded"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaCNSType", namespace = "http://servicos.saude.gov.br/schema/cadsus/v5r0/cns", propOrder = {
    "cns"
})
public class ListaCNSType {

    @XmlElement(name = "CNS", required = true)
    protected List<CNSType> cns;

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
