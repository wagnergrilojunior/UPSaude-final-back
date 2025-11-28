package com.upsaude.mapper;

import com.upsaude.api.request.DispensacoesMedicamentosRequest;
import com.upsaude.api.response.DispensacoesMedicamentosResponse;
import com.upsaude.dto.DispensacoesMedicamentosDTO;
import com.upsaude.entity.DispensacoesMedicamentos;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Medicacao;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface DispensacoesMedicamentosMapper extends EntityMapper<DispensacoesMedicamentos, DispensacoesMedicamentosDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "medicacao", source = "medicamentoId", qualifiedByName = "medicacaoFromId")
    DispensacoesMedicamentos toEntity(DispensacoesMedicamentosDTO dto);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "medicamentoId", source = "medicacao.id")
    DispensacoesMedicamentosDTO toDTO(DispensacoesMedicamentos entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "medicacao", source = "medicamentoId", qualifiedByName = "medicacaoFromId")
    DispensacoesMedicamentos fromRequest(DispensacoesMedicamentosRequest request);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "estabelecimentoNome", source = "estabelecimento.nome")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "pacienteNome", source = "paciente.nomeCompleto")
    @Mapping(target = "medicamentoId", source = "medicacao.id")
    @Mapping(target = "medicamentoNome", source = "medicacao.identificacao.nomeComercial")
    DispensacoesMedicamentosResponse toResponse(DispensacoesMedicamentos entity);

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

    @Named("medicacaoFromId")
    default Medicacao medicacaoFromId(UUID id) {
        if (id == null) return null;
        Medicacao m = new Medicacao();
        m.setId(id);
        return m;
    }
}

