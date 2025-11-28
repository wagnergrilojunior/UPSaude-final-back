package com.upsaude.api.request;

import com.upsaude.entity.embeddable.ClassificacaoEspecialidadeMedica;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadesMedicasRequest {
    @NotBlank(message = "Nome da especialidade é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    private String codigo;

    @Size(max = 255, message = "Nome científico deve ter no máximo 255 caracteres")
    private String nomeCientifico;

    private ClassificacaoEspecialidadeMedica classificacao;

    private String descricao;

    private String areaAtuacaoDescricao;

    private String requisitosFormacao;

    private String observacoes;
}
