package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para tipos de ações de promoção e prevenção em saúde.
 * Baseado nas ações previstas na Política Nacional de Promoção da Saúde.
 *
 * @author UPSaúde
 */
public enum TipoAcaoPromocaoSaudeEnum {
    // Alimentação saudável
    PROMOCAO_ALIMENTACAO_SAUDAVEL(1, "Promoção da Alimentação Saudável"),
    COMBATE_OBESIDADE(2, "Combate à Obesidade"),
    INCENTIVO_ALEITAMENTO(3, "Incentivo ao Aleitamento Materno"),
    
    // Atividade física
    PRATICAS_CORPORAIS(10, "Práticas Corporais/Atividade Física"),
    ACADEMIA_SAUDE(11, "Academia da Saúde"),
    GINASTICA_LABORAL(12, "Ginástica Laboral"),
    
    // Prevenção de doenças
    PREVENCAO_TABAGISMO(20, "Prevenção e Controle do Tabagismo"),
    PREVENCAO_ALCOOLISMO(21, "Prevenção do Uso de Álcool"),
    PREVENCAO_DROGAS(22, "Prevenção do Uso de Drogas"),
    PREVENCAO_ACIDENTES(23, "Prevenção de Acidentes"),
    PREVENCAO_VIOLENCIA(24, "Prevenção da Violência"),
    PREVENCAO_DST_AIDS(25, "Prevenção DST/AIDS"),
    
    // Saúde bucal
    FLUORETACAO(30, "Fluoretação"),
    ESCOVACAO_SUPERVISIONADA(31, "Escovação Supervisionada"),
    
    // Saúde ambiental
    SANEAMENTO_BASICO(40, "Saneamento Básico"),
    CONTROLE_VETORES(41, "Controle de Vetores"),
    QUALIDADE_AGUA(42, "Qualidade da Água"),
    
    // Rastreamentos
    RASTREAMENTO_CANCER_MAMA(50, "Rastreamento Câncer de Mama"),
    RASTREAMENTO_CANCER_COLO(51, "Rastreamento Câncer de Colo de Útero"),
    RASTREAMENTO_CANCER_PROSTATA(52, "Rastreamento Câncer de Próstata"),
    RASTREAMENTO_HIPERTENSAO(53, "Rastreamento Hipertensão"),
    RASTREAMENTO_DIABETES(54, "Rastreamento Diabetes"),
    
    // Imunização
    CAMPANHA_VACINACAO(60, "Campanha de Vacinação"),
    MULTIVACINACAO(61, "Multivacinação"),
    VACINACAO_GRIPE(62, "Vacinação contra Gripe"),
    
    // Saúde mental
    PROMOCAO_SAUDE_MENTAL(70, "Promoção da Saúde Mental"),
    PREVENCAO_SUICIDIO(71, "Prevenção ao Suicídio"),
    
    // Outros
    PROGRAMA_SAUDE_ESCOLA(80, "Programa Saúde na Escola"),
    PROGRAMA_SAUDE_TRABALHO(81, "Programa Saúde do Trabalhador"),
    VIGILANCIA_EPIDEMIOLOGICA(82, "Vigilância Epidemiológica"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoAcaoPromocaoSaudeEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoAcaoPromocaoSaudeEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoAcaoPromocaoSaudeEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

