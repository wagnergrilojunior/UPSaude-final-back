package com.upsaude.util;

import com.upsaude.exception.BadRequestException;

import java.util.regex.Pattern;

/**
 * Utilitário para validação de formatos CNES (Código Nacional de Estabelecimento de Saúde),
 * CNS (Cartão Nacional de Saúde), INE (Identificador Nacional de Equipes) e competência.
 */
public class CnesValidator {

    // CNES: 7 dígitos numéricos
    private static final Pattern CNES_PATTERN = Pattern.compile("^\\d{7}$");
    
    // CNS: 15 dígitos numéricos
    private static final Pattern CNS_PATTERN = Pattern.compile("^\\d{15}$");
    
    // INE: 15 caracteres alfanuméricos
    private static final Pattern INE_PATTERN = Pattern.compile("^[A-Za-z0-9]{15}$");
    
    // Competência: formato AAAAMM (ano + mês)
    private static final Pattern COMPETENCIA_PATTERN = Pattern.compile("^\\d{4}(0[1-9]|1[0-2])$");

    /**
     * Valida formato CNES (7 dígitos numéricos).
     * 
     * @param cnes Código CNES a validar
     * @throws BadRequestException se o formato for inválido
     */
    public static void validarCnes(String cnes) {
        if (cnes == null || cnes.isBlank()) {
            throw new BadRequestException("CNES não pode ser vazio");
        }
        if (!CNES_PATTERN.matcher(cnes.trim()).matches()) {
            throw new BadRequestException("CNES deve conter exatamente 7 dígitos numéricos. Valor recebido: " + cnes);
        }
    }

    /**
     * Valida formato CNS (15 dígitos numéricos).
     * 
     * @param cns Código CNS a validar
     * @throws BadRequestException se o formato for inválido
     */
    public static void validarCns(String cns) {
        if (cns == null || cns.isBlank()) {
            throw new BadRequestException("CNS não pode ser vazio");
        }
        if (!CNS_PATTERN.matcher(cns.trim()).matches()) {
            throw new BadRequestException("CNS deve conter exatamente 15 dígitos numéricos. Valor recebido: " + cns);
        }
    }

    /**
     * Valida formato INE (15 caracteres alfanuméricos).
     * 
     * @param ine Código INE a validar
     * @throws BadRequestException se o formato for inválido
     */
    public static void validarIne(String ine) {
        if (ine == null || ine.isBlank()) {
            throw new BadRequestException("INE não pode ser vazio");
        }
        if (!INE_PATTERN.matcher(ine.trim()).matches()) {
            throw new BadRequestException("INE deve conter exatamente 15 caracteres alfanuméricos. Valor recebido: " + ine);
        }
    }

    /**
     * Valida formato competência (AAAAMM - ano + mês).
     * 
     * @param competencia Competência a validar (formato AAAAMM)
     * @throws BadRequestException se o formato for inválido
     */
    public static void validarCompetencia(String competencia) {
        if (competencia == null || competencia.isBlank()) {
            throw new BadRequestException("Competência não pode ser vazia");
        }
        if (!COMPETENCIA_PATTERN.matcher(competencia.trim()).matches()) {
            throw new BadRequestException("Competência deve estar no formato AAAAMM (ano + mês). Exemplo: 202501. Valor recebido: " + competencia);
        }
    }

    /**
     * Normaliza CNES removendo espaços e zeros à esquerda.
     * 
     * @param cnes CNES a normalizar
     * @return CNES normalizado ou null se for null ou vazio
     */
    public static String normalizarCnes(String cnes) {
        if (cnes == null || cnes.isBlank()) {
            return null;
        }
        return cnes.trim();
    }

    /**
     * Normaliza CNS removendo espaços.
     * 
     * @param cns CNS a normalizar
     * @return CNS normalizado ou null se for null ou vazio
     */
    public static String normalizarCns(String cns) {
        if (cns == null || cns.isBlank()) {
            return null;
        }
        return cns.trim();
    }

    /**
     * Normaliza INE removendo espaços e convertendo para maiúsculo.
     * 
     * @param ine INE a normalizar
     * @return INE normalizado ou null se for null ou vazio
     */
    public static String normalizarIne(String ine) {
        if (ine == null || ine.isBlank()) {
            return null;
        }
        return ine.trim().toUpperCase();
    }

    /**
     * Normaliza competência removendo espaços.
     * 
     * @param competencia Competência a normalizar
     * @return Competência normalizada ou null se for null ou vazia
     */
    public static String normalizarCompetencia(String competencia) {
        if (competencia == null || competencia.isBlank()) {
            return null;
        }
        return competencia.trim();
    }
}

