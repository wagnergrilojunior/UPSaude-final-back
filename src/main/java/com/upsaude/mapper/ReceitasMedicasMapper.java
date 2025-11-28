package com.upsaude.mapper;

import com.upsaude.api.request.ReceitasMedicasRequest;
import com.upsaude.api.response.ReceitasMedicasResponse;
import com.upsaude.dto.ReceitasMedicasDTO;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Medicacao;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ReceitasMedicas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface ReceitasMedicasMapper extends EntityMapper<ReceitasMedicas, ReceitasMedicasDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "medico", source = "medicoId", qualifiedByName = "medicoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "cidPrincipal", source = "cidPrincipalId", qualifiedByName = "cidFromId")
    @Mapping(target = "medicacoes", source = "medicacoesIds", qualifiedByName = "medicacoesFromIds")
    ReceitasMedicas toEntity(ReceitasMedicasDTO dto);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "medicoId", source = "medico.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "cidPrincipalId", source = "cidPrincipal.id")
    @Mapping(target = "medicacoesIds", source = "medicacoes", qualifiedByName = "idsFromMedicacoes")
    ReceitasMedicasDTO toDTO(ReceitasMedicas entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "medico", source = "medicoId", qualifiedByName = "medicoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "cidPrincipal", source = "cidPrincipalId", qualifiedByName = "cidFromId")
    @Mapping(target = "medicacoes", source = "medicacoesIds", qualifiedByName = "medicacoesFromIds")
    ReceitasMedicas fromRequest(ReceitasMedicasRequest request);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "estabelecimentoNome", source = "estabelecimento.nome")
    @Mapping(target = "medicoId", source = "medico.id")
    @Mapping(target = "medicoNome", source = "medico.nomeCompleto")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "pacienteNome", source = "paciente.nomeCompleto")
    @Mapping(target = "cidPrincipalId", source = "cidPrincipal.id")
    @Mapping(target = "cidPrincipalCodigo", source = "cidPrincipal.codigo")
    @Mapping(target = "cidPrincipalDescricao", source = "cidPrincipal.descricao")
    @Mapping(target = "medicacoesIds", source = "medicacoes", qualifiedByName = "idsFromMedicacoes")
    ReceitasMedicasResponse toResponse(ReceitasMedicas entity);

    @Named("estabelecimentoFromId")
    default Estabelecimentos estabelecimentoFromId(UUID id) {
        if (id == null) return null;
        Estabelecimentos e = new Estabelecimentos();
        e.setId(id);
        return e;
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

    @Named("cidFromId")
    default CidDoencas cidFromId(UUID id) {
        if (id == null) return null;
        CidDoencas cid = new CidDoencas();
        cid.setId(id);
        return cid;
    }

    @Named("medicacoesFromIds")
    default List<Medicacao> medicacoesFromIds(List<UUID> ids) {
        if (ids == null) return new ArrayList<>();
        List<Medicacao> list = new ArrayList<>();
        for (UUID id : ids) {
            Medicacao m = new Medicacao();
            m.setId(id);
            list.add(m);
        }
        return list;
    }

    @Named("idsFromMedicacoes")
    default List<UUID> idsFromMedicacoes(List<Medicacao> medicacoes) {
        if (medicacoes == null) return new ArrayList<>();
        List<UUID> ids = new ArrayList<>();
        for (Medicacao m : medicacoes) {
            ids.add(m.getId());
        }
        return ids;
    }
}

