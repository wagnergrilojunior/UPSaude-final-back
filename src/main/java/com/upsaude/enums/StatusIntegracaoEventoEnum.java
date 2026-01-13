package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum StatusIntegracaoEventoEnum {
    PENDENTE(1, "PENDENTE", "Aguardando processamento"),
    PROCESSANDO(2, "PROCESSANDO", "Em processamento"),
    SUCESSO(3, "SUCESSO", "Envio concluído com sucesso"),
    ERRO(4, "ERRO", "Erro no envio");

    private final Integer codigo;
    private final String nome;
    private final String descricao;

    StatusIntegracaoEventoEnum(Integer codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusIntegracaoEventoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static StatusIntegracaoEventoEnum fromNome(String nome) {
        if (nome == null) return null;
        String n = nome.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.nome.equals(n))
                .findFirst()
                .orElse(null);
    }
}
