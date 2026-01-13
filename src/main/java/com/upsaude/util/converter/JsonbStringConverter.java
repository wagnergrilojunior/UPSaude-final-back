package com.upsaude.util.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGobject;

import java.sql.SQLException;

@Converter(autoApply = false)
public class JsonbStringConverter implements AttributeConverter<String, PGobject> {

    @Override
    public PGobject convertToDatabaseColumn(String attribute) {
        if (attribute == null || attribute.trim().isEmpty()) {
            return null;
        }
        
        // Validar se é um JSON válido
        try {
            // Tentar parsear como JSON para validar
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            mapper.readTree(attribute);
            
            // Se for válido, criar PGobject para JSONB
            PGobject pgObject = new PGobject();
            pgObject.setType("jsonb");
            pgObject.setValue(attribute);
            return pgObject;
        } catch (Exception e) {
            // Se não for JSON válido, retornar null
            return null;
        }
    }

    @Override
    public String convertToEntityAttribute(PGobject dbData) {
        if (dbData == null) {
            return null;
        }
        
        return dbData.getValue();
    }
}
