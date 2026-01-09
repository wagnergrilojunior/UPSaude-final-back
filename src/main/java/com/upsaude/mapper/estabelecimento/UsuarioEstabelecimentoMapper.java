package com.upsaude.mapper.estabelecimento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.estabelecimento.UsuarioEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.UsuarioEstabelecimentoResponse;
import com.upsaude.entity.estabelecimento.UsuarioEstabelecimento;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class, uses = { EstabelecimentosMapper.class })
public interface UsuarioEstabelecimentoMapper {

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

    @Mapping(target = "usuario.medico", ignore = true)
    @Mapping(target = "usuario.paciente", ignore = true)
    @Mapping(target = "usuario.profissionalSaude", ignore = true)
    @Mapping(target = "usuario.usuario", ignore = true)
    @Mapping(target = "estabelecimento.responsaveis.responsavelTecnico.sigtapOcupacao", ignore = true)
    @Mapping(target = "estabelecimento.responsaveis.responsavelAdministrativo.sigtapOcupacao", ignore = true)
    UsuarioEstabelecimentoResponse toResponse(UsuarioEstabelecimento entity);
}
