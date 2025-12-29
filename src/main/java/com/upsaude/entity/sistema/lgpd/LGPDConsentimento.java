package com.upsaude.entity.sistema.lgpd;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.paciente.Paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "paciente_lgpd_consentimento", schema = "public",
       indexes = {
           @Index(name = "idx_paciente_lgpd_consentimento_paciente", columnList = "paciente_id")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class LGPDConsentimento extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @Column(name = "autorizacao_uso_dados", nullable = false)
    private Boolean autorizacaoUsoDados = false;

    @Column(name = "autorizacao_contato_whatsapp", nullable = false)
    private Boolean autorizacaoContatoWhatsApp = false;

    @Column(name = "autorizacao_contato_email", nullable = false)
    private Boolean autorizacaoContatoEmail = false;

    @Column(name = "data_consentimento")
    private LocalDateTime dataConsentimento;
}
