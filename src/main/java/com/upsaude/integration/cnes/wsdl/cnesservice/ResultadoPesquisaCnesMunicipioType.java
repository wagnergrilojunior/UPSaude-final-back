
package com.upsaude.integration.cnes.wsdl.cnesservice;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResultadoPesquisaCnesMunicipioType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ResultadoPesquisaCnesMunicipioType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosestabelecimento}DadosBasicosEstabelecimento" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="sumario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultadoPesquisaCnesMunicipioType", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadopesquisacnesmunicipio", propOrder = {
    "dadosBasicosEstabelecimento",
    "sumario"
})
public class ResultadoPesquisaCnesMunicipioType {

    @XmlElement(name = "DadosBasicosEstabelecimento", namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/dadosbasicosestabelecimento")
    protected List<DadosBasicosEstabelecimentoType> dadosBasicosEstabelecimento;
    protected int sumario;

    /**
     * Dados Básicos do Estabelecimento de Saúde..Gets the value of the dadosBasicosEstabelecimento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the dadosBasicosEstabelecimento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDadosBasicosEstabelecimento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DadosBasicosEstabelecimentoType }
     * 
     * 
     * @return
     *     The value of the dadosBasicosEstabelecimento property.
     */
    public List<DadosBasicosEstabelecimentoType> getDadosBasicosEstabelecimento() {
        if (dadosBasicosEstabelecimento == null) {
            dadosBasicosEstabelecimento = new ArrayList<>();
        }
        return this.dadosBasicosEstabelecimento;
    }

    /**
     * Gets the value of the sumario property.
     * 
     */
    public int getSumario() {
        return sumario;
    }

    /**
     * Sets the value of the sumario property.
     * 
     */
    public void setSumario(int value) {
        this.sumario = value;
    }

}
