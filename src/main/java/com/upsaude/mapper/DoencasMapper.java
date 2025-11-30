package com.upsaude.mapper;

import com.upsaude.api.request.DoencasRequest;
import com.upsaude.api.response.DoencasResponse;
import com.upsaude.dto.DoencasDTO;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Doencas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface DoencasMapper extends EntityMapper<Doencas, DoencasDTO> {

    @Mapping(target = "cidPrincipal", source = "cidPrincipalId", qualifiedByName = "cidFromId")
    Doencas toEntity(DoencasDTO dto);

    @Mapping(target = "cidPrincipalId", source = "cidPrincipal.id")
    DoencasDTO toDTO(Doencas entity);

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidPrincipal", source = "cidPrincipalId", qualifiedByName = "cidFromId")
    Doencas fromRequest(DoencasRequest request);

    @Mapping(target = "cidPrincipalId", source = "cidPrincipal.id")
    @Mapping(target = "cidPrincipalCodigo", source = "cidPrincipal.codigo")
    @Mapping(target = "cidPrincipalDescricao", source = "cidPrincipal.descricao")
    DoencasResponse toResponse(Doencas entity);

    @Named("cidFromId")
    default CidDoencas cidFromId(UUID id) {
        if (id == null) return null;
        CidDoencas cid = new CidDoencas();
        cid.setId(id);
        return cid;
    }
}

