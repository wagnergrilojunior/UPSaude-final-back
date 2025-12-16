package com.upsaude.mapper;

import com.upsaude.api.request.DoencasRequest;
import com.upsaude.api.response.DoencasResponse;
import com.upsaude.dto.DoencasDTO;
import com.upsaude.entity.Doencas;
import com.upsaude.entity.CidDoencas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {CidDoencasMapper.class, com.upsaude.mapper.embeddable.ClassificacaoDoencaMapper.class, com.upsaude.mapper.embeddable.SintomasDoencaMapper.class, com.upsaude.mapper.embeddable.TratamentoPadraoDoencaMapper.class, com.upsaude.mapper.embeddable.EpidemiologiaDoencaMapper.class})
public interface DoencasMapper extends EntityMapper<Doencas, DoencasDTO> {

    @Mapping(target = "active", ignore = true)
    Doencas toEntity(DoencasDTO dto);

    DoencasDTO toDTO(Doencas entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidPrincipal", ignore = true)
    Doencas fromRequest(DoencasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidPrincipal", ignore = true)
    void updateFromRequest(DoencasRequest request, @MappingTarget Doencas entity);

    DoencasResponse toResponse(Doencas entity);
}
