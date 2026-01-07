
package com.upsaude.integration.cnes.wsdl.profissional;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoCNSType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>{@code
 * <simpleType name="TipoCNSType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="D"/>
 *     <enumeration value="P"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "TipoCNSType", namespace = "http://servicos.saude.gov.br/schema/cadsus/v5r0/cns")
@XmlEnum
public enum TipoCNSType {

    D,
    P;

    public String value() {
        return name();
    }

    public static TipoCNSType fromValue(String v) {
        return valueOf(v);
    }

}
