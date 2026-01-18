package com.upsaude.importacao.sia.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class SiaPaCsvParser {

    private static final String QUOTE = "\"";

    
    public Map<String, String> parseLine(String linha, String[] headers) {
        Map<String, String> campos = new HashMap<>();

        if (linha == null || linha.isEmpty()) {
            log.debug("Linha vazia recebida para parsing");
            return campos;
        }

        if (headers == null || headers.length == 0) {
            log.warn("Headers não fornecidos para parsing");
            return campos;
        }

        String[] valores = parseCsvLine(linha);

        for (int i = 0; i < headers.length; i++) {
            String header = limparHeader(headers[i]);
            String valor = null;

            if (i < valores.length) {
                valor = valores[i];
                
                if (valor != null) {
                    
                    if (valor.startsWith(QUOTE) && valor.endsWith(QUOTE) && valor.length() >= 2) {
                        valor = valor.substring(1, valor.length() - 1);
                    }
                    
                    valor = valor.trim();
                    
                    
                    if (valor.isEmpty() || "NA".equalsIgnoreCase(valor)) {
                        valor = null;
                    }
                }
            }

            campos.put(header, valor);
        }

        return campos;
    }

    
    private String[] parseCsvLine(String linha) {
        if (linha == null || linha.isEmpty()) {
            return new String[0];
        }

        java.util.List<String> campos = new java.util.ArrayList<>();
        StringBuilder campoAtual = new StringBuilder();
        boolean dentroAspas = false;

        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);

            if (c == '"') {
                if (dentroAspas && i + 1 < linha.length() && linha.charAt(i + 1) == '"') {
                    
                    campoAtual.append('"');
                    i++; 
                } else {
                    
                    dentroAspas = !dentroAspas;
                }
            } else if (c == ',' && !dentroAspas) {
                
                campos.add(campoAtual.toString());
                campoAtual = new StringBuilder();
            } else {
                campoAtual.append(c);
            }
        }

        
        campos.add(campoAtual.toString());

        return campos.toArray(new String[0]);
    }

    
    private String limparHeader(String header) {
        if (header == null) {
            return null;
        }
        
        String limpo = header.trim();
        
        
        if (limpo.startsWith(QUOTE) && limpo.endsWith(QUOTE) && limpo.length() >= 2) {
            limpo = limpo.substring(1, limpo.length() - 1);
        }
        
        return limpo.trim();
    }
}

