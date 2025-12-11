package com.upsaude.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @Column(name = "gestante", nullable = false)
    private Boolean gestante = false;

    @Column(name = "fumante", nullable = false)
    private Boolean fumante = false;

    @Column(name = "alcoolista", nullable = false)
    private Boolean alcoolista = false;

    @Column(name = "usuario_drogas", nullable = false)
    private Boolean usuarioDrogas = false;

    @Column(name = "historico_violencia", nullable = false)
    private Boolean historicoViolencia = false;

    @Column(name = "acompanhamento_psicossocial", nullable = false)
    private Boolean acompanhamentoPsicossocial = false;
}
