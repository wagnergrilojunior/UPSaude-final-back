package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para vias de administração de medicamentos.
 * Baseado em padrões médicos e farmacêuticos.
 *
 * @deprecated Use {@link ViaAdministracaoEnum} unificado. Este enum será removido em versão futura.
 * @author UPSaúde
 */
@Deprecated
public enum ViaAdministracaoMedicamentoEnum {
    ORAL(1, "Oral"),
    SUBLINGUAL(2, "Sublingual"),
    RETAL(3, "Retal"),
    VAGINAL(4, "Vaginal"),
    TOPICA(5, "Tópica"),
    TRANSDERMICA(6, "Transdérmica"),
    INTRAMUSCULAR(7, "Intramuscular"),
    INTRAVENOSA(8, "Intravenosa"),
    SUBCUTANEA(9, "Subcutânea"),
    INTRADERMICA(10, "Intradérmica"),
    INTRATECAL(11, "Intratecal"),
    INTRAPERITONEAL(12, "Intraperitoneal"),
    INTRACARDIACA(13, "Intracardíaca"),
    INTRAAORTICA(14, "Intra-aórtica"),
    INTRAPULMONAR(15, "Intrapulmonar"),
    NASAL(16, "Nasal"),
    OFTALMICA(17, "Oftálmica"),
    OTICA(18, "Ótica"),
    INALATORIA(19, "Inalatória"),
    ENDOTRAQUEAL(20, "Endotraqueal"),
    OUTRA(99, "Outra");

    private final Integer codigo;
    private final String descricao;

    ViaAdministracaoMedicamentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ViaAdministracaoMedicamentoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static ViaAdministracaoMedicamentoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

