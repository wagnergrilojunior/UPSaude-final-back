package com.upsaude.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lgpd_consentimentos", schema = "public",
       indexes = {
           @Index(name = "idx_lgpd_consentimentos_paciente", columnList = "paciente_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
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
