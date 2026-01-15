package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoContaBancariaEnum {
    CORRENTE(1, "CORRENTE", "Conta Corrente"),
    POUPANCA(2, "POUPANCA", "Conta Poupança"),
    PAGAMENTO(3, "PAGAMENTO", "Conta Pagamento"),
    SALARIO(4, "SALARIO", "Conta Salário");

    private final Integer codigo;
    private final String nome;
    private final String descricao;

    TipoContaBancariaEnum(Integer codigo, String nome, String descricao) {
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

    public static TipoContaBancariaEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoContaBancariaEnum fromNome(String nome) {
        if (nome == null)
            return null;
        String n = nome.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.nome.equals(n))
                .findFirst()
                .orElse(null);
    }
}
