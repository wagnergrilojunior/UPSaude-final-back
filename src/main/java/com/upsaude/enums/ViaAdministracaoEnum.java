package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum unificado para classificação de vias de administração.
 * Consolida ViaAdministracaoEnum, ViaAdministracaoMedicamentoEnum e ViaAdministracaoVacinaEnum.
 * Baseado em padrões médicos, farmacêuticos, ANVISA e PNI.
 *
 * @author UPSaúde
 */
public enum ViaAdministracaoEnum {
    // ========== VIAS COMUNS ==========
    ORAL(1, "Oral"),
    SUBLINGUAL(2, "Sublingual"),
    RETAL(3, "Retal"),
    VAGINAL(4, "Vaginal"),
    TOPICA(5, "Tópica"),
    TRANSDERMICA(6, "Transdérmica"),
    
    // ========== VIAS PARENTERAL (INJETÁVEIS) ==========
    INTRAMUSCULAR(7, "Intramuscular"),
    INTRAVENOSA(8, "Intravenosa"),
    SUBCUTANEA(9, "Subcutânea"),
    INTRADERMICA(10, "Intradérmica"),
    INTRATECAL(11, "Intratecal"),
    INTRAPERITONEAL(12, "Intraperitoneal"),
    INTRACARDIACA(13, "Intracardíaca"),
    INTRAAORTICA(14, "Intra-aórtica"),
    INTRAPULMONAR(15, "Intrapulmonar"),
    
    // ========== VIAS ESPECÍFICAS DE VACINA ==========
    SUBCUTANEA_PROFUNDA(21, "Subcutânea Profunda"),
    INTRADERMICA_MULTIPUNCAO(22, "Intradérmica (Múltipla punção)"),
    
    // ========== VIAS DE ADMINISTRAÇÃO ESPECIAL ==========
    NASAL(16, "Nasal"),
    OFTALMICA(17, "Oftálmica"),
    OTICA(18, "Ótica"),
    INALATORIA(19, "Inalatória"),
    ENDOTRAQUEAL(20, "Endotraqueal"),
    
    // ========== OUTRAS ==========
    OUTRA(99, "Outra");

    private final Integer codigo;
    private final String descricao;

    ViaAdministracaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ViaAdministracaoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static ViaAdministracaoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método de compatibilidade para conversão do ViaAdministracaoMedicamentoEnum antigo.
     * Mapeia códigos do enum antigo para o novo enum unificado.
     */
    public static ViaAdministracaoEnum fromCodigoMedicamentoAntigo(Integer codigoAntigo) {
        if (codigoAntigo == null) return null;
        
        // Mapeamento direto - os códigos foram mantidos compatíveis
        return fromCodigo(codigoAntigo);
    }

    /**
     * Método de compatibilidade para conversão do ViaAdministracaoVacinaEnum antigo.
     * Mapeia códigos do enum antigo para o novo enum unificado.
     */
    public static ViaAdministracaoEnum fromCodigoVacinaAntigo(Integer codigoAntigo) {
        if (codigoAntigo == null) return null;
        
        // Mapeamento específico para vacinas (códigos podem diferir)
        switch (codigoAntigo) {
            case 1: return INTRAMUSCULAR;
            case 2: return SUBCUTANEA;
            case 3: return INTRADERMICA;
            case 4: return ORAL;
            case 5: return NASAL;
            case 6: return INTRADERMICA_MULTIPUNCAO;
            case 7: return SUBCUTANEA_PROFUNDA;
            default: return fromCodigo(codigoAntigo);
        }
    }
}
