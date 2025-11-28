package com.upsaude.mapper;

import com.upsaude.api.request.EstabelecimentosRequest;
import com.upsaude.api.response.EstabelecimentosResponse;
import com.upsaude.dto.EstabelecimentosDTO;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.ProfissionaisSaude;
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
    @Mapping(target = "enderecoPrincipal", source = "enderecoPrincipalId", qualifiedByName = "enderecoFromId")
    @Mapping(target = "enderecosSecundarios", source = "enderecosSecundariosIds", qualifiedByName = "enderecosFromIds")
    @Mapping(target = "responsavelTecnico", source = "responsavelTecnicoId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "responsavelAdministrativo", source = "responsavelAdministrativoId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "servicos", ignore = true)
    @Mapping(target = "equipamentos", ignore = true)
    @Mapping(target = "infraestrutura", ignore = true)
    Estabelecimentos toEntity(EstabelecimentosDTO dto);

    @Mapping(target = "enderecoPrincipalId", source = "enderecoPrincipal.id")
    @Mapping(target = "enderecosSecundariosIds", source = "enderecosSecundarios", qualifiedByName = "idsFromEnderecos")
    @Mapping(target = "responsavelTecnicoId", source = "responsavelTecnico.id")
    @Mapping(target = "responsavelAdministrativoId", source = "responsavelAdministrativo.id")
    EstabelecimentosDTO toDTO(Estabelecimentos entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecoPrincipal", source = "enderecoPrincipalId", qualifiedByName = "enderecoFromId")
    @Mapping(target = "enderecosSecundarios", source = "enderecosSecundariosIds", qualifiedByName = "enderecosFromIds")
    @Mapping(target = "responsavelTecnico", source = "responsavelTecnicoId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "responsavelAdministrativo", source = "responsavelAdministrativoId", qualifiedByName = "profissionalFromId")
    @Mapping(target = "servicos", ignore = true)
    @Mapping(target = "equipamentos", ignore = true)
    @Mapping(target = "infraestrutura", ignore = true)
    Estabelecimentos fromRequest(EstabelecimentosRequest request);

    @Mapping(target = "enderecoPrincipalId", source = "enderecoPrincipal", qualifiedByName = "enderecoId")
    @Mapping(target = "enderecoPrincipalLogradouro", source = "enderecoPrincipal.logradouro")
    @Mapping(target = "enderecoPrincipalNumero", source = "enderecoPrincipal.numero")
    @Mapping(target = "enderecoPrincipalBairro", source = "enderecoPrincipal.bairro")
    @Mapping(target = "enderecoPrincipalCep", source = "enderecoPrincipal.cep")
    @Mapping(target = "enderecoPrincipalCidade", source = "enderecoPrincipal", qualifiedByName = "cidadeNome")
    @Mapping(target = "enderecoPrincipalEstado", source = "enderecoPrincipal", qualifiedByName = "estadoSigla")
    @Mapping(target = "enderecosSecundariosIds", source = "enderecosSecundarios", qualifiedByName = "idsFromEnderecos")
    @Mapping(target = "responsavelTecnicoId", source = "responsavelTecnico", qualifiedByName = "profissionalId")
    @Mapping(target = "responsavelTecnicoNome", source = "responsavelTecnico.nomeCompleto")
    @Mapping(target = "responsavelAdministrativoId", source = "responsavelAdministrativo", qualifiedByName = "profissionalId")
    @Mapping(target = "responsavelAdministrativoNome", source = "responsavelAdministrativo.nomeCompleto")
    EstabelecimentosResponse toResponse(Estabelecimentos entity);

    @Named("enderecoFromId")
    default Endereco enderecoFromId(UUID id) {
        if (id == null) return null;
        Endereco end = new Endereco();
        end.setId(id);
        return end;
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
            if (e != null && e.getId() != null) {
                ids.add(e.getId());
            }
        }
        return ids;
    }

    @Named("profissionalFromId")
    default ProfissionaisSaude profissionalFromId(UUID id) {
        if (id == null) return null;
        ProfissionaisSaude p = new ProfissionaisSaude();
        p.setId(id);
        return p;
    }

    @Named("enderecoId")
    default UUID enderecoId(Endereco endereco) {
        return endereco != null ? endereco.getId() : null;
    }

    @Named("cidadeNome")
    default String cidadeNome(Endereco endereco) {
        return endereco != null && endereco.getCidade() != null ? endereco.getCidade().getNome() : null;
    }

    @Named("estadoSigla")
    default String estadoSigla(Endereco endereco) {
        return endereco != null && endereco.getEstado() != null ? endereco.getEstado().getSigla() : null;
    }

    @Named("profissionalId")
    default UUID profissionalId(ProfissionaisSaude profissional) {
        return profissional != null ? profissional.getId() : null;
    }
}

