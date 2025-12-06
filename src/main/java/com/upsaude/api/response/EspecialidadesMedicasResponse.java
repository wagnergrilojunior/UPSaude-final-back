package com.upsaude.api.response;

import com.upsaude.api.response.embeddable.ClassificacaoEspecialidadeMedicaResponse;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadesMedicasResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String codigo;
    private String nomeCientifico;
    private ClassificacaoEspecialidadeMedicaResponse classificacao;
    private String descricao;
    private String areaAtuacaoDescricao;
    private String requisitosFormacao;
    private String observacoes;
}
