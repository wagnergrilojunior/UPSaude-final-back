package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para tipos de cuidados de enfermagem.
 * Baseado nos procedimentos comuns em unidades básicas de saúde.
 *
 * @author UPSaúde
 */
public enum TipoCuidadoEnfermagemEnum {
    // Curativos
    CURATIVO_SIMPLES(1, "Curativo Simples"),
    CURATIVO_COMPLEXO(2, "Curativo Complexo"),
    CURATIVO_FERIDA_CIRURGICA(3, "Curativo de Ferida Cirúrgica"),
    CURATIVO_ULCERA(4, "Curativo de Úlcera"),
    CURATIVO_QUEIMADURA(5, "Curativo de Queimadura"),
    
    // Administração de medicamentos
    ADMINISTRACAO_MEDICAMENTO_ORAL(10, "Administração de Medicamento Oral"),
    ADMINISTRACAO_MEDICAMENTO_IM(11, "Administração de Medicamento IM"),
    ADMINISTRACAO_MEDICAMENTO_IV(12, "Administração de Medicamento IV"),
    ADMINISTRACAO_MEDICAMENTO_SC(13, "Administração de Medicamento SC"),
    ADMINISTRACAO_MEDICAMENTO_TOPICO(14, "Administração de Medicamento Tópico"),
    NEBULIZACAO(15, "Nebulização"),
    OXIGENOTERAPIA(16, "Oxigenoterapia"),
    
    // Procedimentos
    VERIFICACAO_SINAIS_VITAIS(20, "Verificação de Sinais Vitais"),
    AFERICAO_PRESSAO(21, "Aferição de Pressão Arterial"),
    VERIFICACAO_GLICEMIA(22, "Verificação de Glicemia Capilar"),
    COLETA_SANGUE(23, "Coleta de Sangue"),
    COLETA_URINA(24, "Coleta de Urina"),
    SONDAGEM_VESICAL(25, "Sondagem Vesical"),
    SONDAGEM_NASOGASTRICA(26, "Sondagem Nasogástrica"),
    ASPIRACAO_VIAS_AEREAS(27, "Aspiração de Vias Aéreas"),
    RETIRADA_PONTOS(28, "Retirada de Pontos"),
    
    // Cuidados gerais
    HIGIENE_CORPORAL(30, "Higiene Corporal"),
    BANHO_LEITO(31, "Banho no Leito"),
    MUDANCA_DECUBITO(32, "Mudança de Decúbito"),
    ALIMENTACAO_ASSISTIDA(33, "Alimentação Assistida"),
    
    // Orientações
    ORIENTACAO_AUTOCUIDADO(40, "Orientação de Autocuidado"),
    ORIENTACAO_MEDICAMENTO(41, "Orientação sobre Medicamentos"),
    ORIENTACAO_DIETA(42, "Orientação Dietética"),
    ORIENTACAO_EXERCICIO(43, "Orientação sobre Exercícios"),
    
    // Teste rápido
    TESTE_RAPIDO_HIV(50, "Teste Rápido HIV"),
    TESTE_RAPIDO_SIFILIS(51, "Teste Rápido Sífilis"),
    TESTE_RAPIDO_HEPATITE_B(52, "Teste Rápido Hepatite B"),
    TESTE_RAPIDO_HEPATITE_C(53, "Teste Rápido Hepatite C"),
    TESTE_RAPIDO_GRAVIDEZ(54, "Teste Rápido de Gravidez"),
    
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoCuidadoEnfermagemEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoCuidadoEnfermagemEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoCuidadoEnfermagemEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

