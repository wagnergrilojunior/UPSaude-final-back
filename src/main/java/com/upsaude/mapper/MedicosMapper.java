package com.upsaude.mapper;

import com.upsaude.api.request.MedicosRequest;
import com.upsaude.api.response.MedicosResponse;
import com.upsaude.dto.MedicosDTO;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Medicos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface MedicosMapper extends EntityMapper<Medicos, MedicosDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "enderecos", source = "enderecosIds", qualifiedByName = "enderecosFromIds")
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "especialidade", source = "especialidadeId", qualifiedByName = "especialidadeFromId")
    Medicos toEntity(MedicosDTO dto);

    @Mapping(target = "enderecosIds", source = "enderecos", qualifiedByName = "idsFromEnderecos")
    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "especialidadeId", source = "especialidade.id")
    MedicosDTO toDTO(Medicos entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecos", source = "enderecosIds", qualifiedByName = "enderecosFromIds")
    @Mapping(target = "estabelecimento", source = "estabelecimentoId", qualifiedByName = "estabelecimentoFromId")
    @Mapping(target = "especialidade", source = "especialidadeId", qualifiedByName = "especialidadeFromId")
    Medicos fromRequest(MedicosRequest request);

    @Mapping(target = "enderecosIds", source = "enderecos", qualifiedByName = "idsFromEnderecos")
    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "estabelecimentoNome", source = "estabelecimento.nome")
    @Mapping(target = "especialidadeId", source = "especialidade.id")
    @Mapping(target = "especialidadeNome", source = "especialidade.nome")
    MedicosResponse toResponse(Medicos entity);

    @Named("estabelecimentoFromId")
    default Estabelecimentos estabelecimentoFromId(UUID id) {
        if (id == null) return null;
        Estabelecimentos e = new Estabelecimentos();
        e.setId(id);
        return e;
    }

    @Named("especialidadeFromId")
    default EspecialidadesMedicas especialidadeFromId(UUID id) {
        if (id == null) return null;
        EspecialidadesMedicas em = new EspecialidadesMedicas();
        em.setId(id);
        return em;
    }

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
