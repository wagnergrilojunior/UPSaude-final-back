
package com.upsaude.integration.cnes.wsdl.leito;

import java.util.ArrayList;
import java.util.List;
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
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/leito}leito" maxOccurs="unbounded" minOccurs="0"/>
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
    "leito"
})
@XmlRootElement(name = "responseConsultarLeitosCNES")
public class ResponseConsultarLeitosCNES {

    @XmlElement(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/leito")
    protected List<LeitoType> leito;

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

}
