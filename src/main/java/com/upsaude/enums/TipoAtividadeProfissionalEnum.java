package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoAtividadeProfissionalEnum {
    ATENDIMENTO_PACIENTE(1, "Atendimento ao Paciente"),
    CONSULTA(2, "Consulta"),
    PROCEDIMENTO(3, "Procedimento"),
    CIRURGIA(4, "Cirurgia"),
    VISITA_PACIENTE(5, "Visita ao Paciente"),
    REUNIAO_EQUIPE(6, "Reunião de Equipe"),
    CAPACITACAO(7, "Capacitação"),
    DOCUMENTACAO(8, "Documentação"),
    ADMINISTRATIVO(9, "Atividades Administrativas"),
    EDUCACAO_SAUDE(10, "Educação em Saúde"),
    VISITA_DOMICILIAR(11, "Visita Domiciliar"),
    TELEMEDICINA(12, "Telemedicina"),
    SUPERVISAO(13, "Supervisão"),
    AUDITORIA(14, "Auditoria"),
    PESQUISA(15, "Pesquisa"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoAtividadeProfissionalEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoAtividadeProfissionalEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoAtividadeProfissionalEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
