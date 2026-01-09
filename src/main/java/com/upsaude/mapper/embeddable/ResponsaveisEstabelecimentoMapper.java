package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ResponsaveisEstabelecimentoRequest;
import com.upsaude.api.response.embeddable.ResponsaveisEstabelecimentoResponse;
import com.upsaude.entity.embeddable.ResponsaveisEstabelecimento;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {ProfissionaisSaudeMapper.class})
public interface ResponsaveisEstabelecimentoMapper {
    @Mapping(target = "responsavelTecnico", ignore = true)
    @Mapping(target = "responsavelAdministrativo", ignore = true)
    ResponsaveisEstabelecimento toEntity(ResponsaveisEstabelecimentoRequest request);
    ResponsaveisEstabelecimentoResponse toResponse(ResponsaveisEstabelecimento entity);
    @Mapping(target = "responsavelTecnico", ignore = true)
    @Mapping(target = "responsavelAdministrativo", ignore = true)
    void updateFromRequest(ResponsaveisEstabelecimentoRequest request, @MappingTarget ResponsaveisEstabelecimento entity);
}
