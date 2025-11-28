package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Entidade que representa uma medição clínica realizada em um paciente.
 * Armazena sinais vitais e medidas antropométricas com data/hora específica.
 * Permite histórico de medições ao longo do tempo.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "medicoes_clinicas", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class MedicaoClinica extends BaseEntity {

    /**
     * Relacionamento ManyToOne com Paciente.
     * Um paciente pode ter múltiplas medições clínicas ao longo do tempo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    /**
     * Data e hora da medição clínica.
     * Registra quando a medição foi realizada.
     */
    @Column(name = "data_hora", nullable = false)
    private OffsetDateTime dataHora;

    /**
     * Pressão arterial sistólica (mmHg).
     * Valor máximo da pressão arterial durante a contração do coração.
     */
    @Column(name = "pressao_sistolica")
    private Integer pressaoSistolica;

    /**
     * Pressão arterial diastólica (mmHg).
     * Valor mínimo da pressão arterial durante o relaxamento do coração.
     */
    @Column(name = "pressao_diastolica")
    private Integer pressaoDiastolica;

    /**
     * Frequência cardíaca (bpm - batimentos por minuto).
     * Número de vezes que o coração bate por minuto.
     */
    @Column(name = "frequencia_cardiaca")
    private Integer frequenciaCardiaca;

    /**
     * Frequência respiratória (rpm - respirações por minuto).
     * Número de respirações completas por minuto.
     */
    @Column(name = "frequencia_respiratoria")
    private Integer frequenciaRespiratoria;

    /**
     * Temperatura corporal (°C).
     * Medida em graus Celsius com 2 casas decimais de precisão.
     */
    @Column(name = "temperatura", precision = 5, scale = 2)
    private BigDecimal temperatura;

    /**
     * Saturação de oxigênio (% SpO2).
     * Percentual de saturação de oxigênio no sangue medida por oximetria de pulso.
     */
    @Column(name = "saturacao_oxigenio")
    private Integer saturacaoOxigenio;

    /**
     * Glicemia capilar (mg/dL).
     * Nível de glicose no sangue medida por glicosímetro, com 2 casas decimais de precisão.
     */
    @Column(name = "glicemia_capilar", precision = 6, scale = 2)
    private BigDecimal glicemiaCapilar;

    /**
     * Peso do paciente em quilogramas (kg).
     * Precisão: 5 dígitos totais, 2 casas decimais (ex: 150.50 kg).
     */
    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;

    /**
     * Altura do paciente em metros (m).
     * Precisão: 4 dígitos totais, 2 casas decimais (ex: 1.75 m).
     */
    @Column(name = "altura", precision = 4, scale = 2)
    private BigDecimal altura;

    /**
     * Circunferência abdominal (cm).
     * Medida da circunferência da cintura, importante para avaliação de risco cardiovascular.
     * Precisão: 6 dígitos totais, 2 casas decimais.
     */
    @Column(name = "circunferencia_abdominal", precision = 6, scale = 2)
    private BigDecimal circunferenciaAbdominal;

    /**
     * Índice de Massa Corporal (IMC).
     * Calculado: peso / (altura * altura).
     * Precisão: 4 dígitos totais, 2 casas decimais (ex: 24.50).
     */
    @Column(name = "imc", precision = 4, scale = 2)
    private BigDecimal imc;

    /**
     * Observações sobre a medição clínica.
     * Campo de texto livre para anotações adicionais.
     */
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

