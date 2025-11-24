package com.upsaude.mapper;

import com.upsaude.api.request.UsuariosSistemaRequest;
import com.upsaude.api.response.UsuariosSistemaResponse;
import com.upsaude.dto.UsuariosSistemaDTO;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.UsuariosSistema;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface UsuariosSistemaMapper extends EntityMapper<UsuariosSistema, UsuariosSistemaDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "profissionalSaude", source = "profissionalSaudeId", qualifiedByName = "profissionalSaudeFromId")
    @Mapping(target = "medico", source = "medicoId", qualifiedByName = "medicoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    UsuariosSistema toEntity(UsuariosSistemaDTO dto);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "profissionalSaudeId", source = "profissionalSaude.id")
    @Mapping(target = "medicoId", source = "medico.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    UsuariosSistemaDTO toDTO(UsuariosSistema entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "profissionalSaude", source = "profissionalSaudeId", qualifiedByName = "profissionalSaudeFromId")
    @Mapping(target = "medico", source = "medicoId", qualifiedByName = "medicoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    UsuariosSistema fromRequest(UsuariosSistemaRequest request);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "profissionalSaudeId", source = "profissionalSaude.id")
    @Mapping(target = "medicoId", source = "medico.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    UsuariosSistemaResponse toResponse(UsuariosSistema entity);

    @Named("estabelecimentoFromId")
    default Estabelecimentos estabelecimentoFromId(UUID id) {
        if (id == null) return null;
        Estabelecimentos e = new Estabelecimentos();
        e.setId(id);
        return e;
    }

    @Named("profissionalSaudeFromId")
    default ProfissionaisSaude profissionalSaudeFromId(UUID id) {
        if (id == null) return null;
        ProfissionaisSaude ps = new ProfissionaisSaude();
        ps.setId(id);
        return ps;
    }

    @Named("medicoFromId")
    default Medicos medicoFromId(UUID id) {
        if (id == null) return null;
        Medicos m = new Medicos();
        m.setId(id);
        return m;
    }

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }
}

