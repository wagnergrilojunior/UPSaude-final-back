
package com.upsaude.integration.cnes.wsdl.equipe;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
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
 *         <element name="identificador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="Mensagem" type="{http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/mensagem}MensagemType" maxOccurs="unbounded" minOccurs="0"/>
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
    "identificador",
    "mensagem"
})
@XmlRootElement(name = "MSFalha", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/msfalha")
public class MSFalha {

    @XmlElementRef(name = "identificador", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/msfalha", type = JAXBElement.class, required = false)
    protected JAXBElement<String> identificador;
    @XmlElement(name = "Mensagem", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/msfalha")
    protected List<MensagemType> mensagem;

    /**
     * Gets the value of the identificador property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdentificador() {
        return identificador;
    }

    /**
     * Sets the value of the identificador property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdentificador(JAXBElement<String> value) {
        this.identificador = value;
    }

    /**
     * Gets the value of the mensagem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the mensagem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMensagem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MensagemType }
     * 
     * 
     * @return
     *     The value of the mensagem property.
     */
    public List<MensagemType> getMensagem() {
        if (mensagem == null) {
            mensagem = new ArrayList<>();
        }
        return this.mensagem;
    }

}
