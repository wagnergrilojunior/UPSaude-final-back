package com.upsaude.mapper.embeddable;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.upsaude.api.response.embeddable.DocumentosBasicosPacienteResponse;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface DocumentosBasicosPacienteMapper {
    DocumentosBasicosPacienteMapper INSTANCE = Mappers.getMapper(DocumentosBasicosPacienteMapper.class);

    default DocumentosBasicosPacienteResponse toResponse(Paciente paciente) {
        if (paciente == null || paciente.getIdentificadores() == null) {
            return null;
        }

        String cpf = paciente.getIdentificadores().stream()
                .filter(id -> id.getTipo() == TipoIdentificadorEnum.CPF && Boolean.TRUE.equals(id.getPrincipal()))
                .map(id -> id.getValor())
                .findFirst().orElse(null);

        String rg = paciente.getIdentificadores().stream()
                .filter(id -> id.getTipo() == TipoIdentificadorEnum.RG && Boolean.TRUE.equals(id.getPrincipal()))
                .map(id -> id.getValor())
                .findFirst().orElse(null);

        String cns = paciente.getIdentificadores().stream()
                .filter(id -> id.getTipo() == TipoIdentificadorEnum.CNS && Boolean.TRUE.equals(id.getPrincipal()))
                .map(id -> id.getValor())
                .findFirst().orElse(null);

        if (cpf == null && rg == null && cns == null) {
            return null;
        }

        return DocumentosBasicosPacienteResponse.builder()
                .cpf(cpf)
                .rg(rg)
                .cns(cns)
                .build();
    }
}

