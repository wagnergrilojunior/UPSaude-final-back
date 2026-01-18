package com.upsaude.importacao.sigtap.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SigtapLayoutField {
    private String nome;
    private int tamanho;
    private int inicio;
    private int fim;
    private String tipo; 
}
