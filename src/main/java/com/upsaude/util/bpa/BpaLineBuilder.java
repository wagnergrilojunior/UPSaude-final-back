package com.upsaude.util.bpa;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Builder para criar linhas do arquivo BPA seguindo o layout de largura fixa.
 */
@Slf4j
public class BpaLineBuilder {
    
    private final char[] line;
    private static final char PADDING_CHAR = ' ';
    private static final char NUMERIC_PADDING_CHAR = '0';
    
    public BpaLineBuilder() {
        this.line = new char[BpaLayoutDefinition.getTotalLineLength()];
        // Inicializa com espaços
        for (int i = 0; i < line.length; i++) {
            line[i] = PADDING_CHAR;
        }
    }
    
    /**
     * Define o valor de um campo no layout.
     */
    public BpaLineBuilder setField(BpaLayoutDefinition field, String value) {
        if (value == null) {
            value = "";
        }
        
        String formattedValue = formatValue(field, value);
        int startIdx = field.getStartPosition() - 1; // Converter para 0-based
        int length = field.getLength();
        
        // Truncar se necessário
        if (formattedValue.length() > length) {
            formattedValue = formattedValue.substring(0, length);
            log.warn("Valor truncado para campo {}: '{}' -> '{}'", field.name(), value, formattedValue);
        }
        
        // Preencher com padding apropriado
        char paddingChar = field.getFieldType() == BpaLayoutDefinition.FieldType.NUMERIC 
                || field.getFieldType() == BpaLayoutDefinition.FieldType.DECIMAL
                ? NUMERIC_PADDING_CHAR 
                : PADDING_CHAR;
        
        // Preencher à direita para strings, à esquerda para números
        boolean leftPad = field.getFieldType() == BpaLayoutDefinition.FieldType.NUMERIC 
                || field.getFieldType() == BpaLayoutDefinition.FieldType.DECIMAL;
        
        String paddedValue = leftPad 
                ? String.format("%" + length + "s", formattedValue).replace(' ', paddingChar)
                : String.format("%-" + length + "s", formattedValue).replace(' ', paddingChar);
        
        // Copiar para o array
        for (int i = 0; i < Math.min(paddedValue.length(), length); i++) {
            line[startIdx + i] = paddedValue.charAt(i);
        }
        
        return this;
    }
    
    /**
     * Define campo numérico.
     */
    public BpaLineBuilder setNumericField(BpaLayoutDefinition field, Integer value) {
        if (value == null) {
            return setField(field, "");
        }
        return setField(field, String.valueOf(value));
    }
    
    /**
     * Define campo decimal (valor monetário).
     */
    public BpaLineBuilder setDecimalField(BpaLayoutDefinition field, BigDecimal value) {
        if (value == null) {
            return setField(field, "");
        }
        // Formatar sem separador de milhar, com 2 decimais
        String formatted = value.setScale(2, RoundingMode.HALF_UP)
                .toString()
                .replace(".", "")
                .replace(",", "");
        return setField(field, formatted);
    }
    
    /**
     * Define campo de data no formato DDMMAAAA.
     */
    public BpaLineBuilder setDateField(BpaLayoutDefinition field, OffsetDateTime dateTime) {
        if (dateTime == null) {
            return setField(field, "");
        }
        LocalDate date = dateTime.toLocalDate();
        String formatted = date.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return setField(field, formatted);
    }
    
    /**
     * Define campo de data no formato DDMMAAAA a partir de LocalDate.
     */
    public BpaLineBuilder setDateField(BpaLayoutDefinition field, LocalDate date) {
        if (date == null) {
            return setField(field, "");
        }
        String formatted = date.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return setField(field, formatted);
    }
    
    /**
     * Define número de linha sequencial.
     */
    public BpaLineBuilder setNumeroLinha(int numeroLinha) {
        return setNumericField(BpaLayoutDefinition.NUMERO_LINHA, numeroLinha);
    }
    
    /**
     * Formata valor conforme o tipo do campo.
     */
    private String formatValue(BpaLayoutDefinition field, String value) {
        if (value == null || value.trim().isEmpty()) {
            return "";
        }
        
        switch (field.getFieldType()) {
            case STRING:
                return value.toUpperCase().trim();
            case NUMERIC:
                return value.replaceAll("[^0-9]", "");
            case DECIMAL:
                return value.replaceAll("[^0-9]", "");
            case DATE_DDMMAAAA:
                return value.replaceAll("[^0-9]", "");
            default:
                return value.trim();
        }
    }
    
    /**
     * Constrói a linha final como String.
     */
    public String build() {
        return new String(line);
    }
    
    /**
     * Constrói a linha final como array de bytes (ISO-8859-1 para compatibilidade com DATASUS).
     */
    public byte[] buildAsBytes() {
        try {
            return new String(line).getBytes("ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            log.error("Erro ao converter linha para bytes ISO-8859-1", e);
            return new String(line).getBytes();
        }
    }
}
