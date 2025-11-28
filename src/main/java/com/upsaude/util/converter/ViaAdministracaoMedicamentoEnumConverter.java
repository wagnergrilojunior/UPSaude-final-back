package com.upsaude.util.converter;

import com.upsaude.enums.ViaAdministracaoMedicamentoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para ViaAdministracaoMedicamentoEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class ViaAdministracaoMedicamentoEnumConverter implements AttributeConverter<ViaAdministracaoMedicamentoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ViaAdministracaoMedicamentoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public ViaAdministracaoMedicamentoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return ViaAdministracaoMedicamentoEnum.fromCodigo(dbData);
    }
}

