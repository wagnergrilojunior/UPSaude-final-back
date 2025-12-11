package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormacaoMedicoRequest {
    @Size(max = 255, message = "Instituição de ensino deve ter no máximo 255 caracteres")
    private String instituicaoEnsino;

    private Integer anoFormatura;

    private LocalDate dataFormatura;

    @Size(max = 255, message = "Especialização deve ter no máximo 255 caracteres")
    private String especializacao;

    @Size(max = 255, message = "Residência médica deve ter no máximo 255 caracteres")
    private String residenciaMedica;

    @Size(max = 255, message = "Mestrado deve ter no máximo 255 caracteres")
    private String mestrado;

    @Size(max = 255, message = "Doutorado deve ter no máximo 255 caracteres")
    private String doutorado;

    @Size(max = 255, message = "Pós-graduação deve ter no máximo 255 caracteres")
    private String posGraduacao;

    @NotNull(message = "Título especialista é obrigatório")
    @Builder.Default
    private Boolean tituloEspecialista = false;

    @Size(max = 50, message = "RQE deve ter no máximo 50 caracteres")
    private String rqe;
}
