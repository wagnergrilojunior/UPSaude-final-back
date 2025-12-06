package com.upsaude.api.response.embeddable;

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
public class FormacaoMedicoResponse {
    private String instituicaoEnsino;
    private Integer anoFormatura;
    private LocalDate dataFormatura;
    private String especializacao;
    private String residenciaMedica;
    private String mestrado;
    private String doutorado;
    private String posGraduacao;
    private Boolean tituloEspecialista;
    private String rqe;
}
