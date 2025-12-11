package com.upsaude.mapper;

import com.upsaude.api.request.TemplateNotificacaoRequest;
import com.upsaude.api.response.TemplateNotificacaoResponse;
import com.upsaude.dto.TemplateNotificacaoDTO;
import com.upsaude.entity.TemplateNotificacao;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface TemplateNotificacaoMapper extends EntityMapper<TemplateNotificacao, TemplateNotificacaoDTO> {

    @Mapping(target = "active", ignore = true)
    TemplateNotificacao toEntity(TemplateNotificacaoDTO dto);

    TemplateNotificacaoDTO toDTO(TemplateNotificacao entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    TemplateNotificacao fromRequest(TemplateNotificacaoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(TemplateNotificacaoRequest request, @MappingTarget TemplateNotificacao entity);

    TemplateNotificacaoResponse toResponse(TemplateNotificacao entity);
}
