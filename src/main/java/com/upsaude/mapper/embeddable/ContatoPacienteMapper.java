package com.upsaude.mapper.embeddable;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.upsaude.api.response.embeddable.ContatoPacienteResponse;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface ContatoPacienteMapper {
    ContatoPacienteMapper INSTANCE = Mappers.getMapper(ContatoPacienteMapper.class);

    default ContatoPacienteResponse toResponse(Paciente paciente) {
        if (paciente == null || paciente.getContatos() == null) {
            return null;
        }

        String telefone = paciente.getContatos().stream()
                .filter(c -> c.getTipo() == TipoContatoEnum.TELEFONE)
                .map(c -> c.getTelefone())
                .filter(t -> t != null && !t.trim().isEmpty())
                .findFirst().orElse(null);

        String celular = paciente.getContatos().stream()
                .map(c -> c.getCelular())
                .filter(t -> t != null && !t.trim().isEmpty())
                .findFirst().orElse(null);

        String email = paciente.getContatos().stream()
                .filter(c -> c.getTipo() == TipoContatoEnum.EMAIL)
                .map(c -> c.getEmail())
                .filter(e -> e != null && !e.trim().isEmpty())
                .findFirst().orElse(null);

        if (telefone == null && celular == null && email == null) {
            return null;
        }

        return ContatoPacienteResponse.builder()
                .telefone(telefone)
                .celular(celular)
                .email(email)
                .build();
    }
}

