package com.upsaude.mapper;

import com.upsaude.api.request.ProfissionalEstabelecimentoRequest;
import com.upsaude.api.response.ProfissionalEstabelecimentoResponse;
import com.upsaude.dto.ProfissionalEstabelecimentoDTO;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.ProfissionalEstabelecimento;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface ProfissionalEstabelecimentoMapper extends EntityMapper<ProfissionalEstabelecimento, ProfissionalEstabelecimentoDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "profissional", source = "profissionalId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    ProfissionalEstabelecimento toEntity(ProfissionalEstabelecimentoDTO dto);

    @Mapping(target = "profissionalId", source = "profissional.id")
    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    ProfissionalEstabelecimentoDTO toDTO(ProfissionalEstabelecimento entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "profissional", source = "profissionalId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    ProfissionalEstabelecimento fromRequest(ProfissionalEstabelecimentoRequest request);

    @Mapping(target = "profissionalId", source = "profissional.id")
    @Mapping(target = "profissionalNome", source = "profissional.nomeCompleto")
    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "estabelecimentoNome", source = "estabelecimento.nome")
    ProfissionalEstabelecimentoResponse toResponse(ProfissionalEstabelecimento entity);

    @Named("profissionalFromId")
    default ProfissionaisSaude profissionalFromId(UUID id) {
        if (id == null) return null;
        ProfissionaisSaude p = new ProfissionaisSaude();
        p.setId(id);
        return p;
    }

    @Named("estabelecimentoFromId")
    default Estabelecimentos estabelecimentoFromId(UUID id) {
        if (id == null) return null;
        Estabelecimentos e = new Estabelecimentos();
        e.setId(id);
        return e;
    }
}

