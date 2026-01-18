package com.upsaude.importacao.cid10.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class Cid10CsvParser {

    private static final String DELIMITER = ";";

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
            String header = headers[i].trim();
            String valor = null;

            if (i < valores.length) {
                valor = valores[i].trim();
                if (valor.isEmpty()) {
                    valor = null;
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

        return linha.split(DELIMITER, -1);
    }
}
