package com.upsaude.mapper.sistema.multitenancy;

import com.upsaude.api.request.sistema.multitenancy.TenantRequest;
import com.upsaude.api.response.sistema.multitenancy.TenantResponse;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.embeddable.ContatoTenantMapper;
import com.upsaude.mapper.embeddable.DadosFiscaisTenantMapper;
import com.upsaude.mapper.embeddable.DadosIdentificacaoTenantMapper;
import com.upsaude.mapper.embeddable.InformacoesAdicionaisTenantMapper;
import com.upsaude.mapper.embeddable.ResponsavelTenantMapper;
import com.upsaude.mapper.geral.EnderecoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {
        DadosIdentificacaoTenantMapper.class,
        ContatoTenantMapper.class,
        DadosFiscaisTenantMapper.class,
        ResponsavelTenantMapper.class,
        InformacoesAdicionaisTenantMapper.class,
        EnderecoMapper.class
})
public interface TenantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    @Mapping(target = "slug", source = "slug")
    Tenant fromRequest(TenantRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    void updateFromRequest(TenantRequest request, @MappingTarget Tenant entity);

    @Mapping(target = "endereco", source = "endereco", qualifiedByName = "toResponseSimplificado")
    TenantResponse toResponse(Tenant entity);
}
