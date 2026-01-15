package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.LogFinanceiroRequest;
import com.upsaude.api.response.financeiro.LogFinanceiroResponse;
import com.upsaude.api.response.sistema.usuario.UsuarioSistemaSimplificadoResponse;
import com.upsaude.entity.financeiro.LogFinanceiro;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = { UsuarioSistemaMapper.class })
public interface LogFinanceiroMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    LogFinanceiro fromRequest(LogFinanceiroRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateFromRequest(LogFinanceiroRequest request, @MappingTarget LogFinanceiro entity);

    @Mapping(target = "criadoEm", source = "createdAt")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "mapUsuarioSistemaSimplificado")
    LogFinanceiroResponse toResponse(LogFinanceiro entity);

}

