package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para classificação de tipos de vacinas conforme PNI (Programa Nacional de Imunizações).
 * Baseado no Calendário Nacional de Vacinação do Ministério da Saúde.
 *
 * @author UPSaúde
 */
public enum TipoVacinaEnum {
    // ========== VACINAS DO CALENDÁRIO BÁSICO ==========
    BCG(1, "BCG - Tuberculose"),
    HEPATITE_B(2, "Hepatite B"),
    PENTAVALENTE(3, "Pentavalente (DTP + Hib + Hepatite B)"),
    DTP(4, "DTP - Difteria, Tétano e Coqueluche"),
    DTPA(5, "DTPa - Difteria, Tétano e Coqueluche Acelular"),
    DT(6, "DT - Difteria e Tétano"),
    DTPA_VIP(7, "DTPa + VIP - Difteria, Tétano, Coqueluche Acelular + Poliomielite Inativada"),
    VIP(8, "VIP - Poliomielite Inativada"),
    VOP(9, "VOP - Poliomielite Oral"),
    ROTAVIRUS(10, "Rotavírus Humano"),
    MENINGOCOCICA_C(11, "Meningocócica C"),
    MENINGOCOCICA_ACWY(12, "Meningocócica ACWY"),
    MENINGOCOCICA_B(13, "Meningocócica B"),
    PNEUMOCOCICA_10(14, "Pneumocócica 10-valente"),
    PNEUMOCOCICA_13(15, "Pneumocócica 13-valente"),
    PNEUMOCOCICA_23(16, "Pneumocócica 23-valente"),
    FEBRE_AMARELA(17, "Febre Amarela"),
    TRIPLICE_VIRAL(18, "Tríplice Viral - Sarampo, Caxumba e Rubéola"),
    TETRA_VIRAL(19, "Tetra Viral - Sarampo, Caxumba, Rubéola e Varicela"),
    VARICELA(20, "Varicela"),
    HEPATITE_A(21, "Hepatite A"),
    HPV_QUADRIVALENTE(22, "HPV Quadrivalente"),
    HPV_BIVALENTE(23, "HPV Bivalente"),
    DENGUE(24, "Dengue"),
    
    // ========== VACINAS PARA ADULTOS E IDOSOS ==========
    DUPLA_ADULTO(30, "Dupla Adulto - Difteria e Tétano"),
    HEPATITE_B_ADULTO(31, "Hepatite B (Adulto)"),
    FEBRE_AMARELA_ADULTO(32, "Febre Amarela (Adulto)"),
    TRIPLICE_VIRAL_ADULTO(33, "Tríplice Viral (Adulto)"),
    PNEUMOCOCICA_23_ADULTO(34, "Pneumocócica 23-valente (Adulto)"),
    INFLUENZA(35, "Influenza (Gripe)"),
    HERPES_ZOSTER(36, "Herpes Zoster"),
    
    // ========== VACINAS ESPECIAIS ==========
    RABICA(40, "Raiva"),
    ANTIRRABICA(41, "Antirrábica"),
    ANTITETANICA(42, "Antitetânica"),
    ANTIDIFTERICA(43, "Antidiftérica"),
    TETANO_ACIDENTAL(44, "Tétano (Acidental)"),
    HEPATITE_A_E_B(45, "Hepatite A e B Combinada"),
    
    // ========== VACINAS PARA GRUPOS ESPECIAIS ==========
    COVID_19(50, "COVID-19"),
    COVID_19_CORONAVAC(51, "COVID-19 - Coronavac"),
    COVID_19_ASTRAZENECA(52, "COVID-19 - AstraZeneca"),
    COVID_19_PFIZER(53, "COVID-19 - Pfizer"),
    COVID_19_JANSSEN(54, "COVID-19 - Janssen"),
    COVID_19_BUTANVAC(55, "COVID-19 - Butanvac"),
    
    // ========== VACINAS PARA VIAGENS ==========
    FEBRE_TIFOIDE(60, "Febre Tifoide"),
    COLERA(61, "Cólera"),
    ENCEFALITE_JAPONESA(62, "Encefalite Japonesa"),
    MENINGOCOCICA_ACWY_VIAGEM(63, "Meningocócica ACWY (Viagem)"),
    
    // ========== OUTRAS ==========
    OUTRA(99, "Outra");

    private final Integer codigo;
    private final String descricao;

    TipoVacinaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoVacinaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoVacinaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

