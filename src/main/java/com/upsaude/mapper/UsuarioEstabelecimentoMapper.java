package com.upsaude.mapper;

import com.upsaude.api.request.UsuarioEstabelecimentoRequest;
import com.upsaude.api.response.UsuarioEstabelecimentoResponse;
import com.upsaude.dto.UsuarioEstabelecimentoDTO;
import com.upsaude.entity.UsuarioEstabelecimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.UsuariosSistema;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface UsuarioEstabelecimentoMapper extends EntityMapper<UsuarioEstabelecimento, UsuarioEstabelecimentoDTO> {

    @Mapping(target = "active", ignore = true)
    UsuarioEstabelecimento toEntity(UsuarioEstabelecimentoDTO dto);

    UsuarioEstabelecimentoDTO toDTO(UsuarioEstabelecimento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    UsuarioEstabelecimento fromRequest(UsuarioEstabelecimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateFromRequest(UsuarioEstabelecimentoRequest request, @MappingTarget UsuarioEstabelecimento entity);

    UsuarioEstabelecimentoResponse toResponse(UsuarioEstabelecimento entity);
}
