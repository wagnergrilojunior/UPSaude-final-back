package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosIdentificacaoTenantRequest;
import com.upsaude.api.response.embeddable.DadosIdentificacaoTenantResponse;
import com.upsaude.entity.embeddable.DadosIdentificacaoTenant;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosIdentificacaoTenantMapper {
    DadosIdentificacaoTenant fromRequest(DadosIdentificacaoTenantRequest request);
    DadosIdentificacaoTenantResponse toResponse(DadosIdentificacaoTenant entity);
    void updateFromRequest(DadosIdentificacaoTenantRequest request, @MappingTarget DadosIdentificacaoTenant entity);
}
