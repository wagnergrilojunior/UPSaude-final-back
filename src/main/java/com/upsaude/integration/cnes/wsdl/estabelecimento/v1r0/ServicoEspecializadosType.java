
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServicoEspecializadosType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ServicoEspecializadosType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="servicoespecializado" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/servicoespecializado}ServicoEspecializadoType" maxOccurs="unbounded"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServicoEspecializadosType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/servicoespecializados", propOrder = {
    "servicoespecializado"
})
public class ServicoEspecializadosType {

    @XmlElement(required = true)
    protected List<ServicoEspecializadoType> servicoespecializado;

    /**
     * Gets the value of the servicoespecializado property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the servicoespecializado property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServicoespecializado().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServicoEspecializadoType }
     * 
     * 
     * @return
     *     The value of the servicoespecializado property.
     */
    public List<ServicoEspecializadoType> getServicoespecializado() {
        if (servicoespecializado == null) {
            servicoespecializado = new ArrayList<>();
        }
        return this.servicoespecializado;
    }

}
