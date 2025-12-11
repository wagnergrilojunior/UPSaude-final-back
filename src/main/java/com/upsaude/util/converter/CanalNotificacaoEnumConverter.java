package com.upsaude.util.converter;

import com.upsaude.enums.CanalNotificacaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CanalNotificacaoEnumConverter implements AttributeConverter<CanalNotificacaoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CanalNotificacaoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public CanalNotificacaoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return CanalNotificacaoEnum.fromCodigo(dbData);
    }
}
