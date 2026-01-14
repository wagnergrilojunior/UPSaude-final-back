package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.ResponsavelTenantRequest;
import com.upsaude.api.response.embeddable.ResponsavelTenantResponse;
import com.upsaude.entity.embeddable.ResponsavelTenant;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.validation.util.DocumentoUtil;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface ResponsavelTenantMapper {

    /**
     * Converte request para entidade, normalizando o CPF removendo formatação
     */
    default ResponsavelTenant fromRequest(ResponsavelTenantRequest request) {
        if (request == null) {
            return null;
        }
        
        ResponsavelTenant entity = new ResponsavelTenant();
        entity.setResponsavelNome(request.getResponsavelNome());
        entity.setResponsavelTelefone(request.getResponsavelTelefone());
        
        // Normaliza CPF removendo formatação (pontos e hífen)
        if (request.getResponsavelCpf() != null && !request.getResponsavelCpf().trim().isEmpty()) {
            String cpfNormalizado = DocumentoUtil.somenteDigitos(request.getResponsavelCpf());
            entity.setResponsavelCpf(cpfNormalizado);
        }
        
        return entity;
    }

    ResponsavelTenantResponse toResponse(ResponsavelTenant entity);

    /**
     * Atualiza a entidade embeddada preservando valores existentes quando o request não fornece esses valores
     */
    default void updateFromRequest(ResponsavelTenantRequest request, @MappingTarget ResponsavelTenant entity) {
        if (request == null) {
            return;
        }
        
        // Preservar valores existentes se o request não fornecer
        if (request.getResponsavelNome() != null && !request.getResponsavelNome().trim().isEmpty()) {
            entity.setResponsavelNome(request.getResponsavelNome());
        }
        
        if (request.getResponsavelTelefone() != null && !request.getResponsavelTelefone().trim().isEmpty()) {
            entity.setResponsavelTelefone(request.getResponsavelTelefone());
        }
        
        if (request.getResponsavelCpf() != null && !request.getResponsavelCpf().trim().isEmpty()) {
            // Normaliza CPF removendo formatação (pontos e hífen)
            String cpfNormalizado = DocumentoUtil.somenteDigitos(request.getResponsavelCpf());
            entity.setResponsavelCpf(cpfNormalizado);
        }
    }
}
