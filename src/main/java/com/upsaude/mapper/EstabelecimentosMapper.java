package com.upsaude.mapper;

import com.upsaude.api.request.EstabelecimentosRequest;
import com.upsaude.api.response.EstabelecimentosResponse;
import com.upsaude.dto.EstabelecimentosDTO;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface EstabelecimentosMapper extends EntityMapper<Estabelecimentos, EstabelecimentosDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "enderecos", source = "enderecosIds", qualifiedByName = "enderecosFromIds")
    Estabelecimentos toEntity(EstabelecimentosDTO dto);

    @Mapping(target = "enderecosIds", source = "enderecos", qualifiedByName = "idsFromEnderecos")
    EstabelecimentosDTO toDTO(Estabelecimentos entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecos", source = "enderecosIds", qualifiedByName = "enderecosFromIds")
    Estabelecimentos fromRequest(EstabelecimentosRequest request);

    @Mapping(target = "enderecosIds", source = "enderecos", qualifiedByName = "idsFromEnderecos")
    EstabelecimentosResponse toResponse(Estabelecimentos entity);

    @Named("enderecosFromIds")
    default List<Endereco> enderecosFromIds(List<UUID> ids) {
        if (ids == null) return new ArrayList<>();
        List<Endereco> list = new ArrayList<>();
        for (UUID id : ids) {
            Endereco end = new Endereco();
            end.setId(id);
            list.add(end);
        }
        return list;
    }

    @Named("idsFromEnderecos")
    default List<UUID> idsFromEnderecos(List<Endereco> enderecos) {
        if (enderecos == null) return new ArrayList<>();
        List<UUID> ids = new ArrayList<>();
        for (Endereco e : enderecos) {
            ids.add(e.getId());
        }
        return ids;
    }
}

