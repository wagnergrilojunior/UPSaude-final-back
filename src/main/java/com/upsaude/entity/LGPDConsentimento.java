package com.upsaude.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade para armazenar consentimentos LGPD do paciente.
 * Relacionamento 1:1 com Paciente.
 * Utilizada para conformidade com a Lei Geral de Proteção de Dados Pessoais (Lei 13.709/2018).
 */
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

    /**
     * Relacionamento 1:1 com Paciente.
     * O paciente possui um único registro de consentimentos LGPD.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    /**
     * Indica se o paciente autorizou o uso dos seus dados pessoais.
     * Conforme art. 7º, I da LGPD - consentimento do titular.
     */
    @Column(name = "autorizacao_uso_dados", nullable = false)
    private Boolean autorizacaoUsoDados = false;

    /**
     * Indica se o paciente autorizou contato via WhatsApp.
     * Importante para comunicação com o paciente via aplicativo de mensagens.
     */
    @Column(name = "autorizacao_contato_whatsapp", nullable = false)
    private Boolean autorizacaoContatoWhatsApp = false;

    /**
     * Indica se o paciente autorizou contato via e-mail.
     * Importante para comunicação com o paciente via correio eletrônico.
     */
    @Column(name = "autorizacao_contato_email", nullable = false)
    private Boolean autorizacaoContatoEmail = false;

    /**
     * Data e hora em que o paciente forneceu o consentimento.
     * Importante para rastreabilidade e prova de consentimento.
     */
    @Column(name = "data_consentimento")
    private LocalDateTime dataConsentimento;
}

