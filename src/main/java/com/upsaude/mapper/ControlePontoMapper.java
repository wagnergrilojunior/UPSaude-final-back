package com.upsaude.mapper;

import com.upsaude.api.request.ControlePontoRequest;
import com.upsaude.api.response.ControlePontoResponse;
import com.upsaude.dto.ControlePontoDTO;
import com.upsaude.entity.ControlePonto;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

/**
 * Mapper para conversão entre entidades, DTOs, Requests e Responses de ControlePonto.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface ControlePontoMapper extends EntityMapper<ControlePonto, ControlePontoDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "profissional", source = "profissionalId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "medico", source = "medicoId", qualifiedByName = "medicoFromId")
    ControlePonto toEntity(ControlePontoDTO dto);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "profissionalId", source = "profissional.id")
    @Mapping(target = "medicoId", source = "medico.id")
    ControlePontoDTO toDTO(ControlePonto entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "profissional", source = "profissionalId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "medico", source = "medicoId", qualifiedByName = "medicoFromId")
    ControlePonto fromRequest(ControlePontoRequest request);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "estabelecimentoNome", source = "estabelecimento.nome")
    @Mapping(target = "profissionalId", source = "profissional.id")
    @Mapping(target = "profissionalNome", source = "profissional.nomeCompleto")
    @Mapping(target = "medicoId", source = "medico.id")
    @Mapping(target = "medicoNome", source = "medico.nomeCompleto")
    @Mapping(target = "tipoPontoDescricao", source = "tipoPonto.descricao")
    ControlePontoResponse toResponse(ControlePonto entity);

    @Named("estabelecimentoFromId")
    default Estabelecimentos estabelecimentoFromId(UUID id) {
        if (id == null) return null;
        Estabelecimentos e = new Estabelecimentos();
        e.setId(id);
        return e;
    }

    @Named("profissionalFromId")
    default ProfissionaisSaude profissionalFromId(UUID id) {
        if (id == null) return null;
        ProfissionaisSaude p = new ProfissionaisSaude();
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
}

