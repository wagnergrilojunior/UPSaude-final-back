
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResultadosLocalizacaoEstabelecimentoSaudeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="ResultadosLocalizacaoEstabelecimentoSaudeType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadolocalizacaoestabelecimentosaude}ResultadoLocalizacaoEstabelecimentoSaude" maxOccurs="unbounded" minOccurs="0"/>
 *         <element ref="{http://servicos.saude.gov.br/wsdl/mensageria/v1r0/paginacao}Paginacao" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultadosLocalizacaoEstabelecimentoSaudeType", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadoslocalizacaoestabelecimentosaude", propOrder = {
    "resultadoLocalizacaoEstabelecimentoSaude",
    "paginacao"
})
public class ResultadosLocalizacaoEstabelecimentoSaudeType {

    @XmlElement(name = "ResultadoLocalizacaoEstabelecimentoSaude", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/resultadolocalizacaoestabelecimentosaude")
    protected List<ResultadoLocalizacaoEstabelecimentoSaudeType> resultadoLocalizacaoEstabelecimentoSaude;
    @XmlElement(name = "Paginacao", namespace = "http://servicos.saude.gov.br/wsdl/mensageria/v1r0/paginacao")
    protected PaginacaoType paginacao;

    /**
     * Lista de Estabelecimentos de Saúde Localizados Gets the value of the resultadoLocalizacaoEstabelecimentoSaude property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the resultadoLocalizacaoEstabelecimentoSaude property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultadoLocalizacaoEstabelecimentoSaude().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResultadoLocalizacaoEstabelecimentoSaudeType }
     * 
     * 
     * @return
     *     The value of the resultadoLocalizacaoEstabelecimentoSaude property.
     */
    public List<ResultadoLocalizacaoEstabelecimentoSaudeType> getResultadoLocalizacaoEstabelecimentoSaude() {
        if (resultadoLocalizacaoEstabelecimentoSaude == null) {
            resultadoLocalizacaoEstabelecimentoSaude = new ArrayList<>();
        }
        return this.resultadoLocalizacaoEstabelecimentoSaude;
    }

    /**
     * Resposta da paginação
     * 					
     * 
     * @return
     *     possible object is
     *     {@link PaginacaoType }
     *     
     */
    public PaginacaoType getPaginacao() {
        return paginacao;
    }

    /**
     * Sets the value of the paginacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaginacaoType }
     *     
     */
    public void setPaginacao(PaginacaoType value) {
        this.paginacao = value;
    }

}
