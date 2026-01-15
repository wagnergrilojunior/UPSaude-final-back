package com.upsaude.util.bpa;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Definição do layout do arquivo BPA em formato TXT de largura fixa.
 * Baseado no padrão DATASUS/SUS para Boletim de Produção Ambulatorial.
 * 
 * IMPORTANTE: Este layout é uma aproximação baseada em padrões comuns.
 * O layout exato deve ser validado com a documentação oficial do DATASUS/SUS.
 */
@Getter
@RequiredArgsConstructor
public enum BpaLayoutDefinition {
    
    // Campos principais do BPA
    CNES(1, 7, FieldType.STRING, "CNES do estabelecimento"),
    COMPETENCIA(8, 13, FieldType.STRING, "Competência no formato AAAAMM"),
    PROCEDIMENTO_SIGTAP(14, 23, FieldType.STRING, "Código do procedimento SIGTAP"),
    CID_PRINCIPAL(24, 27, FieldType.STRING, "CID-10 principal (4 caracteres)"),
    QUANTIDADE(28, 35, FieldType.NUMERIC, "Quantidade produzida"),
    VALOR_UNITARIO(36, 47, FieldType.DECIMAL, "Valor unitário (12 caracteres, 2 decimais)"),
    VALOR_TOTAL(48, 59, FieldType.DECIMAL, "Valor total (12 caracteres, 2 decimais)"),
    DATA_ATENDIMENTO(60, 67, FieldType.DATE_DDMMAAAA, "Data do atendimento (DDMMAAAA)"),
    CBO_PROFISSIONAL(68, 73, FieldType.STRING, "CBO do profissional"),
    CARATER_ATENDIMENTO(74, 75, FieldType.STRING, "Caráter do atendimento"),
    IDADE(76, 78, FieldType.NUMERIC, "Idade do paciente"),
    SEXO(79, 79, FieldType.STRING, "Sexo do paciente (M/F/I)"),
    MUNICIPIO_PACIENTE(80, 85, FieldType.STRING, "Código IBGE do município do paciente"),
    TIPO_DOCUMENTO_ORIGEM(86, 86, FieldType.STRING, "Tipo do documento origem"),
    NUMERO_DOCUMENTO_ORIGEM(87, 99, FieldType.STRING, "Número do documento origem"),
    NUMERO_LINHA(100, 107, FieldType.NUMERIC, "Número sequencial da linha");
    
    private final int startPosition; // Posição inicial (1-based)
    private final int endPosition; // Posição final (inclusive)
    private final FieldType fieldType;
    private final String description;
    
    public int getLength() {
        return endPosition - startPosition + 1;
    }
    
    public enum FieldType {
        STRING,
        NUMERIC,
        DECIMAL,
        DATE_DDMMAAAA
    }
    
    /**
     * Retorna o tamanho total da linha do BPA.
     */
    public static int getTotalLineLength() {
        return NUMERO_LINHA.getEndPosition();
    }
}
