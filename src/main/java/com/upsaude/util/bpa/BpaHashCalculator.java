package com.upsaude.util.bpa;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Calculadora de hash para validação de integridade dos dados BPA.
 */
@Slf4j
public class BpaHashCalculator {
    
    private static final String HASH_ALGORITHM = "SHA-256";
    
    /**
     * Calcula hash SHA-256 de uma string (conteúdo do arquivo BPA).
     */
    public static String calcularHash(String conteudo) {
        if (conteudo == null || conteudo.isEmpty()) {
            return "";
        }
        
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashBytes = digest.digest(conteudo.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("Algoritmo de hash não disponível: {}", HASH_ALGORITHM, e);
            throw new RuntimeException("Erro ao calcular hash", e);
        }
    }
    
    /**
     * Calcula hash SHA-256 de um array de bytes.
     */
    public static String calcularHash(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashBytes = digest.digest(bytes);
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("Algoritmo de hash não disponível: {}", HASH_ALGORITHM, e);
            throw new RuntimeException("Erro ao calcular hash", e);
        }
    }
    
    /**
     * Calcula hash combinado de múltiplas strings (para snapshot hash).
     */
    public static String calcularHashCombinado(String... valores) {
        if (valores == null || valores.length == 0) {
            return "";
        }
        
        StringBuilder combined = new StringBuilder();
        for (String valor : valores) {
            if (valor != null) {
                combined.append(valor);
            }
        }
        
        return calcularHash(combined.toString());
    }
    
    /**
     * Converte array de bytes para string hexadecimal.
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    /**
     * Valida se dois hashes são iguais.
     */
    public static boolean validarHash(String hash1, String hash2) {
        if (hash1 == null || hash2 == null) {
            return false;
        }
        return hash1.equalsIgnoreCase(hash2);
    }
}
