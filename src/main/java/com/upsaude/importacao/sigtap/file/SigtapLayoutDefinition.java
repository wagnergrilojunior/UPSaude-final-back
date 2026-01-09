package com.upsaude.importacao.sigtap.file;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SigtapLayoutDefinition {
    private String nomeArquivo;
    private List<SigtapLayoutField> campos = new ArrayList<>();
}
