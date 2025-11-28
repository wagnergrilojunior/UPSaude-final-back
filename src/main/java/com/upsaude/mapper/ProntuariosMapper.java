package com.upsaude.mapper;

import com.upsaude.api.request.ProntuariosRequest;
import com.upsaude.api.response.ProntuariosResponse;
import com.upsaude.dto.ProntuariosDTO;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Prontuarios;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface ProntuariosMapper extends EntityMapper<Prontuarios, ProntuariosDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    Prontuarios toEntity(ProntuariosDTO dto);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    ProntuariosDTO toDTO(Prontuarios entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    Prontuarios fromRequest(ProntuariosRequest request);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "estabelecimentoNome", source = "estabelecimento.nome")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "pacienteNome", source = "paciente.nomeCompleto")
    ProntuariosResponse toResponse(Prontuarios entity);

    @Named("estabelecimentoFromId")
    default Estabelecimentos estabelecimentoFromId(UUID id) {
        if (id == null) return null;
        Estabelecimentos e = new Estabelecimentos();
        e.setId(id);
        return e;
    }

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }
}

