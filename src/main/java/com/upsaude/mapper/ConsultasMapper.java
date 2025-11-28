package com.upsaude.mapper;

import com.upsaude.api.request.ConsultasRequest;
import com.upsaude.api.response.ConsultasResponse;
import com.upsaude.dto.ConsultasDTO;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Consultas;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface ConsultasMapper extends EntityMapper<Consultas, ConsultasDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "medico", source = "medicoId", qualifiedByName = "medicoFromId")
    @Mapping(target = "profissionalSaude", source = "profissionalSaudeId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "especialidade", source = "especialidadeId", qualifiedByName = "especialidadeFromId")
    @Mapping(target = "convenio", source = "convenioId", qualifiedByName = "convenioFromId")
    @Mapping(target = "cidPrincipal", source = "cidPrincipalId", qualifiedByName = "cidFromId")
    Consultas toEntity(ConsultasDTO dto);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "medicoId", source = "medico.id")
    @Mapping(target = "profissionalSaudeId", source = "profissionalSaude.id")
    @Mapping(target = "especialidadeId", source = "especialidade.id")
    @Mapping(target = "convenioId", source = "convenio.id")
    @Mapping(target = "cidPrincipalId", source = "cidPrincipal.id")
    ConsultasDTO toDTO(Consultas entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "medico", source = "medicoId", qualifiedByName = "medicoFromId")
    @Mapping(target = "profissionalSaude", source = "profissionalSaudeId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "especialidade", source = "especialidadeId", qualifiedByName = "especialidadeFromId")
    @Mapping(target = "convenio", source = "convenioId", qualifiedByName = "convenioFromId")
    @Mapping(target = "cidPrincipal", source = "cidPrincipalId", qualifiedByName = "cidFromId")
    Consultas fromRequest(ConsultasRequest request);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "pacienteNome", source = "paciente", qualifiedByName = "pacienteNome")
    @Mapping(target = "medicoId", source = "medico.id")
    @Mapping(target = "medicoNome", source = "medico", qualifiedByName = "medicoNome")
    @Mapping(target = "profissionalSaudeId", source = "profissionalSaude.id")
    @Mapping(target = "profissionalSaudeNome", source = "profissionalSaude", qualifiedByName = "profissionalNome")
    @Mapping(target = "especialidadeId", source = "especialidade.id")
    @Mapping(target = "especialidadeNome", source = "especialidade.nome")
    @Mapping(target = "convenioId", source = "convenio.id")
    @Mapping(target = "convenioNome", source = "convenio.nome")
    @Mapping(target = "cidPrincipalId", source = "cidPrincipal.id")
    @Mapping(target = "cidPrincipalCodigo", source = "cidPrincipal.codigo")
    @Mapping(target = "cidPrincipalDescricao", source = "cidPrincipal.descricao")
    ConsultasResponse toResponse(Consultas entity);

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }

    @Named("medicoFromId")
    default Medicos medicoFromId(UUID id) {
        if (id == null) return null;
        Medicos m = new Medicos();
        m.setId(id);
        return m;
    }

    @Named("profissionalFromId")
    default ProfissionaisSaude profissionalFromId(UUID id) {
        if (id == null) return null;
        ProfissionaisSaude p = new ProfissionaisSaude();
        p.setId(id);
        return p;
    }

    @Named("especialidadeFromId")
    default EspecialidadesMedicas especialidadeFromId(UUID id) {
        if (id == null) return null;
        EspecialidadesMedicas e = new EspecialidadesMedicas();
        e.setId(id);
        return e;
    }

    @Named("convenioFromId")
    default Convenio convenioFromId(UUID id) {
        if (id == null) return null;
        Convenio c = new Convenio();
        c.setId(id);
        return c;
    }

    @Named("cidFromId")
    default CidDoencas cidFromId(UUID id) {
        if (id == null) return null;
        CidDoencas cid = new CidDoencas();
        cid.setId(id);
        return cid;
    }

    @Named("pacienteNome")
    default String pacienteNome(Paciente paciente) {
        if (paciente == null) return null;
        return paciente.getNomeCompleto();
    }

    @Named("medicoNome")
    default String medicoNome(Medicos medico) {
        if (medico == null) return null;
        return medico.getNomeCompleto();
    }

    @Named("profissionalNome")
    default String profissionalNome(ProfissionaisSaude profissional) {
        if (profissional == null) return null;
        return profissional.getNomeCompleto();
    }
}
