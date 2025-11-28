package com.upsaude.mapper;

import com.upsaude.api.request.AtendimentoRequest;
import com.upsaude.api.response.AtendimentoResponse;
import com.upsaude.dto.AtendimentoDTO;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

/**
 * Mapper para conversão entre entidades, DTOs, Requests e Responses de Atendimento.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface AtendimentoMapper extends EntityMapper<Atendimento, AtendimentoDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "profissional", source = "profissionalId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "cidPrincipal", source = "cidPrincipalId", qualifiedByName = "cidFromId")
    Atendimento toEntity(AtendimentoDTO dto);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "profissionalId", source = "profissional.id")
    @Mapping(target = "cidPrincipalId", source = "cidPrincipal.id")
    AtendimentoDTO toDTO(Atendimento entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "profissional", source = "profissionalId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "cidPrincipal", source = "cidPrincipalId", qualifiedByName = "cidFromId")
    Atendimento fromRequest(AtendimentoRequest request);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "pacienteNome", source = "paciente.nomeCompleto")
    @Mapping(target = "profissionalId", source = "profissional.id")
    @Mapping(target = "profissionalNome", source = "profissional.nomeCompleto")
    @Mapping(target = "cidPrincipalId", source = "cidPrincipal.id")
    @Mapping(target = "cidPrincipalCodigo", source = "cidPrincipal.codigo")
    @Mapping(target = "cidPrincipalDescricao", source = "cidPrincipal.descricao")
    AtendimentoResponse toResponse(Atendimento entity);

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }

    @Named("profissionalFromId")
    default ProfissionaisSaude profissionalFromId(UUID id) {
        if (id == null) return null;
        ProfissionaisSaude p = new ProfissionaisSaude();
        p.setId(id);
        return p;
    }

    @Named("cidFromId")
    default com.upsaude.entity.CidDoencas cidFromId(UUID id) {
        if (id == null) return null;
        com.upsaude.entity.CidDoencas cid = new com.upsaude.entity.CidDoencas();
        cid.setId(id);
        return cid;
    }
}

