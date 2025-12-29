package com.upsaude.entity.paciente;
import com.upsaude.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "paciente_dados_clinicos", schema = "public",
       indexes = {
           @Index(name = "idx_paciente_dados_clinicos_paciente", columnList = "paciente_id")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
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
