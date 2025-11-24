package com.upsaude.mapper;

import com.upsaude.api.request.VinculosPapeisRequest;
import com.upsaude.api.response.VinculosPapeisResponse;
import com.upsaude.dto.VinculosPapeisDTO;
import com.upsaude.entity.Departamentos;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Papeis;
import com.upsaude.entity.VinculosPapeis;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface VinculosPapeisMapper extends EntityMapper<VinculosPapeis, VinculosPapeisDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "departamento", source = "departamentoId", qualifiedByName = "departamentoFromId")
    @Mapping(target = "papel", source = "papelId", qualifiedByName = "papelFromId")
    VinculosPapeis toEntity(VinculosPapeisDTO dto);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "departamentoId", source = "departamento.id")
    @Mapping(target = "papelId", source = "papel.id")
    VinculosPapeisDTO toDTO(VinculosPapeis entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "departamento", source = "departamentoId", qualifiedByName = "departamentoFromId")
    @Mapping(target = "papel", source = "papelId", qualifiedByName = "papelFromId")
    VinculosPapeis fromRequest(VinculosPapeisRequest request);

    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "departamentoId", source = "departamento.id")
    @Mapping(target = "papelId", source = "papel.id")
    VinculosPapeisResponse toResponse(VinculosPapeis entity);

    @Named("estabelecimentoFromId")
    default Estabelecimentos estabelecimentoFromId(UUID id) {
        if (id == null) return null;
        Estabelecimentos e = new Estabelecimentos();
        e.setId(id);
        return e;
    }

    @Named("departamentoFromId")
    default Departamentos departamentoFromId(UUID id) {
        if (id == null) return null;
        Departamentos d = new Departamentos();
        d.setId(id);
        return d;
    }

    @Named("papelFromId")
    default Papeis papelFromId(UUID id) {
        if (id == null) return null;
        Papeis p = new Papeis();
        p.setId(id);
        return p;
    }
}

