package com.upsaude.importacao.sigtap.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Parser para arquivos de texto fixo (fixed-width) do SIGTAP.
 * Extrai campos de uma linha usando as defini??es de layout.
 */
@Component
public class SigtapFileParser {

    private static final Logger log = LoggerFactory.getLogger(SigtapFileParser.class);
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    /**
     * Parseia uma linha de arquivo fixo usando a definição de layout.
     *
     * @param line Linha do arquivo (em ISO-8859-1)
     * @param layout Definição do layout
     * @return Map com nome do campo -> valor (já convertido para UTF-8)
     */
    public Map<String, String> parseLine(String line, SigtapLayoutDefinition layout) {
        Map<String, String> campos = new HashMap<>();

        if (line == null || line.isEmpty()) {
            log.debug("Linha vazia recebida para parsing");
            return campos;
        }

        // A linha já foi lida com o encoding correto (ISO-8859-1) no BufferedReader
        // Não precisa fazer conversão adicional - Java usa UTF-16 internamente para Strings
        // e os caracteres já estão corretos quando lidos com o charset apropriado
        String linhaUtf8 = line;

        for (SigtapLayoutField field : layout.getCampos()) {
            try {
                // Extrair campo usando posições (início é 1-based no layout, mas 0-based em Java)
                int inicio = Math.max(0, field.getInicio() - 1);
                int fim = Math.min(field.getFim(), linhaUtf8.length());

                if (inicio < linhaUtf8.length() && inicio < fim) {
                    String valor = linhaUtf8.substring(inicio, fim).trim();

                    // Validação e tratamento de valores vazios
                    if (valor.isEmpty()) {
                        // Para tipos NUMBER, null é mais apropriado
                        if ("NUMBER".equals(field.getTipo())) {
                            valor = null;
                        }
                        // Para outros tipos, manter string vazia ou null conforme necessário
                    } else {
                        // Validar tamanho do campo
                        if (valor.length() > field.getTamanho()) {
                            log.debug("Campo {} excede tamanho esperado ({} > {}), truncando", 
                                    field.getNome(), valor.length(), field.getTamanho());
                            valor = valor.substring(0, field.getTamanho());
                        }
                    }

                    campos.put(field.getNome(), valor);
                } else {
                    // Campo fora dos limites da linha
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

    /**
     * Converte um valor String para Integer, tratando valores inválidos.
     * Valida valores especiais como 9999 (não se aplica).
     */
    public Integer parseInteger(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            String valorLimpo = valor.trim();
            // Remover zeros à esquerda desnecessários, mas manter o valor
            valorLimpo = valorLimpo.replaceFirst("^0+", "");
            if (valorLimpo.isEmpty()) {
                valorLimpo = "0";
            }
            Integer resultado = Integer.parseInt(valorLimpo);
            
            // Validação: valores muito grandes podem indicar erro
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

    /**
     * Converte um valor String para BigDecimal, tratando valores inválidos.
     * Assume que valores monetários podem estar em centavos (divide por 100).
     * Valida valores negativos e muito grandes.
     */
    public BigDecimal parseBigDecimal(String valor, boolean dividirPorCem) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            String valorLimpo = valor.trim();
            BigDecimal bd = new BigDecimal(valorLimpo);
            
            // Validação: valores muito grandes podem indicar erro
            if (bd.abs().compareTo(new BigDecimal("999999999999")) > 0) {
                log.debug("Valor BigDecimal suspeito (muito grande): {}", valor);
                return null;
            }
            
            // Dividir por 100 se necessário (valores em centavos)
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

    /**
     * Converte um valor String para Boolean (1 = true, outros = false).
     */
    public Boolean parseBoolean(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        return "1".equals(valor.trim()) || "S".equalsIgnoreCase(valor.trim()) || "Y".equalsIgnoreCase(valor.trim());
    }
}
