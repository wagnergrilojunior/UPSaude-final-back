package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe embeddable para informações de exames solicitados na consulta.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ExamesSolicitadosConsulta {

    public ExamesSolicitadosConsulta() {
        this.examesSolicitados = "";
        this.examesLaboratoriais = "";
        this.examesImagem = "";
        this.examesOutros = "";
        this.urgenciaExames = false;
    }

    @Column(name = "exames_solicitados", columnDefinition = "TEXT")
    private String examesSolicitados; // Exames solicitados

    @Column(name = "exames_laboratoriais", columnDefinition = "TEXT")
    private String examesLaboratoriais; // Exames laboratoriais solicitados

    @Column(name = "exames_imagem", columnDefinition = "TEXT")
    private String examesImagem; // Exames de imagem solicitados

    @Column(name = "exames_outros", columnDefinition = "TEXT")
    private String examesOutros; // Outros exames solicitados

    @Column(name = "urgencia_exames", nullable = false)
    @Builder.Default
    private Boolean urgenciaExames = false; // Se os exames são urgentes
}

