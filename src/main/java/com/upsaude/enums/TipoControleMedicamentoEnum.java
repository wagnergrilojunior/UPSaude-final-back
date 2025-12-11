package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoControleMedicamentoEnum {
    LIVRE(1, "Livre Comercialização"),
    RECEITA_SIMPLES(2, "Receita Simples"),
    RECEITA_CONTROLADA(3, "Receita Controlada"),
    RECEITA_AZUL(4, "Receita Azul (A1)"),
    RECEITA_AMARELA(5, "Receita Amarela (A2)"),
    RECEITA_BRANCA(6, "Receita Branca (B1)"),
    RECEITA_BRANCA_ESPECIAL(7, "Receita Branca Especial (B2)"),
    RECEITA_VERDE(8, "Receita Verde (C1)"),
    RECEITA_VERDE_ESPECIAL(9, "Receita Verde Especial (C2)"),
    PORTARIA_344(10, "Portaria 344 (Substâncias Controladas)"),
    PORTARIA_344_LISTA_A1(11, "Portaria 344 - Lista A1"),
    PORTARIA_344_LISTA_A2(12, "Portaria 344 - Lista A2"),
    PORTARIA_344_LISTA_B1(13, "Portaria 344 - Lista B1"),
    PORTARIA_344_LISTA_B2(14, "Portaria 344 - Lista B2"),
    PORTARIA_344_LISTA_C1(15, "Portaria 344 - Lista C1"),
    PORTARIA_344_LISTA_C2(16, "Portaria 344 - Lista C2"),
    PORTARIA_344_LISTA_C3(17, "Portaria 344 - Lista C3"),
    PORTARIA_344_LISTA_C4(18, "Portaria 344 - Lista C4"),
    PORTARIA_344_LISTA_C5(19, "Portaria 344 - Lista C5"),
    PORTARIA_344_LISTA_D1(20, "Portaria 344 - Lista D1"),
    PORTARIA_344_LISTA_D2(21, "Portaria 344 - Lista D2"),
    PORTARIA_344_LISTA_E(22, "Portaria 344 - Lista E"),
    PORTARIA_344_LISTA_F1(23, "Portaria 344 - Lista F1"),
    PORTARIA_344_LISTA_F2(24, "Portaria 344 - Lista F2"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoControleMedicamentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoControleMedicamentoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoControleMedicamentoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
