package com.upsaude.api.response;

import com.upsaude.entity.embeddable.ClassificacaoEspecialidadeMedica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
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
    private ClassificacaoEspecialidadeMedica classificacao;
    private String descricao;
    private String areaAtuacaoDescricao;
    private String requisitosFormacao;
    private String observacoes;
}
