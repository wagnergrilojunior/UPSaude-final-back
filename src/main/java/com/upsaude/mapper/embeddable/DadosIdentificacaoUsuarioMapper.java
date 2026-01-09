package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.DadosIdentificacaoUsuarioRequest;
import com.upsaude.api.response.embeddable.DadosIdentificacaoUsuarioResponse;
import com.upsaude.entity.embeddable.DadosIdentificacaoUsuario;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.validation.util.DocumentoUtil;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface DadosIdentificacaoUsuarioMapper {

    /**
     * Converte request para entidade, normalizando o CPF removendo formatação
     */
    default DadosIdentificacaoUsuario fromRequest(DadosIdentificacaoUsuarioRequest request) {
        if (request == null) {
            return null;
        }
        
        DadosIdentificacaoUsuario entity = new DadosIdentificacaoUsuario();
        entity.setUsername(request.getUsername());
        
        // Normaliza CPF removendo formatação (pontos e hífen)
        if (request.getCpf() != null && !request.getCpf().trim().isEmpty()) {
            String cpfNormalizado = DocumentoUtil.somenteDigitos(request.getCpf());
            entity.setCpf(cpfNormalizado);
        }
        
        return entity;
    }

    DadosIdentificacaoUsuarioResponse toResponse(DadosIdentificacaoUsuario entity);

    /**
     * Atualiza a entidade embeddada preservando valores existentes quando o request não fornece esses valores
     */
    default void updateFromRequest(DadosIdentificacaoUsuarioRequest request, @MappingTarget DadosIdentificacaoUsuario entity) {
        if (request == null) {
            return;
        }
        
        // Preservar valores existentes se o request não fornecer
        if (request.getUsername() != null && !request.getUsername().trim().isEmpty()) {
            entity.setUsername(request.getUsername());
        }
        
        if (request.getCpf() != null && !request.getCpf().trim().isEmpty()) {
            // Normaliza CPF removendo formatação (pontos e hífen)
            String cpfNormalizado = DocumentoUtil.somenteDigitos(request.getCpf());
            entity.setCpf(cpfNormalizado);
        }
    }
}
