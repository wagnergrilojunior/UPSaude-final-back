package com.upsaude.api.request;

import com.upsaude.api.request.embeddable.ClassificacaoEspecialidadeMedicaRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class EspecialidadesMedicasRequest {
    @NotBlank(message = "Nome da especialidade é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;
    
    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    private String codigo;
    
    @Size(max = 255, message = "Nome científico deve ter no máximo 255 caracteres")
    private String nomeCientifico;
    
    private ClassificacaoEspecialidadeMedicaRequest classificacao;
    
    private String descricao;
    private String areaAtuacaoDescricao;
    private String requisitosFormacao;
    private String observacoes;
}
