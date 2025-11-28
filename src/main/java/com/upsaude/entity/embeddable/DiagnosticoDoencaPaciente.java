package com.upsaude.entity.embeddable;

import com.upsaude.enums.GravidadeDoencaEnum;
import com.upsaude.enums.StatusDiagnosticoEnum;
import com.upsaude.util.converter.GravidadeDoencaEnumConverter;
import com.upsaude.util.converter.StatusDiagnosticoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Classe embeddable para informações de diagnóstico da doença no paciente.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoDoencaPaciente {

    @Column(name = "data_diagnostico")
    private LocalDate dataDiagnostico; // Data do diagnóstico

    @Column(name = "data_inicio_sintomas")
    private LocalDate dataInicioSintomas; // Data de início dos sintomas

    @Convert(converter = StatusDiagnosticoEnumConverter.class)
    @Column(name = "status_diagnostico")
    private StatusDiagnosticoEnum statusDiagnostico; // Status do diagnóstico

    @Convert(converter = GravidadeDoencaEnumConverter.class)
    @Column(name = "gravidade_atual")
    private GravidadeDoencaEnum gravidadeAtual; // Gravidade atual da doença no paciente

    @Column(name = "local_diagnostico", length = 255)
    private String localDiagnostico; // Local onde foi feito o diagnóstico

    @Column(name = "metodo_diagnostico", length = 255)
    private String metodoDiagnostico; // Método utilizado para diagnóstico (ex: Exame clínico, Laboratorial, Imagem)
}

