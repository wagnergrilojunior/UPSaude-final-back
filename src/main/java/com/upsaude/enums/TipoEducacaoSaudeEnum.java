package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoEducacaoSaudeEnum {

    GRUPO_GESTANTES(1, "Grupo de Gestantes"),
    GRUPO_HIPERTENSOS(2, "Grupo de Hipertensos"),
    GRUPO_DIABETICOS(3, "Grupo de Diabéticos"),
    GRUPO_TABAGISMO(4, "Grupo de Tabagismo"),
    GRUPO_EMAGRECIMENTO(5, "Grupo de Emagrecimento"),
    GRUPO_CAMINHADA(6, "Grupo de Caminhada"),
    GRUPO_IDOSOS(7, "Grupo de Idosos"),
    GRUPO_ADOLESCENTES(8, "Grupo de Adolescentes"),
    GRUPO_SAUDE_MENTAL(9, "Grupo de Saúde Mental"),
    GRUPO_ALEITAMENTO_MATERNO(10, "Grupo de Aleitamento Materno"),

    PALESTRA_SAUDE(20, "Palestra de Saúde"),
    PALESTRA_PREVENCAO_DST(21, "Palestra Prevenção DST/AIDS"),
    PALESTRA_ALIMENTACAO(22, "Palestra sobre Alimentação Saudável"),
    PALESTRA_HIPERTENSAO(23, "Palestra sobre Hipertensão"),
    PALESTRA_DIABETES(24, "Palestra sobre Diabetes"),
    PALESTRA_SAUDE_BUCAL(25, "Palestra sobre Saúde Bucal"),
    PALESTRA_VACINACAO(26, "Palestra sobre Vacinação"),
    PALESTRA_HIGIENE(27, "Palestra sobre Higiene"),

    OFICINA_CULINARIA_SAUDAVEL(30, "Oficina de Culinária Saudável"),
    OFICINA_ATIVIDADE_FISICA(31, "Oficina de Atividade Física"),
    OFICINA_ARTESANATO(32, "Oficina de Artesanato Terapêutico"),
    OFICINA_MEDITACAO(33, "Oficina de Meditação/Relaxamento"),

    CAMPANHA_VACINACAO(40, "Campanha de Vacinação"),
    CAMPANHA_PREVENCAO_CANCER(41, "Campanha de Prevenção ao Câncer"),
    CAMPANHA_NOVEMBRO_AZUL(42, "Campanha Novembro Azul"),
    CAMPANHA_OUTUBRO_ROSA(43, "Campanha Outubro Rosa"),
    CAMPANHA_SETEMBRO_AMARELO(44, "Campanha Setembro Amarelo"),

    ORIENTACAO_INDIVIDUAL(50, "Orientação Individual"),
    ACONSELHAMENTO(51, "Aconselhamento"),

    ACAO_COMUNITARIA(60, "Ação Comunitária"),
    MUTIRAO_SAUDE(61, "Mutirão de Saúde"),
    VISITA_ESCOLAR(62, "Visita a Escola"),

    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoEducacaoSaudeEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoEducacaoSaudeEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoEducacaoSaudeEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
