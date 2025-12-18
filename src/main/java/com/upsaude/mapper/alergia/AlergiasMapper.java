package com.upsaude.mapper.alergia;

import com.upsaude.api.request.alergia.AlergiasRequest;
import com.upsaude.api.response.alergia.AlergiasResponse;
import com.upsaude.dto.AlergiasDTO;
import com.upsaude.entity.alergia.Alergias;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.embeddable.ClassificacaoAlergiaMapper;
import com.upsaude.mapper.embeddable.PrevencaoTratamentoAlergiaMapper;
import com.upsaude.mapper.embeddable.ReacoesAlergiaMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {ClassificacaoAlergiaMapper.class, ReacoesAlergiaMapper.class, PrevencaoTratamentoAlergiaMapper.class})
public interface AlergiasMapper extends EntityMapper<Alergias, AlergiasDTO> {

    @Mapping(target = "active", ignore = true)
    Alergias toEntity(AlergiasDTO dto);

    AlergiasDTO toDTO(Alergias entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Alergias fromRequest(AlergiasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(AlergiasRequest request, @MappingTarget Alergias entity);

    AlergiasResponse toResponse(Alergias entity);
}
