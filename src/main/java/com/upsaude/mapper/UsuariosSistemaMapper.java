package com.upsaude.mapper;

import com.upsaude.api.request.UsuariosSistemaRequest;
import com.upsaude.api.response.UsuariosSistemaResponse;
import com.upsaude.dto.UsuariosSistemaDTO;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.entity.UsuarioEstabelecimento;
import com.upsaude.entity.UsuariosSistema;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(config = MappingConfig.class)
public interface UsuariosSistemaMapper extends EntityMapper<UsuariosSistema, UsuariosSistemaDTO> {

    @Mapping(target = "tenant", source = "tenantId", qualifiedByName = "tenantFromId")
    @Mapping(target = "estabelecimentosVinculados", ignore = true) // Ignorar ao mapear de DTO para Entity
    @Mapping(target = "profissionalSaude", source = "profissionalSaudeId", qualifiedByName = "profissionalSaudeFromId")
    @Mapping(target = "medico", source = "medicoId", qualifiedByName = "medicoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    UsuariosSistema toEntity(UsuariosSistemaDTO dto);

    @Mapping(target = "tenantId", source = "tenant.id")
    @Mapping(target = "estabelecimentosIds", source = "estabelecimentosVinculados", qualifiedByName = "idsFromEstabelecimentosVinculados")
    @Mapping(target = "profissionalSaudeId", source = "profissionalSaude.id")
    @Mapping(target = "medicoId", source = "medico.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    UsuariosSistemaDTO toDTO(UsuariosSistema entity);

    @Mapping(target = "tenant", source = "tenantId", qualifiedByName = "tenantFromId")
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimentosVinculados", ignore = true) // Ignorar ao mapear de Request para Entity
    @Mapping(target = "profissionalSaude", source = "profissionalSaudeId", qualifiedByName = "profissionalSaudeFromId")
    @Mapping(target = "medico", source = "medicoId", qualifiedByName = "medicoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    UsuariosSistema fromRequest(UsuariosSistemaRequest request);

    @Mapping(target = "tenantId", source = "tenant.id")
    @Mapping(target = "tenantNome", source = "tenant.nome")
    @Mapping(target = "tenantSlug", source = "tenant.slug")
    @Mapping(target = "estabelecimentosIds", source = "estabelecimentosVinculados", qualifiedByName = "idsFromEstabelecimentosVinculados")
    @Mapping(target = "estabelecimentosNomes", source = "estabelecimentosVinculados", qualifiedByName = "nomesFromEstabelecimentosVinculados")
    @Mapping(target = "profissionalSaudeId", source = "profissionalSaude.id")
    @Mapping(target = "medicoId", source = "medico.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    UsuariosSistemaResponse toResponse(UsuariosSistema entity);

    @Named("idsFromEstabelecimentosVinculados")
    default List<UUID> idsFromEstabelecimentosVinculados(List<UsuarioEstabelecimento> vinculos) {
        if (vinculos == null) return new ArrayList<>();
        return vinculos.stream()
                .filter(v -> v != null && v.getActive() != null && v.getActive() && v.getEstabelecimento() != null && v.getEstabelecimento().getId() != null)
                .map(v -> v.getEstabelecimento().getId())
                .distinct()
                .collect(Collectors.toList());
    }

    @Named("nomesFromEstabelecimentosVinculados")
    default List<String> nomesFromEstabelecimentosVinculados(List<UsuarioEstabelecimento> vinculos) {
        if (vinculos == null) return new ArrayList<>();
        return vinculos.stream()
                .filter(v -> v != null && v.getActive() != null && v.getActive() && v.getEstabelecimento() != null && v.getEstabelecimento().getNome() != null)
                .map(v -> v.getEstabelecimento().getNome())
                .distinct()
                .collect(Collectors.toList());
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

    @Named("tenantFromId")
    default Tenant tenantFromId(UUID id) {
        if (id == null) return null;
        Tenant t = new Tenant();
        t.setId(id);
        return t;
    }
}

