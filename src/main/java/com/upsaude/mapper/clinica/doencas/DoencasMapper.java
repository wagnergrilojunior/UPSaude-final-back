package com.upsaude.mapper.clinica.doencas;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.doencas.DoencasRequest;
import com.upsaude.api.response.clinica.doencas.DoencasResponse;
import com.upsaude.dto.clinica.doencas.DoencasDTO;
import com.upsaude.entity.clinica.doencas.Doencas;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class, uses = {com.upsaude.mapper.embeddable.ClassificacaoDoencaMapper.class, com.upsaude.mapper.embeddable.SintomasDoencaMapper.class, com.upsaude.mapper.embeddable.TratamentoPadraoDoencaMapper.class, com.upsaude.mapper.embeddable.EpidemiologiaDoencaMapper.class})
public interface DoencasMapper extends EntityMapper<Doencas, DoencasDTO> {

    @Mapping(target = "active", ignore = true)
    Doencas toEntity(DoencasDTO dto);

    DoencasDTO toDTO(Doencas entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Doencas fromRequest(DoencasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(DoencasRequest request, @MappingTarget Doencas entity);

    DoencasResponse toResponse(Doencas entity);
}
