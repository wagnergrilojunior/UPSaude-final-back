package com.upsaude.mapper;

import com.upsaude.api.request.EnderecoRequest;
import com.upsaude.api.response.EnderecoResponse;
import com.upsaude.dto.EnderecoDTO;
import com.upsaude.entity.Cidades;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.Estados;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface EnderecoMapper extends EntityMapper<Endereco, EnderecoDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estado", source = "estadoId", qualifiedByName = "estadoFromId")
    @Mapping(target = "cidade", source = "cidadeId", qualifiedByName = "cidadeFromId")
    Endereco toEntity(EnderecoDTO dto);

    @Mapping(target = "estadoId", source = "estado.id")
    @Mapping(target = "cidadeId", source = "cidade.id")
    EnderecoDTO toDTO(Endereco entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estado", source = "estadoId", qualifiedByName = "estadoFromId")
    @Mapping(target = "cidade", source = "cidadeId", qualifiedByName = "cidadeFromId")
    Endereco fromRequest(EnderecoRequest request);

    @Mapping(target = "estadoId", source = "estado.id")
    @Mapping(target = "cidadeId", source = "cidade.id")
    EnderecoResponse toResponse(Endereco entity);

    @Named("estadoFromId")
    default Estados estadoFromId(UUID id) {
        if (id == null) return null;
        Estados e = new Estados();
        e.setId(id);
        return e;
    }

    @Named("cidadeFromId")
    default Cidades cidadeFromId(UUID id) {
        if (id == null) return null;
        Cidades c = new Cidades();
        c.setId(id);
        return c;
    }
}

