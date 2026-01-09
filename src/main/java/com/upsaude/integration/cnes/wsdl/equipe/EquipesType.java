
package com.upsaude.integration.cnes.wsdl.equipe;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EquipesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="EquipesType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Equipe" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/equipe}EquipeType" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EquipesType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/equipe", propOrder = {
    "equipe"
})
public class EquipesType {

    @XmlElement(name = "Equipe")
    protected List<EquipeType> equipe;

    /**
     * Gets the value of the equipe property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the equipe property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEquipe().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EquipeType }
     * 
     * 
     * @return
     *     The value of the equipe property.
     */
    public List<EquipeType> getEquipe() {
        if (equipe == null) {
            equipe = new ArrayList<>();
        }
        return this.equipe;
    }

}
