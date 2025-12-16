package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoDoencaEnum {
    CRONICA(1, "Doença Crônica"),
    AGUDA(2, "Doença Aguda"),
    INFECCIOSA(3, "Doença Infecciosa"),
    PARASITARIA(4, "Doença Parasitária"),
    VIRAL(5, "Doença Viral"),
    BACTERIANA(6, "Doença Bacteriana"),
    FUNGICA(7, "Doença Fúngica"),
    NEOPLASICA(8, "Doença Neoplásica"),
    CARDIOVASCULAR(9, "Doença Cardiovascular"),
    RESPIRATORIA(10, "Doença Respiratória"),
    DIGESTIVA(11, "Doença Digestiva"),
    ENDOCRINA(12, "Doença Endócrina"),
    METABOLICA(13, "Doença Metabólica"),
    NEUROLOGICA(14, "Doença Neurológica"),
    PSIQUIATRICA(15, "Doença Psiquiátrica"),
    REUMATOLOGICA(16, "Doença Reumatológica"),
    DERMATOLOGICA(17, "Doença Dermatológica"),
    OFTALMOLOGICA(18, "Doença Oftalmológica"),
    OTORRINOLARINGOLOGICA(19, "Doença Otorrinolaringológica"),
    UROLOGICA(20, "Doença Urológica"),
    GINECOLOGICA(21, "Doença Ginecológica"),
    ORTOPEDICA(22, "Doença Ortopédica"),
    HEMATOLOGICA(23, "Doença Hematológica"),
    IMUNOLOGICA(24, "Doença Imunológica"),
    AUTOIMUNE(25, "Doença Autoimune"),
    GENETICA(26, "Doença Genética"),
    CONGENITA(27, "Doença Congênita"),
    DEGENERATIVA(28, "Doença Degenerativa"),
    ALERGICA(29, "Doença Alérgica"),
    INTOXICACAO(30, "Intoxicação"),
    TRAUMATICA(31, "Doença Traumática"),
    OUTRA(99, "Outra");

    private final Integer codigo;
    private final String descricao;

    TipoDoencaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoDoencaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoDoencaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
