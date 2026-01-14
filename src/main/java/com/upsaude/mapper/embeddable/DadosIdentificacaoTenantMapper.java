package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosIdentificacaoTenantRequest;
import com.upsaude.api.response.embeddable.DadosIdentificacaoTenantResponse;
import com.upsaude.entity.embeddable.DadosIdentificacaoTenant;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.validation.util.DocumentoUtil;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosIdentificacaoTenantMapper {

    /**
     * Converte request para entidade, normalizando o CNPJ removendo formatação
     */
    default DadosIdentificacaoTenant fromRequest(DadosIdentificacaoTenantRequest request) {
        if (request == null) {
            return null;
        }
        
        DadosIdentificacaoTenant entity = new DadosIdentificacaoTenant();
        entity.setNome(request.getNome());
        entity.setDescricao(request.getDescricao());
        entity.setUrlLogo(request.getUrlLogo());
        
        // Normaliza CNPJ removendo formatação (pontos, barra e hífen)
        if (request.getCnpj() != null && !request.getCnpj().trim().isEmpty()) {
            String cnpjNormalizado = DocumentoUtil.somenteDigitos(request.getCnpj());
            entity.setCnpj(cnpjNormalizado);
        }
        
        return entity;
    }

    DadosIdentificacaoTenantResponse toResponse(DadosIdentificacaoTenant entity);

    /**
     * Atualiza a entidade embeddada preservando valores existentes quando o request não fornece esses valores
     */
    default void updateFromRequest(DadosIdentificacaoTenantRequest request, @MappingTarget DadosIdentificacaoTenant entity) {
        if (request == null) {
            return;
        }
        
        // Preservar valores existentes se o request não fornecer
        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
            entity.setNome(request.getNome());
        }
        
        if (request.getDescricao() != null) {
            entity.setDescricao(request.getDescricao());
        }
        
        if (request.getUrlLogo() != null) {
            entity.setUrlLogo(request.getUrlLogo());
        }
        
        if (request.getCnpj() != null && !request.getCnpj().trim().isEmpty()) {
            // Normaliza CNPJ removendo formatação (pontos, barra e hífen)
            String cnpjNormalizado = DocumentoUtil.somenteDigitos(request.getCnpj());
            entity.setCnpj(cnpjNormalizado);
        }
    }
}
