package com.upsaude.importacao.sigtap.file;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SigtapFileParser {

    private static final Logger log = LoggerFactory.getLogger(SigtapFileParser.class);
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    public Map<String, String> parseLine(String line, SigtapLayoutDefinition layout) {
        Map<String, String> campos = new HashMap<>();

        if (line == null || line.isEmpty()) {
            log.debug("Linha vazia recebida para parsing");
            return campos;
        }

        String linhaUtf8 = line;

        for (SigtapLayoutField field : layout.getCampos()) {
            try {
                int inicio = Math.max(0, field.getInicio() - 1);
                int fim = Math.min(field.getFim(), linhaUtf8.length());

                if (inicio < linhaUtf8.length() && inicio < fim) {
                    String valor = linhaUtf8.substring(inicio, fim).trim();

                    if (valor.isEmpty()) {
                        if ("NUMBER".equals(field.getTipo())) {
                            valor = null;
                        }
                    } else {
                        if (valor.length() > field.getTamanho()) {
                            log.debug("Campo {} excede tamanho esperado ({} > {}), truncando", 
                                    field.getNome(), valor.length(), field.getTamanho());
                            valor = valor.substring(0, field.getTamanho());
                        }
                    }

                    campos.put(field.getNome(), valor);
                } else {
                    campos.put(field.getNome(), null);
                }
            } catch (StringIndexOutOfBoundsException e) {
                log.debug("Erro ao extrair campo {} (posição {}-{}): linha muito curta (tamanho: {})", 
                        field.getNome(), field.getInicio(), field.getFim(), linhaUtf8.length());
                campos.put(field.getNome(), null);
            } catch (Exception e) {
                log.warn("Erro ao extrair campo {} da linha: {}", 
                        field.getNome(), 
                        linhaUtf8.substring(0, Math.min(100, linhaUtf8.length())), 
                        e);
                campos.put(field.getNome(), null);
            }
        }

        return campos;
    }

    public Integer parseInteger(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            String valorLimpo = valor.trim();
            valorLimpo = valorLimpo.replaceFirst("^0+", "");
            if (valorLimpo.isEmpty()) {
                valorLimpo = "0";
            }
            Integer resultado = Integer.parseInt(valorLimpo);
            
            if (Math.abs(resultado) > 999999) {
                log.debug("Valor Integer suspeito (muito grande): {}", valor);
                return null;
            }
            
            return resultado;
        } catch (NumberFormatException e) {
            log.debug("Erro ao converter para Integer: {}", valor);
            return null;
        }
    }

    public BigDecimal parseBigDecimal(String valor, boolean dividirPorCem) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            String valorLimpo = valor.trim();
            BigDecimal bd = new BigDecimal(valorLimpo);
            
            if (bd.abs().compareTo(new BigDecimal("999999999999")) > 0) {
                log.debug("Valor BigDecimal suspeito (muito grande): {}", valor);
                return null;
            }
            
            if (dividirPorCem) {
                bd = bd.divide(new BigDecimal("100"), 2, java.math.RoundingMode.HALF_UP);
            }
            
            return bd;
        } catch (NumberFormatException e) {
            log.debug("Erro ao converter para BigDecimal: {}", valor);
            return null;
        } catch (ArithmeticException e) {
            log.debug("Erro aritmético ao converter BigDecimal: {}", valor);
            return null;
        }
    }

    public Boolean parseBoolean(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        return "1".equals(valor.trim()) || "S".equalsIgnoreCase(valor.trim()) || "Y".equalsIgnoreCase(valor.trim());
    }
}
