
package com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoEmailType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="TipoEmailType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="P"/>
 *     <enumeration value="A"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "TipoEmailType", namespace = "http://servicos.saude.gov.br/schema/corporativo/v1r2/email")
@XmlEnum
public enum TipoEmailType {

    P,
    A;

    public String value() {
        return name();
    }

    public static TipoEmailType fromValue(String v) {
        return valueOf(v);
    }

}
