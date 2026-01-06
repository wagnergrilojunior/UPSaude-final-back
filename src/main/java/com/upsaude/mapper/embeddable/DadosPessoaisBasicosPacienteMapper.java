package com.upsaude.mapper.embeddable;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.upsaude.api.response.embeddable.DadosPessoaisBasicosPacienteResponse;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface DadosPessoaisBasicosPacienteMapper {
    DadosPessoaisBasicosPacienteMapper INSTANCE = Mappers.getMapper(DadosPessoaisBasicosPacienteMapper.class);

    default DadosPessoaisBasicosPacienteResponse toResponse(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        return DadosPessoaisBasicosPacienteResponse.builder()
                .nomeCompleto(paciente.getNomeCompleto())
                .nomeSocial(paciente.getNomeSocial())
                .dataNascimento(paciente.getDataNascimento())
                .sexo(paciente.getSexo())
                .build();
    }
}

