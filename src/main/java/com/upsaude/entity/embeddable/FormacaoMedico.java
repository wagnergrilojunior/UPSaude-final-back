package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Classe embeddable para dados de formação do médico.
 *
 * @author UPSaúde
 */
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
    private String instituicaoEnsino; // Instituição onde se formou

    @Column(name = "ano_formatura")
    private Integer anoFormatura; // Ano de formatura

    @Column(name = "data_formatura")
    private LocalDate dataFormatura; // Data de formatura

    @Size(max = 255, message = "Especialização deve ter no máximo 255 caracteres")
    @Column(name = "especializacao", length = 255)
    private String especializacao; // Especialização realizada

    @Size(max = 255, message = "Residência médica deve ter no máximo 255 caracteres")
    @Column(name = "residencia_medica", length = 255)
    private String residenciaMedica; // Residência médica realizada

    @Size(max = 255, message = "Mestrado deve ter no máximo 255 caracteres")
    @Column(name = "mestrado", length = 255)
    private String mestrado; // Mestrado realizado

    @Size(max = 255, message = "Doutorado deve ter no máximo 255 caracteres")
    @Column(name = "doutorado", length = 255)
    private String doutorado; // Doutorado realizado

    @Size(max = 255, message = "Pós-graduação deve ter no máximo 255 caracteres")
    @Column(name = "pos_graduacao", length = 255)
    private String posGraduacao; // Outras pós-graduações

    @Column(name = "titulo_especialista", nullable = false)
    @Builder.Default
    private Boolean tituloEspecialista = false; // Se possui título de especialista

    @Size(max = 50, message = "RQE deve ter no máximo 50 caracteres")
    @Column(name = "rqe", length = 50)
    private String rqe; // Registro de Qualificação de Especialista
}

