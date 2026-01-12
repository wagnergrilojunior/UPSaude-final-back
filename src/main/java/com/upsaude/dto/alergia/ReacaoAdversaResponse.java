package com.upsaude.dto.alergia;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.upsaude.entity.alergia.ReacaoAdversaCatalogo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReacaoAdversaResponse {

    private UUID id;
    private String codigoFhir;
    private String nome;
    private String categoria;
    private String codigoSistema;
    private Boolean ativo;
    private OffsetDateTime dataSincronizacao;
    private OffsetDateTime criadoEm;
    private OffsetDateTime atualizadoEm;

    public static ReacaoAdversaResponse fromEntity(ReacaoAdversaCatalogo entity) {
        if (entity == null)
            return null;

        return ReacaoAdversaResponse.builder()
                .id(entity.getId())
                .codigoFhir(entity.getCodigoFhir())
                .nome(entity.getNome())
                .categoria(entity.getCategoria())
                .codigoSistema(entity.getCodigoSistema())
                .ativo(entity.getAtivo())
                .dataSincronizacao(entity.getDataSincronizacao())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }
}
