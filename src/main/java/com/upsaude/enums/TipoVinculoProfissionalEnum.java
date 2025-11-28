package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para tipos de vínculo entre profissional de saúde e estabelecimento.
 * Define as diferentes formas de contratação ou associação profissional.
 *
 * @author UPSaúde
 */
public enum TipoVinculoProfissionalEnum {
    EFETIVO(1, "Efetivo"),
    CONTRATO(2, "Contrato"),
    TEMPORARIO(3, "Temporário"),
    TERCEIRIZADO(4, "Terceirizado"),
    ESTAGIARIO(5, "Estagiário"),
    VOLUNTARIO(6, "Voluntário"),
    CONSULTOR(7, "Consultor"),
    RESIDENTE(8, "Residente"),
    OUTRO(9, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoVinculoProfissionalEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoVinculoProfissionalEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoVinculoProfissionalEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

