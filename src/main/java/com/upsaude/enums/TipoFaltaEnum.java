package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para classificação de tipos de falta.
 * Baseado em padrões de sistemas de gestão de recursos humanos.
 *
 * @author UPSaúde
 */
public enum TipoFaltaEnum {
    FALTA_JUSTIFICADA(1, "Falta Justificada"),
    FALTA_NAO_JUSTIFICADA(2, "Falta Não Justificada"),
    ATESTADO_MEDICO(3, "Atestado Médico"),
    LICENCA_MEDICA(4, "Licença Médica"),
    FERIAS(5, "Férias"),
    LICENCA_PREMIUM(6, "Licença Prêmio"),
    LICENCA_MATERNIDADE(7, "Licença Maternidade"),
    LICENCA_PATERNIDADE(8, "Licença Paternidade"),
    LICENCA_GALA(9, "Licença Gala"),
    LICENCA_LUTO(10, "Licença Luto"),
    AUSENCIA_COM_AUTORIZACAO(11, "Ausência com Autorização"),
    ABANDONO_CARGO(12, "Abandono de Cargo"),
    SUSPENSAO(13, "Suspensão"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoFaltaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoFaltaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoFaltaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

