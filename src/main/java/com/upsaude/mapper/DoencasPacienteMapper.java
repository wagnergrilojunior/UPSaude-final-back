package com.upsaude.mapper;

import com.upsaude.api.request.DoencasPacienteRequest;
import com.upsaude.api.response.DoencasPacienteResponse;
import com.upsaude.dto.DoencasPacienteDTO;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Doencas;
import com.upsaude.entity.DoencasPaciente;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface DoencasPacienteMapper extends EntityMapper<DoencasPaciente, DoencasPacienteDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "doenca", source = "doencaId", qualifiedByName = "doencaFromId")
    @Mapping(target = "cidPrincipal", source = "cidPrincipalId", qualifiedByName = "cidFromId")
    DoencasPaciente toEntity(DoencasPacienteDTO dto);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "doencaId", source = "doenca.id")
    @Mapping(target = "cidPrincipalId", source = "cidPrincipal.id")
    DoencasPacienteDTO toDTO(DoencasPaciente entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "doenca", source = "doencaId", qualifiedByName = "doencaFromId")
    @Mapping(target = "cidPrincipal", source = "cidPrincipalId", qualifiedByName = "cidFromId")
    DoencasPaciente fromRequest(DoencasPacienteRequest request);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "pacienteNome", source = "paciente", qualifiedByName = "pacienteNome")
    @Mapping(target = "doencaId", source = "doenca.id")
    @Mapping(target = "doencaNome", source = "doenca.nome")
    @Mapping(target = "cidPrincipalId", source = "cidPrincipal.id")
    @Mapping(target = "cidPrincipalCodigo", source = "cidPrincipal.codigo")
    @Mapping(target = "cidPrincipalDescricao", source = "cidPrincipal.descricao")
    DoencasPacienteResponse toResponse(DoencasPaciente entity);

    @Named("pacienteFromId")
    default Paciente pacienteFromId(UUID id) {
        if (id == null) return null;
        Paciente p = new Paciente();
        p.setId(id);
        return p;
    }

    @Named("doencaFromId")
    default Doencas doencaFromId(UUID id) {
        if (id == null) return null;
        Doencas d = new Doencas();
        d.setId(id);
        return d;
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
        // Tenta obter o nome completo do paciente
        if (paciente.getNomeCompleto() != null) {
            return paciente.getNomeCompleto();
        }
        return null;
    }
}

