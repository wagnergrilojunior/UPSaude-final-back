package com.upsaude.api.request.profissional;

import com.upsaude.api.request.embeddable.ClassificacaoEspecialidadeMedicaRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de especialidades médicas")
public class EspecialidadesMedicasRequest {
    @NotBlank(message = "Nome da especialidade é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    private String codigo;

    @Size(max = 255, message = "Nome científico deve ter no máximo 255 caracteres")
    private String nomeCientifico;

    @Valid
    private ClassificacaoEspecialidadeMedicaRequest classificacao;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;

    @Size(max = 1000, message = "Área de atuação deve ter no máximo 1000 caracteres")
    private String areaAtuacaoDescricao;

    @Size(max = 1000, message = "Requisitos de formação deve ter no máximo 1000 caracteres")
    private String requisitosFormacao;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
