package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Classe embeddable para informações de integração com sistemas governamentais.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegracaoGovernamentalVacina {

    @Column(name = "codigo_si_pni", length = 50)
    private String codigoSiPni; // Código no Sistema de Informação do PNI

    @Column(name = "codigo_e_sus", length = 50)
    private String codigoESus; // Código no e-SUS

    @Column(name = "sincronizar_pni", nullable = false)
    @Default
    private Boolean sincronizarPni = false; // Se deve sincronizar com PNI

    @Column(name = "ultima_sincronizacao_pni")
    private OffsetDateTime ultimaSincronizacaoPni; // Data da última sincronização com PNI
}

