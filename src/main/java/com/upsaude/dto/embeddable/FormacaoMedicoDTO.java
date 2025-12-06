package com.upsaude.dto.embeddable;

import java.time.LocalDate;
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
public class FormacaoMedicoDTO {
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
