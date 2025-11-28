package com.upsaude.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Entidade para armazenar dados clínicos básicos do paciente.
 * Relacionamento 1:1 com Paciente.
 * Utilizada para registro de informações clínicas iniciais e acompanhamento básico.
 */
@Entity
@Table(name = "dados_clinicos_basicos", schema = "public",
       indexes = {
           @Index(name = "idx_dados_clinicos_basicos_paciente", columnList = "paciente_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DadosClinicosBasicos extends BaseEntity {

    /**
     * Relacionamento 1:1 com Paciente.
     * O paciente possui um único registro de dados clínicos básicos.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    /**
     * Indica se o paciente está gestante.
     * Importante para atenção pré-natal e políticas públicas.
     */
    @Column(name = "gestante", nullable = false)
    private Boolean gestante = false;

    /**
     * Lista de alergias do paciente em formato JSON.
     * Armazena informações sobre alergias (substâncias, reações, etc.).
     */
    @Column(name = "alergias_json", columnDefinition = "jsonb")
    private String alergiasJson;

    /**
     * Lista de doenças crônicas do paciente em formato JSON.
     * Armazena informações sobre condições crônicas (diabetes, hipertensão, etc.).
     */
    @Column(name = "doencas_cronicas_json", columnDefinition = "jsonb")
    private String doencasCronicasJson;

    /**
     * Lista de deficiências do paciente em formato JSON.
     * Armazena informações sobre deficiências físicas, sensoriais, intelectuais, etc.
     */
    @Column(name = "deficiencias_json", columnDefinition = "jsonb")
    private String deficienciasJson;

    /**
     * Lista de medicações contínuas do paciente em formato JSON.
     * Armazena informações sobre medicamentos de uso contínuo.
     */
    @Column(name = "medicacoes_continuas_json", columnDefinition = "jsonb")
    private String medicacoesContinuasJson;

    /**
     * Indica se o paciente é fumante.
     * Importante para avaliação de risco cardiovascular e pulmonar.
     */
    @Column(name = "fumante", nullable = false)
    private Boolean fumante = false;

    /**
     * Indica se o paciente faz uso abusivo de álcool.
     * Importante para avaliação de risco e planejamento de cuidados.
     */
    @Column(name = "alcoolista", nullable = false)
    private Boolean alcoolista = false;

    /**
     * Indica se o paciente faz uso de drogas ilícitas.
     * Importante para avaliação de risco e planejamento de cuidados.
     */
    @Column(name = "usuario_drogas", nullable = false)
    private Boolean usuarioDrogas = false;

    /**
     * Peso do paciente em quilogramas (kg).
     * Utilizado para cálculo do IMC e acompanhamento nutricional.
     */
    @Column(name = "peso")
    private Double peso;

    /**
     * Altura do paciente em metros (m).
     * Utilizado para cálculo do IMC.
     */
    @Column(name = "altura")
    private Double altura;

    /**
     * Índice de Massa Corporal (IMC).
     * Calculado automaticamente: peso / (altura * altura).
     * Classificação: <18.5 (abaixo), 18.5-24.9 (normal), 25-29.9 (sobrepeso), ≥30 (obesidade).
     */
    @Column(name = "imc")
    private Double imc;

    /**
     * Indica se o paciente possui histórico de violência.
     * Importante para identificação de situações de risco e planejamento de cuidados.
     */
    @Column(name = "historico_violencia", nullable = false)
    private Boolean historicoViolencia = false;

    /**
     * Indica se o paciente está em acompanhamento psicossocial.
     * Utilizado para rastreamento de atenção psicossocial na atenção básica.
     */
    @Column(name = "acompanhamento_psicossocial", nullable = false)
    private Boolean acompanhamentoPsicossocial = false;

    /**
     * Método auxiliar para calcular o IMC baseado no peso e altura.
     * Deve ser chamado quando peso ou altura forem atualizados.
     */
    @PostLoad
    @PostUpdate
    @PostPersist
    private void calcularImc() {
        if (peso != null && altura != null && altura > 0) {
            BigDecimal pesoBD = BigDecimal.valueOf(peso);
            BigDecimal alturaBD = BigDecimal.valueOf(altura);
            BigDecimal alturaQuadrado = alturaBD.multiply(alturaBD);
            BigDecimal imcCalculado = pesoBD.divide(alturaQuadrado, 2, RoundingMode.HALF_UP);
            this.imc = imcCalculado.doubleValue();
        } else {
            this.imc = null;
        }
    }
}

