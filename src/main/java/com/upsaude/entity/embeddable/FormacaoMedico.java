package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class FormacaoMedico {

    public FormacaoMedico() {
        this.instituicaoEnsino = "";
        this.especializacao = "";
        this.residenciaMedica = "";
        this.mestrado = "";
        this.doutorado = "";
        this.posGraduacao = "";
        this.tituloEspecialista = false;
        this.rqe = "";
    }

    @Size(max = 255, message = "Instituição de ensino deve ter no máximo 255 caracteres")
    @Column(name = "instituicao_ensino", length = 255)
    private String instituicaoEnsino;

    @Column(name = "ano_formatura")
    private Integer anoFormatura;

    @Column(name = "data_formatura")
    private LocalDate dataFormatura;

    @Size(max = 255, message = "Especialização deve ter no máximo 255 caracteres")
    @Column(name = "especializacao", length = 255)
    private String especializacao;

    @Size(max = 255, message = "Residência médica deve ter no máximo 255 caracteres")
    @Column(name = "residencia_medica", length = 255)
    private String residenciaMedica;

    @Size(max = 255, message = "Mestrado deve ter no máximo 255 caracteres")
    @Column(name = "mestrado", length = 255)
    private String mestrado;

    @Size(max = 255, message = "Doutorado deve ter no máximo 255 caracteres")
    @Column(name = "doutorado", length = 255)
    private String doutorado;

    @Size(max = 255, message = "Pós-graduação deve ter no máximo 255 caracteres")
    @Column(name = "pos_graduacao", length = 255)
    private String posGraduacao;

    @Column(name = "titulo_especialista", nullable = false)
    @Builder.Default
    private Boolean tituloEspecialista = false;

    @Size(max = 50, message = "RQE deve ter no máximo 50 caracteres")
    @Column(name = "rqe", length = 50)
    private String rqe;
}
