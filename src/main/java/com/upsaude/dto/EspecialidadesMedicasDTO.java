package com.upsaude.dto;

import com.upsaude.entity.embeddable.ClassificacaoEspecialidadeMedica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadesMedicasDTO {
    private UUID id;
    private String nome;
    private String codigo;
    private String nomeCientifico;
    private ClassificacaoEspecialidadeMedica classificacao;
    private String descricao;
    private String areaAtuacaoDescricao;
    private String requisitosFormacao;
    private String observacoes;
    private Boolean active;
}
