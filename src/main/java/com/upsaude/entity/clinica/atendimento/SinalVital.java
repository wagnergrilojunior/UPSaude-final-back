package com.upsaude.entity.clinica.atendimento;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;

@Entity
@Table(name = "sinais_vitais", schema = "public", indexes = {
        @Index(name = "idx_sinais_vitais_paciente", columnList = "paciente_id"),
        @Index(name = "idx_sinais_vitais_atendimento", columnList = "atendimento_id")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class SinalVital extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @Column(name = "data_medicao")
    private OffsetDateTime dataMedicao;

    @Column(name = "peso_kg", precision = 5, scale = 2)
    private BigDecimal pesoKg;

    @Column(name = "altura_cm")
    private Integer alturaCm;

    @Column(name = "imc", precision = 4, scale = 2)
    private BigDecimal imc;

    @Column(name = "pressao_arterial_sistolica")
    private Integer pressaoArterialSistolica;

    @Column(name = "pressao_arterial_diastolica")
    private Integer pressaoArterialDiastolica;

    @Column(name = "frequencia_cardiaca_bpm")
    private Integer frequenciaCardiacaBpm;

    @Column(name = "frequencia_respiratoria_rpm")
    private Integer frequenciaRespiratoriaRpm;

    @Column(name = "temperatura_celsius", precision = 3, scale = 1)
    private BigDecimal temperaturaCelsius;

    @Column(name = "saturacao_o2_percentual")
    private Integer saturacaoO2Percentual;

    @Column(name = "glicemia_capilar_mg_dl")
    private Integer glicemiaCapilarMgDl;

    @Column(name = "status_glicemia", length = 20)
    private String statusGlicemia;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @PrePersist
    @PreUpdate
    public void calcularIMC() {
        if (pesoKg != null && alturaCm != null && alturaCm > 0) {
            double alturaMetros = alturaCm / 100.0;
            double imcValue = pesoKg.doubleValue() / (alturaMetros * alturaMetros);
            this.imc = BigDecimal.valueOf(imcValue).setScale(2, RoundingMode.HALF_UP);
        }
    }
}
