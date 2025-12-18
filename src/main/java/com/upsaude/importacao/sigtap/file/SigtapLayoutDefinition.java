package com.upsaude.importacao.sigtap.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a defini??o completa de layout de um arquivo SIGTAP.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SigtapLayoutDefinition {
    private String nomeArquivo;
    private List<SigtapLayoutField> campos = new ArrayList<>();
}
