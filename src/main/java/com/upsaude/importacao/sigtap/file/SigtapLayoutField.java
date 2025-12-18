package com.upsaude.importacao.sigtap.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um campo de layout de arquivo SIGTAP.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SigtapLayoutField {
    private String nome;
    private int tamanho;
    private int inicio;
    private int fim;
    private String tipo; // VARCHAR2, NUMBER, CHAR
}
