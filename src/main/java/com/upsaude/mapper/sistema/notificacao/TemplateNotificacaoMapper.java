package com.upsaude.mapper.sistema.notificacao;

import com.upsaude.api.request.sistema.notificacao.TemplateNotificacaoRequest;
import com.upsaude.api.response.sistema.notificacao.TemplateNotificacaoResponse;
import com.upsaude.entity.sistema.notificacao.TemplateNotificacao;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface TemplateNotificacaoMapper  {

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
