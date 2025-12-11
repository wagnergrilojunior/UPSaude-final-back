package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

import java.time.OffsetDateTime;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class IntegracaoGovernamentalVacina {

    public IntegracaoGovernamentalVacina() {
        this.codigoSiPni = "";
        this.codigoESus = "";
        this.sincronizarPni = false;
    }

    @Column(name = "codigo_si_pni", length = 50)
    private String codigoSiPni;

    @Column(name = "codigo_e_sus", length = 50)
    private String codigoESus;

    @Column(name = "sincronizar_pni", nullable = false)
    @Default
    private Boolean sincronizarPni = false;

    @Column(name = "ultima_sincronizacao_pni")
    private OffsetDateTime ultimaSincronizacaoPni;
}
