package com.upsaude.mapper;

import com.upsaude.api.request.VacinacoesRequest;
import com.upsaude.api.response.VacinacoesResponse;
import com.upsaude.dto.VacinacoesDTO;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.FabricantesVacina;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Vacinacoes;
import com.upsaude.entity.Vacinas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface VacinacoesMapper extends EntityMapper<Vacinacoes, VacinacoesDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "vacina", source = "vacinaId", qualifiedByName = "vacinaFromId")
    @Mapping(target = "fabricante", source = "fabricanteId", qualifiedByName = "fabricanteFromId")
    Vacinacoes toEntity(VacinacoesDTO dto);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "vacinaId", source = "vacina.id")
    @Mapping(target = "fabricanteId", source = "fabricante.id")
    VacinacoesDTO toDTO(Vacinacoes entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "paciente", source = "pacienteId", qualifiedByName = "pacienteFromId")
    @Mapping(target = "vacina", source = "vacinaId", qualifiedByName = "vacinaFromId")
    @Mapping(target = "fabricante", source = "fabricanteId", qualifiedByName = "fabricanteFromId")
    Vacinacoes fromRequest(VacinacoesRequest request);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "vacinaId", source = "vacina.id")
    @Mapping(target = "fabricanteId", source = "fabricante.id")
    VacinacoesResponse toResponse(Vacinacoes entity);

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

    @Named("vacinaFromId")
    default Vacinas vacinaFromId(UUID id) {
        if (id == null) return null;
        Vacinas v = new Vacinas();
        v.setId(id);
        return v;
    }

    @Named("fabricanteFromId")
    default FabricantesVacina fabricanteFromId(UUID id) {
        if (id == null) return null;
        FabricantesVacina fv = new FabricantesVacina();
        fv.setId(id);
        return fv;
    }
}

