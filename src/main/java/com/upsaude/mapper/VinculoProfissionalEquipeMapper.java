package com.upsaude.mapper;

import com.upsaude.api.request.VinculoProfissionalEquipeRequest;
import com.upsaude.api.response.VinculoProfissionalEquipeResponse;
import com.upsaude.dto.VinculoProfissionalEquipeDTO;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.VinculoProfissionalEquipe;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface VinculoProfissionalEquipeMapper extends EntityMapper<VinculoProfissionalEquipe, VinculoProfissionalEquipeDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "profissional", source = "profissionalId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "equipe", source = "equipeId", qualifiedByName = "equipeFromId")
    VinculoProfissionalEquipe toEntity(VinculoProfissionalEquipeDTO dto);

    @Mapping(target = "profissionalId", source = "profissional.id")
    @Mapping(target = "equipeId", source = "equipe.id")
    VinculoProfissionalEquipeDTO toDTO(VinculoProfissionalEquipe entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "profissional", source = "profissionalId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "equipe", source = "equipeId", qualifiedByName = "equipeFromId")
    VinculoProfissionalEquipe fromRequest(VinculoProfissionalEquipeRequest request);

    @Mapping(target = "profissionalId", source = "profissional.id")
    @Mapping(target = "profissionalNome", source = "profissional.nomeCompleto")
    @Mapping(target = "equipeId", source = "equipe.id")
    @Mapping(target = "equipeNomeReferencia", source = "equipe.nomeReferencia")
    @Mapping(target = "equipeIne", source = "equipe.ine")
    @Mapping(target = "estabelecimentoId", source = "equipe.estabelecimento.id")
    @Mapping(target = "estabelecimentoNome", source = "equipe.estabelecimento.nome")
    VinculoProfissionalEquipeResponse toResponse(VinculoProfissionalEquipe entity);

    @Named("profissionalFromId")
    default ProfissionaisSaude profissionalFromId(UUID id) {
        if (id == null) return null;
        ProfissionaisSaude p = new ProfissionaisSaude();
        p.setId(id);
        return p;
    }

    @Named("equipeFromId")
    default EquipeSaude equipeFromId(UUID id) {
        if (id == null) return null;
        EquipeSaude e = new EquipeSaude();
        e.setId(id);
        return e;
    }
}

