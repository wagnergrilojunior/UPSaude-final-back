package com.upsaude.mapper;

import com.upsaude.api.request.ConvenioRequest;
import com.upsaude.api.response.ConvenioResponse;
import com.upsaude.dto.ConvenioDTO;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.Endereco;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface ConvenioMapper extends EntityMapper<Convenio, ConvenioDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "endereco", source = "enderecoId", qualifiedByName = "enderecoFromId")
    Convenio toEntity(ConvenioDTO dto);

    @Mapping(target = "enderecoId", source = "endereco.id")
    ConvenioDTO toDTO(Convenio entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", source = "enderecoId", qualifiedByName = "enderecoFromId")
    Convenio fromRequest(ConvenioRequest request);

    @Mapping(target = "enderecoId", source = "endereco.id")
    @Mapping(target = "enderecoCompleto", source = "endereco", qualifiedByName = "enderecoCompleto")
    ConvenioResponse toResponse(Convenio entity);

    @Named("enderecoFromId")
    default Endereco enderecoFromId(UUID id) {
        if (id == null) return null;
        Endereco e = new Endereco();
        e.setId(id);
        return e;
    }

    @Named("enderecoCompleto")
    default String enderecoCompleto(Endereco endereco) {
        if (endereco == null) return null;
        // Construir endereço completo para exibição
        StringBuilder sb = new StringBuilder();
        if (endereco.getTipoLogradouro() != null) {
            sb.append(endereco.getTipoLogradouro().getDescricao()).append(" ");
        }
        if (endereco.getLogradouro() != null) {
            sb.append(endereco.getLogradouro());
        }
        if (endereco.getNumero() != null) {
            sb.append(", ").append(endereco.getNumero());
        }
        if (endereco.getComplemento() != null) {
            sb.append(" - ").append(endereco.getComplemento());
        }
        if (endereco.getBairro() != null) {
            sb.append(", ").append(endereco.getBairro());
        }
        if (endereco.getCep() != null) {
            sb.append(" - CEP: ").append(endereco.getCep());
        }
        return sb.length() > 0 ? sb.toString() : null;
    }
}

