
package com.upsaude.integration.cnes.wsdl.cnesservice;

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
 *         <element ref="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisacnesmunicipio}ResultadoPesquisaCnesMunicipio" maxOccurs="unbounded" minOccurs="0"/>
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
    "resultadoPesquisaCnesMunicipio"
})
@XmlRootElement(name = "responseConsultarEstabelecimentoSaudePorMunicipio")
public class ResponseConsultarEstabelecimentoSaudePorMunicipio {

    @XmlElement(name = "ResultadoPesquisaCnesMunicipio", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisacnesmunicipio")
    protected List<ResultadoPesquisaCnesMunicipioType> resultadoPesquisaCnesMunicipio;

    /**
     * Gets the value of the resultadoPesquisaCnesMunicipio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the resultadoPesquisaCnesMunicipio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultadoPesquisaCnesMunicipio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResultadoPesquisaCnesMunicipioType }
     * 
     * 
     * @return
     *     The value of the resultadoPesquisaCnesMunicipio property.
     */
    public List<ResultadoPesquisaCnesMunicipioType> getResultadoPesquisaCnesMunicipio() {
        if (resultadoPesquisaCnesMunicipio == null) {
            resultadoPesquisaCnesMunicipio = new ArrayList<>();
        }
        return this.resultadoPesquisaCnesMunicipio;
    }

}
