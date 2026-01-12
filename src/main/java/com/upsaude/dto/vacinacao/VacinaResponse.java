package com.upsaude.dto.vacinacao;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.upsaude.entity.vacinacao.Imunobiologico;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacinaResponse {

    private UUID id;
    private String codigoFhir;
    private String nome;
    private String nomeAbreviado;
    private String descricao;
    private String codigoSistema;
    private Boolean ativo;
    private OffsetDateTime dataSincronizacao;
    private OffsetDateTime criadoEm;
    private OffsetDateTime atualizadoEm;

    public static VacinaResponse fromEntity(Imunobiologico entity) {
        if (entity == null)
            return null;

        return VacinaResponse.builder()
                .id(entity.getId())
                .codigoFhir(entity.getCodigoFhir())
                .nome(entity.getNome())
                .nomeAbreviado(entity.getNomeAbreviado())
                .descricao(entity.getDescricao())
                .codigoSistema(entity.getCodigoSistema())
                .ativo(entity.getAtivo())
                .dataSincronizacao(entity.getDataSincronizacao())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }
}
