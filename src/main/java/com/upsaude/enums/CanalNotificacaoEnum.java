package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum CanalNotificacaoEnum {
    EMAIL(1, "E-mail"),
    SMS(2, "SMS"),
    WHATSAPP(3, "WhatsApp"),
    MENSAGEM_INTERNA(4, "Mensagem Interna"),
    PUSH_NOTIFICATION(5, "Push Notification"),
    TELEFONE(6, "Telefone"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    CanalNotificacaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static CanalNotificacaoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static CanalNotificacaoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
