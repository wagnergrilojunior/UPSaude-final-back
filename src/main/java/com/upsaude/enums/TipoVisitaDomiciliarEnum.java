package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoVisitaDomiciliarEnum {
    CADASTRAMENTO(1, "Cadastramento de Família"),
    ACOMPANHAMENTO_GESTANTE(2, "Acompanhamento de Gestante"),
    ACOMPANHAMENTO_PUERPERA(3, "Acompanhamento de Puérpera"),
    ACOMPANHAMENTO_RECEM_NASCIDO(4, "Acompanhamento de Recém-nascido"),
    ACOMPANHAMENTO_CRIANCA(5, "Acompanhamento de Criança"),
    ACOMPANHAMENTO_IDOSO(6, "Acompanhamento de Idoso"),
    ACOMPANHAMENTO_ACAMADO(7, "Acompanhamento de Acamado"),
    ACOMPANHAMENTO_HIPERTENSO(8, "Acompanhamento de Hipertenso"),
    ACOMPANHAMENTO_DIABETICO(9, "Acompanhamento de Diabético"),
    ACOMPANHAMENTO_TUBERCULOSE(10, "Acompanhamento de Tuberculose"),
    ACOMPANHAMENTO_HANSENIASE(11, "Acompanhamento de Hanseníase"),
    ACOMPANHAMENTO_SAUDE_MENTAL(12, "Acompanhamento de Saúde Mental"),
    BUSCA_ATIVA_FALTOSO(13, "Busca Ativa de Faltoso"),
    BUSCA_ATIVA_VACINACAO(14, "Busca Ativa de Vacinação"),
    CONVOCACAO(15, "Convocação"),
    ORIENTACAO_SAUDE(16, "Orientação de Saúde"),
    ENTREGA_MEDICAMENTO(17, "Entrega de Medicamento"),
    COLETA_EXAME(18, "Coleta de Exame"),
    CURATIVO_DOMICILIAR(19, "Curativo Domiciliar"),
    VERIFICACAO_PRESSAO(20, "Verificação de Pressão"),
    VERIFICACAO_GLICEMIA(21, "Verificação de Glicemia"),
    ATUALIZACAO_CADASTRO(22, "Atualização de Cadastro"),
    INVESTIGACAO_EPIDEMIOLOGICA(23, "Investigação Epidemiológica"),
    POS_ALTA_HOSPITALAR(24, "Pós-alta Hospitalar"),
    EGRESSO_INTERNACAO(25, "Egresso de Internação"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoVisitaDomiciliarEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoVisitaDomiciliarEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoVisitaDomiciliarEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
