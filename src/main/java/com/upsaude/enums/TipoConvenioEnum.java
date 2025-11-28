package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para classificação de tipos de convênios/planos de saúde.
 * Baseado em padrões da ANS (Agência Nacional de Saúde Suplementar).
 *
 * @author UPSaúde
 */
public enum TipoConvenioEnum {
    PLANO_SAUDE(1, "Plano de Saúde"),
    OPERADORA_PLANO_SAUDE(2, "Operadora de Plano de Saúde"),
    SEGURADORA(3, "Seguradora"),
    AUTOGESTAO(4, "Autogestão"),
    MEDICINA_GRUPO(5, "Medicina de Grupo"),
    COOPERATIVA_MEDICA(6, "Cooperativa Médica"),
    PLANO_ADMINISTRADOR(7, "Plano Administrador"),
    PLANO_DENTAL(8, "Plano Dental"),
    PLANO_ODONTO(9, "Plano Odontológico"),
    CONVENIO_MEDICO(10, "Convênio Médico"),
    CONVENIO_ODONTOLOGICO(11, "Convênio Odontológico"),
    CONVENIO_FARMACIA(12, "Convênio Farmácia"),
    CONVENIO_LABORATORIO(13, "Convênio Laboratório"),
    CONVENIO_EXAMES(14, "Convênio Exames"),
    CONVENIO_FISIOTERAPIA(15, "Convênio Fisioterapia"),
    CONVENIO_PSICOLOGIA(16, "Convênio Psicologia"),
    CONVENIO_NUTRICIONISTA(17, "Convênio Nutricionista"),
    CONVENIO_FONOAUDIOLOGIA(18, "Convênio Fonoaudiologia"),
    CONVENIO_TERAPIA_OCUPACIONAL(19, "Convênio Terapia Ocupacional"),
    SUS(20, "SUS - Sistema Único de Saúde"),
    PARTICULAR(21, "Particular"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoConvenioEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoConvenioEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoConvenioEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

