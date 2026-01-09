
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServicoClassificacoesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ServicoClassificacoesType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="servicoclassificacao" type="{http://servicos.saude.gov.br/schema/cnes/v1r0/servicoclassificacao}ServicoClassificacaoType" maxOccurs="unbounded"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServicoClassificacoesType", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/servicoclassificacoes", propOrder = {
    "servicoclassificacao"
})
public class ServicoClassificacoesType {

    @XmlElement(required = true)
    protected List<ServicoClassificacaoType> servicoclassificacao;

    /**
     * Gets the value of the servicoclassificacao property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the servicoclassificacao property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServicoclassificacao().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServicoClassificacaoType }
     * 
     * 
     * @return
     *     The value of the servicoclassificacao property.
     */
    public List<ServicoClassificacaoType> getServicoclassificacao() {
        if (servicoclassificacao == null) {
            servicoclassificacao = new ArrayList<>();
        }
        return this.servicoclassificacao;
    }

}
