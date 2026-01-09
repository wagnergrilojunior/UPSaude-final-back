package com.upsaude.mapper.embeddable;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.upsaude.api.response.embeddable.ResponsavelLegalPacienteResponse;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface ResponsavelLegalPacienteMapper {
    ResponsavelLegalPacienteMapper INSTANCE = Mappers.getMapper(ResponsavelLegalPacienteMapper.class);

    default ResponsavelLegalPacienteResponse toResponse(Paciente paciente) {
        if (paciente == null || paciente.getResponsavelLegal() == null) {
            return null;
        }

        var responsavel = paciente.getResponsavelLegal();

        return ResponsavelLegalPacienteResponse.builder()
                .nome(responsavel.getNome())
                .cpf(responsavel.getCpf())
                .telefone(responsavel.getTelefone())
                .build();
    }
}

