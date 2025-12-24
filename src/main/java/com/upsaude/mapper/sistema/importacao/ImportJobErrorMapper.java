package com.upsaude.mapper.sistema.importacao;

import com.upsaude.api.response.sistema.importacao.ImportJobErrorResponse;
import com.upsaude.entity.sistema.importacao.ImportJobError;
import org.springframework.stereotype.Component;

/**
 * Mapper para converter entidades ImportJobError em DTOs de resposta.
 */
@Component
public class ImportJobErrorMapper {

    public ImportJobErrorResponse toResponse(ImportJobError error) {
        if (error == null) {
            return null;
        }

        return ImportJobErrorResponse.builder()
                .id(error.getId())
                .createdAt(error.getCreatedAt())
                .updatedAt(error.getUpdatedAt())
                .active(error.getActive())
                .jobId(error.getJob() != null ? error.getJob().getId() : null)
                .linha(error.getLinha())
                .codigoErro(error.getCodigoErro())
                .mensagem(error.getMensagem())
                .rawLineHash(error.getRawLineHash())
                .rawLinePreview(error.getRawLinePreview())
                .build();
    }
}

