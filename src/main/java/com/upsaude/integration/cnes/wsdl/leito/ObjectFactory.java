
package com.upsaude.integration.cnes.wsdl.leito;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.upsaude.integration.cnes.wsdl.leito package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _CodigoCNES_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/codigocnes", "CodigoCNES");
    private static final QName _Leito_QNAME = new QName("http://servicos.saude.gov.br/schema/cnes/v1r0/leito", "leito");
    private static final QName _Mensagem_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/mensagem", "Mensagem");
    private static final QName _MSFalhaIdentificador_QNAME = new QName("http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/msfalha", "identificador");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.upsaude.integration.cnes.wsdl.leito
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RequestConsultarLeitosCNES }
     * 
     * @return
     *     the new instance of {@link RequestConsultarLeitosCNES }
     */
    public RequestConsultarLeitosCNES createRequestConsultarLeitosCNES() {
        return new RequestConsultarLeitosCNES();
    }

    /**
     * Create an instance of {@link CodigoCNESType }
     * 
     * @return
     *     the new instance of {@link CodigoCNESType }
     */
    public CodigoCNESType createCodigoCNESType() {
        return new CodigoCNESType();
    }

    /**
     * Create an instance of {@link ResponseConsultarLeitosCNES }
     * 
     * @return
     *     the new instance of {@link ResponseConsultarLeitosCNES }
     */
    public ResponseConsultarLeitosCNES createResponseConsultarLeitosCNES() {
        return new ResponseConsultarLeitosCNES();
    }

    /**
     * Create an instance of {@link LeitoType }
     * 
     * @return
     *     the new instance of {@link LeitoType }
     */
    public LeitoType createLeitoType() {
        return new LeitoType();
    }

    /**
     * Create an instance of {@link MSFalha }
     * 
     * @return
     *     the new instance of {@link MSFalha }
     */
    public MSFalha createMSFalha() {
        return new MSFalha();
    }

    /**
     * Create an instance of {@link MensagemType }
     * 
     * @return
     *     the new instance of {@link MensagemType }
     */
    public MensagemType createMensagemType() {
        return new MensagemType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodigoCNESType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CodigoCNESType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/codigocnes", name = "CodigoCNES")
    public JAXBElement<CodigoCNESType> createCodigoCNES(CodigoCNESType value) {
        return new JAXBElement<>(_CodigoCNES_QNAME, CodigoCNESType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LeitoType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LeitoType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/schema/cnes/v1r0/leito", name = "leito")
    public JAXBElement<LeitoType> createLeito(LeitoType value) {
        return new JAXBElement<>(_Leito_QNAME, LeitoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MensagemType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MensagemType }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/mensagem", name = "Mensagem")
    public JAXBElement<MensagemType> createMensagem(MensagemType value) {
        return new JAXBElement<>(_Mensagem_QNAME, MensagemType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://servicos.saude.gov.br/wsdl/mensageria/falha/v5r0/msfalha", name = "identificador", scope = MSFalha.class)
    public JAXBElement<String> createMSFalhaIdentificador(String value) {
        return new JAXBElement<>(_MSFalhaIdentificador_QNAME, String.class, MSFalha.class, value);
    }

}
