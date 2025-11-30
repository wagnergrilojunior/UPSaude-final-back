package com.upsaude.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade para armazenar dados clínicos básicos do paciente relacionados a risco social e vulnerabilidade.
 * Relacionamento 1:1 com Paciente.
 * Utilizada para registro de informações sobre fatores de risco social, uso de substâncias e vulnerabilidades.
 *
 * Nota: Dados estruturados como alergias, doenças, deficiências e medicações
 * são gerenciados através das entidades relacionadas ao Paciente:
 * - AlergiasPaciente (relacionamento ManyToOne com Paciente)
 * - DoencasPaciente (relacionamento ManyToOne com Paciente)
 * - DeficienciasPaciente (relacionamento ManyToOne com Paciente)
 * - MedicacaoPaciente (relacionamento ManyToOne com Paciente)
 *
 * Medições clínicas (peso, altura, IMC, sinais vitais) são registradas na entidade MedicaoClinica,
 * permitindo histórico temporal de medições.
 *
 * Isso garante integridade referencial, interoperabilidade e consistência dos dados.
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
}

