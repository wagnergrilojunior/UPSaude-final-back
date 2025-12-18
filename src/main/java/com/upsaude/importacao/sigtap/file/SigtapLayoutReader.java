package com.upsaude.importacao.sigtap.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * L? e parseia arquivos de layout (_layout.txt) do SIGTAP.
 */
@Component
public class SigtapLayoutReader {

    private static final Logger log = LoggerFactory.getLogger(SigtapLayoutReader.class);

    /**
     * L? um arquivo de layout e retorna a defini??o.
     *
     * @param layoutFilePath Caminho do arquivo _layout.txt
     * @return Defini??o do layout
     * @throws IOException Se houver erro ao ler o arquivo
     */
    public SigtapLayoutDefinition readLayout(Path layoutFilePath) throws IOException {
        log.debug("Lendo layout do arquivo: {}", layoutFilePath);

        SigtapLayoutDefinition definition = new SigtapLayoutDefinition();
        definition.setNomeArquivo(layoutFilePath.getFileName().toString());
        definition.setCampos(new ArrayList<>());

        try (BufferedReader reader = Files.newBufferedReader(layoutFilePath, StandardCharsets.UTF_8)) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readLine()) != null) {
                // Pular cabe?alho (primeira linha)
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                // Parsear linha CSV
                String[] partes = parseCsvLine(linha);
                if (partes.length >= 5) {
                    try {
                        SigtapLayoutField field = new SigtapLayoutField();
                        field.setNome(partes[0].trim());
                        field.setTamanho(Integer.parseInt(partes[1].trim()));
                        field.setInicio(Integer.parseInt(partes[2].trim()));
                        field.setFim(Integer.parseInt(partes[3].trim()));
                        field.setTipo(partes[4].trim());

                        definition.getCampos().add(field);
                    } catch (NumberFormatException e) {
                        log.warn("Erro ao parsear campo do layout na linha: {}", linha, e);
                    }
                }
            }
        }

        log.debug("Layout lido com {} campos", definition.getCampos().size());
        return definition;
    }

    /**
     * Parseia uma linha CSV simples (sem aspas complexas).
     */
    private String[] parseCsvLine(String linha) {
        List<String> campos = new ArrayList<>();
        StringBuilder campoAtual = new StringBuilder();
        boolean dentroAspas = false;

        for (char c : linha.toCharArray()) {
            if (c == '"') {
                dentroAspas = !dentroAspas;
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
}
