package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracaoUsuario {

    @Column(name = "admin_tenant", nullable = false)
    @Builder.Default
    private Boolean adminTenant = false;

    @Column(name = "tipo_vinculo", length = 50)
    private String tipoVinculo;
}
